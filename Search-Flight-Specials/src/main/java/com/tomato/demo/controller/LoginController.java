package com.tomato.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.tomato.demo.form.LoginForm;

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
	public String loginView(LoginForm form) {
		System.out.println(form.getLoginId());
		return "loginView";
	}
	
	/**
	 * loginViewからデータを受信
	 * 
	 */
	@PostMapping("/login")
	public void login(Model model, LoginForm form) {
	  System.out.println("ログイン");
	  System.out.println(form.getLoginId());
	  
	}
}
