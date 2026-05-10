package com.example.demo.sandbox.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ArrayUtils;

import com.example.demo.sandbox.web.response.SandBoxResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/web")
@Slf4j
public class SandboxWebController {

	private boolean flag = false;

	@GetMapping({"", "/"})
	public ModelAndView getIndex(ModelAndView mav) {

		mav.addObject("msg", "名前を書いてください。");
		mav.setViewName("index");

		return mav;
	}

	@PostMapping({"", "/"})
	public ModelAndView postIndexForm(@RequestParam("text1") String str, ModelAndView mav) {

		mav.addObject("msg", "こんにちは、" + str + "さん！");
		mav.setViewName("index");

		return mav;
	}

	@GetMapping({"/{path}", "/{path}/"})
	public String getPage(@PathVariable String path, Model model) {

		model.addAttribute("msg", String.format("Called getPage %s", path));

		return switch (path) {
			case "index" -> path;
			default -> "other";
		};
	}

	@GetMapping({"/multi-form", "/multi-form/"})
	public ModelAndView getMultiForm(ModelAndView mav) {

		mav.addObject("msg", "フォームを送信ください。");
		mav.setViewName("multi-form/index");

		return mav;
	}

	@PostMapping({"/multi-form", "/multi-form/"})
	public ModelAndView postMultiForm(
			@RequestParam(required = false) boolean check1,
			@RequestParam(required = false) String radio1,
			@RequestParam(required = false) String select1,
			@RequestParam(required = false) String[] select2,
			ModelAndView mav) {

		String res = "";

		try {
			res = "check:" + check1
					+ " radio:" + radio1
					+ " select:" + select1
					+ "\nselect2:";
		} catch (NullPointerException e) {
		}

		if (!ArrayUtils.isEmpty(select2)) {
			res += String.join(", ", select2);
		} else {
			res += "no select";
		}

		mav.addObject("msg", res);
		mav.setViewName("multi-form/index");

		return mav;
	}

	@GetMapping({"/forward-and-redirect", "/forward-and-redirect/"})
	public ModelAndView getForwardAndRedirect(ModelAndView mav) {

		log.info("forward-and-redirect");

		// mav.addObject("msg", "フォワード／リダイレクト先のページです。");
		mav.setViewName("forward-and-redirect/index");

		return mav;
	}

	@GetMapping({"/redirect", "/redirect/"})
	public ModelAndView getRedirect() {

		// リダイレクトの場合、ブラウザのアドレスバーに表示されるURLが変わる
		log.info("redirect");

		var mav = new ModelAndView("redirect:/web/forward-and-redirect");
		mav.addObject("msg", "リダイレクト先のページです。"); // この内容はリダイレクト先に引き継がれない

		return mav;
	}

	@GetMapping({"/forward", "/forward/"})
	public ModelAndView getForward() {

		// フォワードの場合、ブラウザのアドレスバーに表示されるURLは変わらない
		log.info("forward");

		var mav = new ModelAndView("forward:/web/forward-and-redirect");
		mav.addObject("msg", "フォワード先のページです。"); // この内容はフォワード先に引き継がれる

		return mav;
	}

	@GetMapping({"/view-branch", "/view-branch/"})
	public ModelAndView getViewBranch(ModelAndView mav) {
		flag = !flag;
		mav.addObject("flag", flag);
		mav.addObject("msg", "サンプルのメッセージです。");
		mav.setViewName("view-branch/index");
		return mav;
	}

	@GetMapping({"/loop", "/loop/"})
	public ModelAndView getLoop(ModelAndView mav) {
		mav.addObject("msg", "データを表示します。");
		//		String[] data = new String[]{"One", "Two", "Three"};
		List<SandBoxResponse> data = List.of(
				new SandBoxResponse("One"),
				new SandBoxResponse("Two"),
				new SandBoxResponse("Three"));
		mav.addObject("data", data);
		mav.setViewName("loop/index");
		return mav;
	}

	@GetMapping({"/month/{month}", "/month/{month}/"})
	public ModelAndView getMonth(@PathVariable String month, ModelAndView mav) {
		mav.addObject("msg", month + "月は？");
		mav.addObject("month", month);
		mav.setViewName("month/index");
		return mav;
	}

}
