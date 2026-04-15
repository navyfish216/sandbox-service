package com.example.demo.sandbox.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.sandbox.service.SandBoxService;

@WebMvcTest(SandBoxController.class)
public class SandBoxControllerTest {
	
	@Autowired
	MockMvc mockMvc;
    
	@MockitoBean
	SandBoxService sandBoxService;

	@Test
	public void test_1() throws Exception {
        given(sandBoxService.getString()).willReturn("Hello World!!");
        mockMvc.perform(get("/")).andExpect(status().isOk());
	}
	
}
