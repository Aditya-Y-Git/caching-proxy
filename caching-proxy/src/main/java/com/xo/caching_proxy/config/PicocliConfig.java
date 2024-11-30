package com.xo.caching_proxy.config;

import com.xo.caching_proxy.cli.CachingProxyCLI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

@Configuration
public class PicocliConfig {
    @Bean
    public CommandLine.IFactory commandLineIFactory(){
        return new CommandLine.IFactory() {
            @Override
            public <K> K create(Class<K> aClass) throws Exception {
                return aClass.getDeclaredConstructor().newInstance();
            }
        };
    }

    @Bean
    public CachingProxyCLI cachingProxyCLI(){
        return new CachingProxyCLI();
    }
}
