package com.pan.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 17:12
 **/
@Configuration
public class ThreadPoolConfig {
    public static final String EVENT_THREAD_POOL = "eventThreadPool";

    @Bean(EVENT_THREAD_POOL)
    public ThreadPoolExecutor eventThreadPool() {
        return new ThreadPoolExecutor(
            5,
            10,
            30,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(100),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
