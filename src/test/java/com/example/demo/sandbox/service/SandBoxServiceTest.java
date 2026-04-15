package com.example.demo.sandbox.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SandBoxServiceTest {

	@InjectMocks
	SandBoxService target;
	
	@Test
	public void test_SandBox() {
		var result = target.getString();
		assertEquals(result, "Hello World!!");
	}
	
}
