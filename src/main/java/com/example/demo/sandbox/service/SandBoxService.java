package com.example.demo.sandbox.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
}
