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

import com.example.demo.sandbox.exception.ApplicationException;
import com.example.demo.sandbox.service.SandBoxService;
import com.example.demo.sandbox.util.ProcessUtility;
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
        assertEquals(expect.getMessage(), actual.getMessage());
	}
	
	@Test
	public void test_getErrorInfo_0() throws Exception {
		var expect = new SandBoxResponse("Hello World!!");
        given(sandBoxService.getErrorInfo("0")).willReturn("Hello World!!");
        MvcResult result = mockMvc.perform(get("/error/0"))
        	.andExpect(status().isOk())
        	.andReturn();
        
        ObjectMapper mapper = new ObjectMapper();
        var actual = mapper.readValue(result.getResponse().getContentAsString(), SandBoxResponse.class);
        assertEquals(expect.getMessage(), actual.getMessage());
	}
	
	@Test
	public void test_getErrorInfo_1() throws Exception {
        given(sandBoxService.getErrorInfo("1")).willThrow(new NullPointerException());
        mockMvc.perform(get("/error/1"))
        	.andExpect(status().isInternalServerError())
        	.andExpect(content().string("エラーが発生しました。"));
	}
	
	@Test
	public void test_getErrorInfo_2() throws Exception {
        given(sandBoxService.getErrorInfo("2")).willThrow(new ApplicationException("2"));
        mockMvc.perform(get("/error/2"))
        	.andExpect(status().isBadRequest())
        	.andExpect(content().string("アプリケーションエラーが発生しました。"));
	}
}
