package net.softsociety.prolingo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {

//	@Autowired
//	MemberService service;
	
	@GetMapping("login")
	public String login() {
		
		log.debug("만들어야함");
		
		return "loginForm";
	}
}
