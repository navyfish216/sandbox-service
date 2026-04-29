package com.example.demo.sandbox.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class SandoxConfiguration {

	@Value("${server.port:8080}")
	private Integer selfServerPort;
}
