package cn.xypt.security.handler;

import cn.xypt.common.utils.JwtUtil;
import cn.xypt.common.vo.Result;
import cn.xypt.model.domain.User;
import cn.xypt.security.entity.SecurityUser;
import cn.xypt.util.MinioUtils;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MinioUtils minioUtils;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //获得登录的用户对象
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();


        //通过jwt创建token
        User user = securityUser.getUser();
        user.setAvatarUrl(minioUtils.getUrl(user.getAvatar()));
        String token = jwtUtil.createJwt(user);


        //返回统一result
        Result<Object> result = Result.success(token, "登录成功");

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
