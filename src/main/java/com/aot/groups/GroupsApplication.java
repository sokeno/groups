package com.aot.groups;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.aot.groups.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class GroupsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupsApplication.class, args);
	}

}
