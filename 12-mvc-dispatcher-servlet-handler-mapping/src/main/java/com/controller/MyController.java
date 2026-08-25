package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

	@GetMapping("/registration.do")
	public String registerPage() {

		System.out.println("MyController.test() method -- Test");

		return "register"; // name of "register.jsp"

	}

}
