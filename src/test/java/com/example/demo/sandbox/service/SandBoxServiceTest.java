package com.example.demo.sandbox.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.example.demo.sandbox.exception.ApplicationException;
import com.example.demo.sandbox.util.ProcessUtility;

@ExtendWith(MockitoExtension.class)
public class SandBoxServiceTest {

	@Spy
	ProcessUtility processUtility;
	
	@Spy
	MessageSource messageSource;
	
	@InjectMocks
	SandBoxService target;
	
	@Test
	public void test_SandBox() {
		var result = target.getString();
		assertEquals(result, "Hello World!!");
	}
	
	@Test
	public void test_getErrorInfo_0() throws Exception {
		var result = target.getErrorInfo("0");
		assertEquals(result, "Hello World!!");
	}
	
	@Test
	public void test_getErrorInfo_1() throws Exception {
        assertThrows(NullPointerException.class, () -> {
        	target.getErrorInfo("1");
        });
	}
	
	@Test
	public void test_getErrorInfo_2() throws Exception {
		ApplicationException exception = assertThrows(ApplicationException.class, () -> {
        	target.getErrorInfo("2");
        });
		assertTrue(exception.getMessage().contains("2"));
	}
	
}
