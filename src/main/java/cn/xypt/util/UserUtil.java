package cn.xypt.util;

import cn.dev33.satoken.stp.StpUtil;
import cn.xypt.model.domain.User;
import cn.xypt.service.UserService;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author zhangchao
 * @date 2024/04/17
 */
@Component
public class UserUtil {

    @Resource
    private UserService userServiceBean;

    private static UserService userService;

    @PostConstruct
    public void init() {
        userService = userServiceBean;
    }

    /**
     * 获取当前登录用户
     * @return cn.gcrz.model.domain.User
     */
    public static User getCurrentUser() {
        int userId = StpUtil.getLoginIdAsInt();
        return userService.getById(userId);
    }
}
