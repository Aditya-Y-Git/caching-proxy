package com.xo.caching_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.xo.caching_proxy")
public class CachingProxyApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(CachingProxyApplication.class);
        application.run(args);
    }
}