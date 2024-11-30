package com.xo.caching_proxy.cli;

import com.xo.caching_proxy.config.ServerConfig;
import com.xo.caching_proxy.controller.ProxyController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import picocli.CommandLine;


@Component
@CommandLine.Command(name = "caching-proxy", description = "Caching Proxy Server CLI")
public class CachingProxyCLI implements CommandLineRunner {
    @CommandLine.Option(names = {"--port"}, description = "Port to run proxy server")
    private Integer port;

    @CommandLine.Option(names = {"--origin"}, description = "Origin server URL")
    private String originUrl;

    @CommandLine.Option(names = {"--clear-cache"}, description = "Clear the cache")
    private boolean clearCache;

    private final ServerConfig serverConfig;
    private final ProxyController proxyController;

    public CachingProxyCLI(ServerConfig serverConfig, ProxyController proxyController) {
        this.serverConfig = serverConfig;
        this.proxyController = proxyController;
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLine.ParseResult parseResult = new CommandLine(this).parseArgs(args);
        if (clearCache) {
            proxyController.clearCache();
            System.out.println("Clearing cache");
            return;
        }

        if (port == null || originUrl == null) {
            System.err.println("Both --port and --origin are required");
            return;
        }

        System.out.println("Starting proxy:");
        System.out.println("Port: " + port);
        System.out.println("Origin: " + originUrl);

        serverConfig.setPort(port);
        proxyController.setOriginUrl(originUrl);
    }
}
