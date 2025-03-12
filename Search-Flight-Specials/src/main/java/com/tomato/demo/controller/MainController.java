package com.tomato.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.tomato.demo.config.AmadeusConfig;
import com.tomato.demo.constant.urlConstant.UrlConstant;

@Controller
public class MainController {
	
	private final AmadeusConfig amadeusConfig;
	
	public MainController(AmadeusConfig amadeusConfig) {
        this.amadeusConfig = amadeusConfig;
    }

	/**
	 * main画面を表示
	 * 
	 * @return main.html
	 */
	@GetMapping(UrlConstant.MAIN)
	public String mainView(Model model) {
		model.addAttribute("key", amadeusConfig.getKey());
        model.addAttribute("secret", amadeusConfig.getSecret());
		return "index";
	}
}