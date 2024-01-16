package com.becb.api;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class LoginController   {

	@GetMapping("/hello")
//	@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
	@ResponseBody
	public String hello() {
		return "Hello!";
	}
	@GetMapping("/config")
	@ResponseBody
	public String config() {
		return "{ \"nome\": \"Fulano de Tal\", \"idade\": 30, \"endereco\": \"Rua Exemplo, 123\", \"telefone\": \"(00) 1234-5678\" }";
	}

}