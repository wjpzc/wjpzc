package cn.xypt.service.impl;

import static cn.xypt.util.RoleConstant.ADMIN;
import static cn.xypt.util.RoleConstant.STUDENT;
import static cn.xypt.util.SystemConstant.COLLEGE_NAME_DICT_PARENT_ID;


import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

import cn.xypt.common.utils.JwtUtil;
import cn.xypt.mapper.UserMapper;
import cn.xypt.model.domain.Dict;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.LoginDTO;
import cn.xypt.model.dto.QueryUserDTO;
import cn.xypt.model.dto.UserDTO;
import cn.xypt.model.vo.UserVO;
import cn.xypt.service.DictService;
import cn.xypt.service.UserService;
import cn.xypt.util.AliyunSmsUtil;
import cn.xypt.util.FileUtil;
import cn.xypt.util.MinioUtils;
import cn.xypt.util.PasswordUtil;
import cn.hutool.core.bean.BeanUtil;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;



import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;


/**
 * @author zhangchao
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-04-17 11:50:07
 */
@Slf4j
@Validated
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private DictService dictService;

    @Override
    public SaResult login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        System.out.println("username"+username);
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            throw new RuntimeException("用户名错误");
        }

        if (!user.getPassword().equals("111111")) {
            throw new RuntimeException("密码错误");
        }
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return SaResult.data(tokenInfo);
    }

    @Override
    public SaResult addUser(User user) {
        Long count = lambdaQuery().eq(User::getUsername, user.getUsername()).count();
        if (count > 0) {
            throw new RuntimeException("该用户名已存在");
        }
        user.setPassword(PasswordUtil.getMD5Pwd(user.getPassword()));
        this.save(user);
        return SaResult.data(user.getId());
    }

    @Override
    public SaResult getUserPage(QueryUserDTO queryUserDTO) {
        Page<User> userPage = lambdaQuery()
            .eq(queryUserDTO.getId() != null, User::getId, queryUserDTO.getId())
            .apply(StrUtil.isNotBlank(queryUserDTO.getRole()),
                "JSON_CONTAINS(role_list -> '$[*]',JSON_ARRAY({0}))", queryUserDTO.getRole())
            .like(StrUtil.isNotBlank(queryUserDTO.getNickname()), User::getNickname,
                queryUserDTO.getNickname())
            .like(StrUtil.isNotBlank(queryUserDTO.getSchool()), User::getSchool,
                queryUserDTO.getSchool())
            .page(new Page<>(queryUserDTO.getCurrent(), queryUserDTO.getSize()));

        IPage<UserVO> userVOIPage = userPage.convert(
            user -> BeanUtil.copyProperties(user, UserVO.class));
        return SaResult.data(userVOIPage);
    }

    @Override
    public SaResult updateUser(UserDTO userDTO) {
        // 查询待修改用户
        User editUser = this.getById(userDTO.getId());
        // 获取当前用户id
        int currentUserId = StpUtil.getLoginIdAsInt();
        // 当前用户id与待修改用户id不同
        if (!userDTO.getId().equals(currentUserId) &&
            (!StpUtil.hasRoleOr(ADMIN, STUDENT))) {
            throw new RuntimeException("权限不足");
        }
        // 密码项非空，涉及到修改密码
        if (StrUtil.isNotBlank(userDTO.getPassword())) {
            // 防止普通用户用此接口修改密码，管理员可以修改所有的密码，专业负责人可以修改教师密码
            if (!StpUtil.hasRoleOr(ADMIN, STUDENT)) {
                userDTO.setPassword(null);
            } else {
                // 操作人为管理员
                // 待修改用户为管理员
                if (!editUser.getId().equals(currentUserId) && editUser.getRoleList()
                    .contains(ADMIN)) {
                    throw new RuntimeException("权限不足，不能修改其他管理员的密码");
                }
                // 待修改用户为学生
                if (!editUser.getId().equals(currentUserId) && editUser.getRoleList()
                    .contains(STUDENT) && !StpUtil.hasRole(ADMIN)) {
                    throw new RuntimeException("权限不足，不能修改其他人的密码");
                }
                userDTO.setPassword(PasswordUtil.getMD5Pwd(userDTO.getPassword()));
            }
        }
        // 用户名需要修改
        if (StrUtil.isNotBlank(userDTO.getUsername()) && !userDTO.getUsername()
            .equals(editUser.getUsername())) {
            Long count = lambdaQuery().eq(User::getUsername, userDTO.getUsername()).count();
            if (count > 0) {
                throw new RuntimeException("该用户名已存在");
            }
        }

        User user = BeanUtil.copyProperties(userDTO, User.class);
        this.updateById(user);
        return SaResult.data(user.getId());
    }

    @Override
    public void downloadImportTemplate(HttpServletResponse response) {
        try {
            // 查询所有的学院，现在字典中默认父节点为1的为学院名称
            String[] colleges = dictService.lambdaQuery()
                .eq(Dict::getParentId, COLLEGE_NAME_DICT_PARENT_ID).list()
                .stream().map(Dict::getName).toArray(String[]::new);

            FileUtil.setDownloadHeader(response, "userImportTemplate.xlsx");
            EasyExcel.write(response.getOutputStream(), UserDTO.class).autoCloseStream(false)
                .sheet("用户导入模板")
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                // 写入下拉框
                .registerWriteHandler(new SheetWriteHandler() {
                    @Override
                    public void afterSheetCreate(SheetWriteHandlerContext context) {
                        // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
                        CellRangeAddressList collegeCellRangeAddressList = new CellRangeAddressList(
                            1, 200, 4, 4);
                        CellRangeAddressList sexCellRangeAddressList = new CellRangeAddressList(
                            1, 200, 1, 1);
                        DataValidationHelper helper = context.getWriteSheetHolder().getSheet()
                            .getDataValidationHelper();
                        DataValidationConstraint collegesConstraint = helper.createExplicitListConstraint(
                            colleges);
                        DataValidation collegeData = helper.createValidation(collegesConstraint,
                            collegeCellRangeAddressList);
                        DataValidationConstraint sexConstraint = helper.createExplicitListConstraint(
                            new String[]{"男", "女"});
                        DataValidation sexData = helper.createValidation(sexConstraint,
                            sexCellRangeAddressList);
                        // 写入学院下拉框
                        context.getWriteSheetHolder().getSheet().addValidationData(collegeData);
                        // 写入性别下拉框
                        context.getWriteSheetHolder().getSheet().addValidationData(sexData);
                    }
                })
                .doWrite(Collections.emptyList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("下载文件失败");
        }
    }


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void regUser(User user) {
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        设置角色为学生
        save(user);
    }
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public User getUserInfo(String token) {
        try {
            User user = jwtUtil.parseJwt(token,User.calss);
            return user;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    @Autowired
    private MinioUtils minioUtils;


    @Override
    public Map<String, Object> updateUserAvatar(Integer userId, String avatar ) {
        baseMapper.updateUserAvatar(userId, avatar);

        User user = getById(userId);
        user.setAvatarUrl(minioUtils.getUrl(user.getAvatar() ));

        String jwt = jwtUtil.createJwt(user);

        Map<String, Object> data = new HashMap<>();
        data.put("token", jwt);
        data.put("user", user);
        return data;



    }


    @Value("${my.weixin.appid}")
    private String appid;

    @Value("${my.weixin.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String wxlogin(String jscode) {
       //微信接口
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code"
                .formatted(appid,secret,jscode);
        //调用微信接口，获取openid
        String result =restTemplate.getForObject(url,String.class);
        HashMap resultMap = JSON.parseObject(result, HashMap.class);
        log.debug("------->微信登录"+resultMap);

        //获取openid
        String openid = (String)resultMap.get("openid");
        User user = baseMapper.getUSerByOpenid(openid);
        if(user==null){

            user = new User();
            String name = "wx" + (UUID.randomUUID().toString().replaceAll("-", "")).substring(12);
            user.setUsername(name);
            user.setNickname(name);
            user.setType(2);
            user.setSex("X");
            user.setOpenid(openid);
            user.setAvatar("/static/avatar.png");
            user.setLevel(0);
            this.save(user);
        }
        String jwt = jwtUtil.createJwt(user);
        return jwt;
    }

    @Autowired
    private AliyunSmsUtil aliyunSmsUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    private String getCode(String phone,String code){
        return "code:" + phone + "-" + code;
    }
    @Override
    public void sendSms(String phone) {
        Random random = new Random();
        String rcode = String.format("%06d",random.nextInt(1000000));
        //发送
        aliyunSmsUtil.sendSms(phone, rcode);
        log.debug("------->发送验证码："+ rcode);
        //存入redis
        redisTemplate.opsForValue().set(getCode(phone,rcode),rcode,5, TimeUnit.MINUTES);
    }

    @Override
    public boolean checkCode(String phone, String code) {
        String key = getCode(phone,code);
        Object object = redisTemplate.opsForValue().get(key);
        return object!=null ? true : false;


    }


}
