package cn.xypt.security.service.impl;

import cn.xypt.mapper.UserMapper;
import cn.xypt.model.domain.User;
import cn.xypt.security.entity.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }


        return new SecurityUser(user);
    }
}
