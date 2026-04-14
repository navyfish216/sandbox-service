package com.example.demo.sandbox.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sandbox.service.SandBoxService;
import com.example.demo.sandbox.web.response.SandBoxResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SandBoxController {

	@Autowired
	private SandBoxService sandBoxService;
	
	@GetMapping("/")
	public SandBoxResponse getString() {
		
		log.info("sandbox.controller.log.start");
		var response = new SandBoxResponse(sandBoxService.getString());
		log.info("sandbox.controller.log.end");
		
		return response;
	}
}
