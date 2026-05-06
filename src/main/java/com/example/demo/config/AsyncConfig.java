package com.example.demo.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.support.ContextPropagatingTaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    Executor taskExecutor() {

    	var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);      // 常に維持するスレッド数
        executor.setMaxPoolSize(10);      // 最大スレッド数
        executor.setQueueCapacity(100);   // キュー容量
        executor.setThreadNamePrefix("async-");  // スレッド名
        executor.setTaskDecorator(new ContextPropagatingTaskDecorator()); // トレース情報を伝播
        executor.initialize();
        
        return executor;
    }

}
