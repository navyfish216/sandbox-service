package com.example.demo.sandbox.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import com.example.demo.sahred.util.ProcessUtility;
import com.example.demo.sandbox.controller.response.SandBoxResponse;

@WebMvcTest(SandBoxWebController.class)
@Import(ProcessUtility.class)
public class SandBoxWebControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void test_getString() throws Exception {

		mockMvc.perform(get("/web"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().attribute("msg", "名前を書いてください。"))
				.andReturn();
	}

	@Test
	public void test_postIndexForm() throws Exception {

		mockMvc.perform(post("/web?text1=test"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().attribute("msg", "こんにちは、testさん！"))
				.andReturn();
	}

	@Test
	public void test_getPage_1() throws Exception {

		mockMvc.perform(get("/web/index"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(model().attribute("msg", "Called getPage index"))
				.andReturn();
	}

	@Test
	public void test_getPage_2() throws Exception {

		mockMvc.perform(get("/web/hoge"))
				.andExpect(status().isOk())
				.andExpect(view().name("other"))
				.andExpect(model().attribute("msg", "Called getPage hoge"))
				.andReturn();
	}

	@Test
	public void test_getMultiForm() throws Exception {

		mockMvc.perform(get("/web/multi-form"))
				.andExpect(status().isOk())
				.andExpect(view().name("multi-form/index"))
				.andExpect(model().attribute("msg", "フォームを送信ください。"))
				.andReturn();
	}

	@Test
	public void test_postMultiForm_1() throws Exception {

		mockMvc.perform(post("/web/multi-form?check1=true&radio1=a&select1=b&select2=c&select2=d"))
				.andExpect(status().isOk())
				.andExpect(view().name("multi-form/index"))
				.andExpect(model().attribute("msg", "check : true\nradio : a\nselect1 : b\nselect2 : c, d"))
				.andReturn();
	}

	@Test
	public void test_postMultiForm_2() throws Exception {

		mockMvc.perform(post("/web/multi-form?check1=true&radio1=a&select1=b"))
				.andExpect(status().isOk())
				.andExpect(view().name("multi-form/index"))
				.andExpect(model().attribute("msg", "check : true\nradio : a\nselect1 : b\nselect2 : no select"))
				.andReturn();
	}

	@Test
	public void test_postMultiForm_3() throws Exception {

		mockMvc.perform(post("/web/multi-form"))
				.andExpect(status().isOk())
				.andExpect(view().name("multi-form/index"))
				.andExpect(model().attribute("msg", "check : false\nradio : null\nselect1 : null\nselect2 : no select"))
				.andReturn();
	}

	@Test
	public void test_getForwardAndRedirect() throws Exception {

		mockMvc.perform(get("/web/forward-and-redirect"))
				.andExpect(status().isOk())
				.andExpect(view().name("forward-and-redirect/index"))
				.andReturn();
	}

	@Test
	public void test_getRedirect() throws Exception {

		var param = UriUtils.encode("リダイレクト先のページです。", StandardCharsets.UTF_8);
		mockMvc.perform(get("/web/redirect"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/web/forward-and-redirect?msg=" + param))
				.andExpect(model().attribute("msg", "リダイレクト先のページです。"))
				.andReturn();
	}

	@Test
	public void test_getForward() throws Exception {

		mockMvc.perform(get("/web/forward"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("/web/forward-and-redirect"))
				.andExpect(model().attribute("msg", "フォワード先のページです。"))
				.andReturn();
	}

	@Test
	public void test_getViewBranch() throws Exception {

		mockMvc.perform(get("/web/view-branch"))
				.andExpect(status().isOk())
				.andExpect(view().name("view-branch/index"))
				.andExpect(model().attribute("flag", true))
				.andExpect(model().attribute("msg", "サンプルのメッセージです。"))
				.andReturn();
	}

	@Test
	@SuppressWarnings("unchecked")
	public void test_getLoop() throws Exception {

		MvcResult result = mockMvc.perform(get("/web/loop"))
				.andExpect(status().isOk())
				.andExpect(view().name("loop/index"))
				.andExpect(model().attribute("msg", "データを表示します。"))
				.andExpect(model().attributeExists("data"))
				.andReturn();

		ModelAndView mav = result.getModelAndView();
		List<SandBoxResponse> actual = (List<SandBoxResponse>) mav.getModel().get("data");
		List<SandBoxResponse> expect = List.of(
				new SandBoxResponse("One"),
				new SandBoxResponse("Two"),
				new SandBoxResponse("Three"));

		assertEquals(expect, actual);
	}

	@Test
	public void test_getMonth() throws Exception {

		mockMvc.perform(get("/web/month/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("month/index"))
				.andExpect(model().attribute("msg", "1月は？"))
				.andExpect(model().attribute("month", "1"))
				.andReturn();
	}
}
