package com.example.demo.sandbox.service;

import java.time.Duration;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.sahred.exception.ApplicationException;
import com.example.demo.sahred.util.ProcessUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SandBoxService {

	@Autowired
	private ProcessUtility processUtility;
	
	@Autowired
	private MessageSource messageSource;
	
	public String getString() throws Exception {
		
		log.info(messageSource.getMessage("sandbox.service.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		var ret = "Hello World!!";
		log.info(messageSource.getMessage("sandbox.service.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		
		return ret;
	}
	
	public String getErrorInfo(String errorCode) throws Exception {
		
		log.info(messageSource.getMessage("sandbox.service.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		
		String ret = switch (errorCode) {
			case "1" -> throw new NullPointerException();
			case "2" -> throw new ApplicationException(errorCode);
			default -> getString();
		};
	 
		log.info(messageSource.getMessage("sandbox.service.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		
		return ret;
	}
	
	@Async("taskExecutor")
	public CompletableFuture<Integer> sleepRandom() throws Exception {

		log.info(messageSource.getMessage("sandbox.service.log.start", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		
		Random rand = new Random();
		int sleepSeconds = rand.nextInt(5) + 1;
		Thread.sleep(Duration.ofSeconds(sleepSeconds));
		
		log.info(messageSource.getMessage("sandbox.service.log.end", new String[]{processUtility.getProccessName()}, Locale.getDefault()));
		
		return CompletableFuture.completedFuture(sleepSeconds);
	}
}
