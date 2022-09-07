package net.softsociety.prolingo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping()
	public String home() {
		return "home";
	}
	
	@GetMapping("/test")
	public String test(){	
		return "test";
	}
}
