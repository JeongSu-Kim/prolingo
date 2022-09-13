package net.softsociety.prolingo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
//	@GetMapping({"","/"})
//	public String main() {
//		return "main";
//	}
	
	@GetMapping("courseTest")
	public String courseTest() {
		return "courseTest";
	}
	
	@GetMapping("header")
	public String header() {
		return "header";
	}
	
	@GetMapping("signUp")
	public String signUp() {
		log.debug("signUp called");
		return "signUp";
	}
}
