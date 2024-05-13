package cn.xypt.controller;


import cn.dev33.satoken.util.SaResult;
import cn.xypt.common.vo.Result;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.ChangePwdDTO;
import cn.xypt.model.dto.LoginDTO;
import cn.xypt.model.dto.QueryUserDTO;
import cn.xypt.model.dto.UserDTO;
import cn.xypt.model.vo.UserVO;
import cn.xypt.service.UserService;
import cn.xypt.util.AddGroup;
import cn.xypt.util.PasswordUtil;
import cn.xypt.util.UpdateGroup;
import cn.xypt.util.UserUtil;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangchao
 * @date 2024/04/13
 */
@Api(tags = "用户管理")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @ApiOperation("登录")
    @PostMapping("/login")
    public SaResult login(@RequestBody @Validated LoginDTO loginDTO) {
        System.out.println("loginDTO" + loginDTO);
        return userService.login(loginDTO);
    }

    @ApiOperation("分页查询用户")
//    @SaCheckRole(value = {ADMIN,  STUDENT}, mode = SaMode.OR)
    @GetMapping("/page")
    public SaResult getUserPage(@Validated QueryUserDTO queryUserDTO) {
        return userService.getUserPage(queryUserDTO);
    }

    @ApiOperation("添加用户")
//    @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
    @PostMapping("/addUser")
    public SaResult addUser(@Validated({AddGroup.class}) @RequestBody UserDTO userDTO) {
        User user = BeanUtil.copyProperties(userDTO, User.class);
        return userService.addUser(user);
    }


    @ApiOperation("删除用户")
    @PostMapping("/deleteUser")
    public SaResult deleteUser(@Validated @RequestParam("ids") @NotEmpty List<Integer> ids) {
        boolean flag = userService.removeBatchByIds(ids);
        return SaResult.data(flag);
    }

    @ApiOperation(value = "更新用户信息", notes = "教师角色不能通过此接口修改密码，应该用修改密码的接口")
//    @SaCheckRole(value = {ADMIN, STUDENT}, mode = SaMode.OR)
    @PostMapping("/updateUser")
    public SaResult updateUser(@Validated({UpdateGroup.class}) @RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }


    /**
     * 修改密码，普通用户只能自己修改自己
     */
    @ApiOperation("修改密码")
    @PostMapping("/changePwd")
    public SaResult changePwd(@Validated @RequestBody ChangePwdDTO changePwdDTO) {
        // 获取当前用户信息
        User currentUser = UserUtil.getCurrentUser();
        // 非管理员
        if (!currentUser.getId().equals(changePwdDTO.getUserId())) {
            throw new RuntimeException("权限不足，不能修改其他用户的密码");
        }
        User user = userService.getById(currentUser.getId());
        if (PasswordUtil.getMD5Pwd(changePwdDTO.getOldPassword()).equals(user.getPassword())) {
            PasswordUtil.checkPassword(changePwdDTO.getNewPassword());
            user.setPassword(PasswordUtil.getMD5Pwd(changePwdDTO.getNewPassword()));
            userService.updateById(user);
        } else {
            throw new RuntimeException("原密码错误");
        }

        return SaResult.ok("修改密码成功");
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam("token") String token) {
        User user = userService.getUserInfo(token);
        if (user != null) {
            return Result.success(user);
        }
        return Result.fail(20003, "获取用户信息失败");


    }

    @ApiOperation("微信登录")
    @PostMapping("/wxlogin")
    public Result<String> wxlogin(@RequestParam("jscode") String jscode) {
        String token = userService.wxlogin(jscode);
        return Result.success(token, "微信登录成功");

    }


    @ApiOperation("用户注册")
    @PostMapping("/reg")
    public Result regUser(@Validated @RequestBody User user) {
        userService.regUser(user);
        return Result.success(user);

    }


    @ApiOperation("查询所有用户信息")
    @GetMapping("/all")
    public SaResult getAll() {
        List<User> users = userService.list();
        return SaResult.data(users);
    }

    @ApiOperation("更改用户头像")
    @PutMapping("/avatar")
    public Result<Map<String, Object>> updateUserAvatar(@RequestParam("userId") Integer userId,
                                                        @RequestParam("avatar") String avatar) {
        Map<String, Object> data = userService.updateUserAvatar(userId, avatar);

        return Result.success(data);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/sms/{phone}")
    public Result<Object> sendSms(@PathVariable("phone") String phone) {
        userService.sendSms(phone);
        return Result.success();
    }

    @ApiOperation("校验验证码")
    @GetMapping("/sms/check")
    public Result<Boolean> checkCode(@RequestParam("phone") String phone,@RequestParam("code") String code){
        boolean flag = userService.checkCode(phone, code);
        return Result.success(flag);
    }




}
