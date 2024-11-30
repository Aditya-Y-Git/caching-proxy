package com.xo.caching_proxy;

import com.xo.caching_proxy.cli.CachingProxyCLI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class CachingProxyApplication implements CommandLineRunner {
	private final CachingProxyCLI cliCommand;
	private final CommandLine.IFactory factory;

	public CachingProxyApplication(CachingProxyCLI cliCommand, CommandLine.IFactory factory){
		this.cliCommand = cliCommand;
		this.factory = factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(CachingProxyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		int exitCode = new CommandLine(cliCommand, factory).execute(args);
		System.exit(exitCode);
	}
}
