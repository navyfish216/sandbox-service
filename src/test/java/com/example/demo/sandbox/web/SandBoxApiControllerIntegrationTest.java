package com.example.demo.sandbox.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.sandbox.web.response.SandBoxResponse;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SandBoxApiControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void test_getString() throws Exception {

		var expect = new SandBoxResponse("Hello World!!");

		MvcResult result = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		var actual = mapper.readValue(result.getResponse().getContentAsString(), SandBoxResponse.class);
		assertEquals(expect, actual);
	}

	@Test
	public void test_getErrorInfo_0() throws Exception {

		String errorCode = "0";
		var expect = new SandBoxResponse("Hello World!!");

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

		mockMvc.perform(get("/error/" + errorCode))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("エラーが発生しました。"));
	}

	@Test
	public void test_getErrorInfo_2() throws Exception {

		String errorCode = "2";

		mockMvc.perform(get("/error/" + errorCode))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("アプリケーションエラーが発生しました。"));
	}

	@Test
	public void test_getSleep() throws Exception {

		MvcResult result = mockMvc.perform(get("/sleep")).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		var actual = mapper.readValue(result.getResponse().getContentAsString(), SandBoxResponse.class);
		assertNotNull(actual);
	}
}
