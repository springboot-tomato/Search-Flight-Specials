package com.tomato.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ログインController
 * 
 * 
 */
@Controller
public class LoginController {
	
	/**
	 * login画面を表示
	 * 
	 * @return　login.html
	 */
	@GetMapping("/login")
	public String loginView() {
		return "loginView";
	}
}
