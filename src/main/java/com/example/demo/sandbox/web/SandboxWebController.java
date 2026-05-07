package com.example.demo.sandbox.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web")
public class SandboxWebController {

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
		
		return switch(path) {
			case "index" -> path;
			default -> "other";
		};
	}
	
}
