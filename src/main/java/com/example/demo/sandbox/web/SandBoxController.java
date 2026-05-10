package com.example.demo.sandbox.web;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.sahred.util.ProcessUtility;
import com.example.demo.sandbox.service.SandBoxService;
import com.example.demo.sandbox.web.response.SandBoxResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SandBoxController {

	@Autowired
	private SandBoxService sandBoxService;

	@Autowired
	private ProcessUtility processUtility;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/")
	public SandBoxResponse getString() throws Exception {

		log.info(messageSource.getMessage("sandbox.controller.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		var response = new SandBoxResponse(sandBoxService.getString());
		log.info(messageSource.getMessage("sandbox.controller.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));

		return response;
	}

	@GetMapping("/error/{errorCode}")
	public SandBoxResponse getErrorInfo(@PathVariable String errorCode) throws Exception {

		log.info(messageSource.getMessage("sandbox.controller.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		var response = new SandBoxResponse(sandBoxService.getErrorInfo(errorCode));
		log.info(messageSource.getMessage("sandbox.controller.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));

		return response;
	}

	@GetMapping("/sleep")
	public SandBoxResponse getSleep() throws Exception {

		log.info(messageSource.getMessage("sandbox.controller.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));

		CompletableFuture<Integer> sleepSeconds1 = sandBoxService.sleepRandom();
		CompletableFuture<Integer> sleepSeconds2 = sandBoxService.sleepRandom();
		CompletableFuture.allOf(sleepSeconds1, sleepSeconds2).join();

		var response = new SandBoxResponse();
		response.setMessage(String.format("1回目のSleep時間：%d、2回目のSleep時間：%d。", sleepSeconds1.get(), sleepSeconds2.get()));

		log.info(messageSource.getMessage("sandbox.controller.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));

		return response;
	}

}
