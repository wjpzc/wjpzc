package cn.xypt.security.filter;

import cn.xypt.model.domain.User;

import com.alibaba.fastjson2.JSON;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if ( !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
            try {
                ServletInputStream is = request.getInputStream();

                User user = JSON.parseObject(is, User.class);
                UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(),
                        user.getPassword());
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
}



