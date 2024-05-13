package cn.xypt.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskPoolConfig {

    @Bean("taskExecutor") // bean 的名称，默认为首字母小写的方法名
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数（默认线程数）
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(200); // 缓冲队列数
        executor.setKeepAliveSeconds(60); // 允许线程空闲时间（单位：默认为秒）
        executor.setThreadNamePrefix("taskExecutor-"); // 线程池名前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}