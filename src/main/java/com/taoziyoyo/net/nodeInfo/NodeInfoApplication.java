package com.taoziyoyo.net.nodeInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class NodeInfoApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(NodeInfoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(NodeInfoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("NodeInfo start CommandRunner");
	}
}
