package com.costrategix.gbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import net.bytebuddy.utility.RandomString;

@SpringBootApplication
@EnableCaching
public class Application {
	private static final Logger LOGGER = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
//		String body = "{First name} {Last Name} [Activation Link]";
//	    body=body.replace("{First name} ","Naveen");
//	    body=body.replace("{Last Name}","MS");
//	    body=body.replace("[Activation Link]", "Hello");
//	    System.out.println(body);
		//Hello-world

	    System.out.println("hello");
		SpringApplication.run(Application.class, args);		
		LOGGER.info("Info level log message");
	    LOGGER.debug("Debug level log message");
	    LOGGER.error("Error level log message");
	}

	
}
