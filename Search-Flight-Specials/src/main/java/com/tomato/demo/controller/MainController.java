package com.tomato.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	/**
	 * main画面を表示
	 * 
	 * @return main.html
	 */
	@GetMapping("/")
	public String mainView() {
		return "index";
	}
}
