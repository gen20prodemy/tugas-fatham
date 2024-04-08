package com.spring.pos2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean("asyncTaskExecutor")
    public Executor asyncTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);//jumlah minimum thread
        taskExecutor.setQueueCapacity(150);// Kapasitas antrian
        taskExecutor.setMaxPoolSize(4);//jumlah maksimum thread
        taskExecutor.setThreadNamePrefix("AsyncTaskThread-");
        taskExecutor.initialize();;
        return taskExecutor;
    }
}
