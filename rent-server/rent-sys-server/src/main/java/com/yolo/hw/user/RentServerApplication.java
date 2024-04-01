package com.yolo.hw.user;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@ComponentScan({
		"com.yolo.hw.user",
		"com.yolo.hw.user.domain",
		"com.yolo.hw.user.config.swagger",
		"com.yolo.hw.user.config.weblog",
		"com.yolo.hw.user.config.normal",
})
@SpringBootApplication
@EnableEurekaClient
public class RentServerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RentServerApplication.class, args);
		log.info("汽车租赁服务启动完成");
		log.info("**************************************");
		try {
			String port = context.getEnvironment().getProperty("server.port");
			String path = context.getEnvironment().getProperty("server.servlet.context-path");
			InetAddress[] addresses = InetAddress.getAllByName(InetAddress.getLocalHost().getHostAddress());
			for (InetAddress ip : addresses) {
				String host = ip.getHostAddress();
				log.info("汽车租赁信息:" + host + ":" + port + path);
			}
		} catch (UnknownHostException e) {
			log.error("汽车租赁服务异常:", e);
		}
		log.info("**************************************");
		System.out.println("============汽车租赁服务启动完成=============");
	}
}
