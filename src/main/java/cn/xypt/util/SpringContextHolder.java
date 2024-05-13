package cn.xypt.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhangchao
 * @date 2024/04/17
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 获取 Spring 管理的 Bean 对象
     * @param clazz Bean 的 Class 类型
     * @param  <T> Bean 的类型
     * @return  Bean 实例
     */
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
