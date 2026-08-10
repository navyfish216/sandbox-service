package com.example.demo.sandbox.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.demo.sahred.exception.ApplicationException;
import com.example.demo.sahred.util.ProcessUtility;

@ExtendWith(MockitoExtension.class)
public class SandBoxServiceTest {

	@InjectMocks
	SandBoxService target;

	@BeforeEach
	public void beforeAll() {
		var messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("common-messages");
		ReflectionTestUtils.setField(target, "messageSource", messageSource);
		ReflectionTestUtils.setField(target, "processUtility", new ProcessUtility());
	}

	@Test
	public void test_SandBox() throws Exception {
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
