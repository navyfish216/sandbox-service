package com.example.demo.sandbox.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.sahred.exception.ApplicationException;
import com.example.demo.sahred.util.ProcessUtility;
import com.example.demo.sandbox.service.SandBoxService;
import com.example.demo.sandbox.web.response.SandBoxResponse;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(SandBoxController.class)
@Import(ProcessUtility.class)
public class SandBoxControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	SandBoxService sandBoxService;

	@MockitoSpyBean
	ProcessUtility processUtility;

	@MockitoSpyBean
	MessageSource messageSource;

	@Test
	public void test_getString() throws Exception {

		var expect = new SandBoxResponse("Hello World!!");

		given(sandBoxService.getString()).willReturn("Hello World!!");

		MvcResult result = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		var actual = mapper.readValue(result.getResponse().getContentAsString(), SandBoxResponse.class);
		assertEquals(expect, actual);
	}

	@Test
	public void test_getErrorInfo_0() throws Exception {

		String errorCode = "0";
		var expect = new SandBoxResponse("Hello World!!");

		given(sandBoxService.getErrorInfo(errorCode)).willReturn("Hello World!!");

		MvcResult result = mockMvc.perform(get("/error/" + errorCode))
				.andExpect(status().isOk())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();
		var actual = mapper.readValue(result.getResponse().getContentAsString(), SandBoxResponse.class);
		assertEquals(expect, actual);
	}

	@Test
	public void test_getErrorInfo_1() throws Exception {

		String errorCode = "1";

		given(sandBoxService.getErrorInfo(errorCode)).willThrow(new NullPointerException());

		mockMvc.perform(get("/error/" + errorCode))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("エラーが発生しました。"));
	}

	@Test
	public void test_getErrorInfo_2() throws Exception {

		String errorCode = "2";

		given(sandBoxService.getErrorInfo(errorCode)).willThrow(new ApplicationException(errorCode));

		mockMvc.perform(get("/error/" + errorCode))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("アプリケーションエラーが発生しました。"));
	}
}
