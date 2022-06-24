package person.crayon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author Crayon
 * @date 2021/12/29 14:03
 * @desc 线程池配置类
 */
@Configuration
public class ExecutorConfig {
    @Bean
    public Executor crawlerExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("crawler-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
