package cn.xypt.security.filter;

import cn.xypt.common.utils.JwtUtil;
import cn.xypt.model.domain.User;
import cn.xypt.security.entity.SecurityUser;
import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component



public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.放行部分请求
        String uri = request.getRequestURI();
        if(!uri.endsWith("/user/login")
                && uri.indexOf("/user/wxlogin") < 0
                && uri.indexOf("/user/info") < 0
                && !uri.endsWith("/user/logout")
                && !uri.endsWith("/user/reg")
                && !uri.endsWith("/file/upload")
                && uri.indexOf("/base") < 0
                && uri.indexOf("/user/count") < 0
//                && uri.indexOf("/user/sms") < 0


        ){

            //2.获取jwt，解析
            String token = request.getHeader("Authorization");
            //if内的判断条件可以为StringUtils.hasLength(token)，但是找不到该方法
            if(token != null && !token.trim().isEmpty()){
                User user = null;
                try {
                    user = jwtUtil.parseJwt(token, User.class);
                    if(user != null){
                        //3.jwt有效，则告诉security这是一个有效的已认证的请求
                        SecurityUser securityUser = new SecurityUser(user);
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities())
                        );

                    }
                } catch (Exception e) {
                   // throw new RuntimeException(e);
                }

            }

        }

        filterChain.doFilter(request, response);




    }
}
