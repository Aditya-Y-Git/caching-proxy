package com.xo.caching_proxy.cli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@Component
@CommandLine.Command(name="caching-proxy", description = "Caching Proxy Server CLI")
public class CachingProxyCLI  implements Callable<Integer> {
    @CommandLine.Option(names = {"--port"}, required = true, description = "Port to run proxy server")
    private Integer port;

    @CommandLine.Option(names = {"--origin"},required = true, description = "Origin server URL")
    private String originUrl;

    @CommandLine.Option(names = {"--clear-cache"}, description = "Clear the cache")
    private boolean clearCache;

    @Override
    public Integer call() throws Exception {
        if(clearCache){
            System.out.println("Clearing cache");
            return 0;
        }

        if (port == null || originUrl == null) {
            System.err.println("Both --port and --origin are required");
            return 1;
        }

        System.out.println("Starting proxy:");
        System.out.println("Port: " + port);
        System.out.println("Origin: " + originUrl);

        return 0;
    }
}
