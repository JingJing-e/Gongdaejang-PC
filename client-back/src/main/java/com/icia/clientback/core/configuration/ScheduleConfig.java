package com.icia.clientback.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Bean
    public ScheduledExecutorFactoryBean scheduledExecutorFactoryService() {
        ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
        bean.setPoolSize(5);
        return bean;
    }
}
