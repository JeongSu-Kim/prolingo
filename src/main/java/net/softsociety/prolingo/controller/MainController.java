package net.softsociety.prolingo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@GetMapping({"","/"})
	public String main() {
		return "main";
	}
	
	@GetMapping("courseTest")
	public String courseTest() {
		return "courseTest";
	}
}
