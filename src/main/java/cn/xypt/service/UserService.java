package cn.xypt.service;

import cn.dev33.satoken.util.SaResult;
import cn.xypt.model.domain.User;
import cn.xypt.model.dto.LoginDTO;
import cn.xypt.model.dto.QueryUserDTO;
import cn.xypt.model.dto.UserDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
* @author zhangchao
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-04-17 11:50:07
*/
public interface UserService extends IService<User> {

    SaResult login(LoginDTO loginDTO);


    SaResult addUser(User user);


    SaResult getUserPage(QueryUserDTO queryUserDTO);

    SaResult updateUser(UserDTO userDTO);

    void downloadImportTemplate(HttpServletResponse response);



    void regUser(User user);

    User getUserInfo(String token);

    Map<String, Object> updateUserAvatar(Integer userId,String avatar);

    String wxlogin(String jscode);


    void sendSms(String phone);

    boolean checkCode(String phone, String code);
}
