package com.example.demo.sandbox.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SandBoxControllerIntegrationTest {

	@Autowired
	MockMvc mockMvc;
    
	@Test
	public void test_1() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
	}
	
}
