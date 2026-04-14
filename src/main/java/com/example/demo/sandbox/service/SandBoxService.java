package com.example.demo.sandbox.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SandBoxService {

	public String getString() {
		
		log.info("sandbox.service.log.start");
		var ret = "Hello World!!";
		log.info("sandbox.service.log.end");
		
		return ret;
	}
}
