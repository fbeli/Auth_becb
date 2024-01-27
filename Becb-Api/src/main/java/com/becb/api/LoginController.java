package com.becb.api;

import com.becb.api.security.AuthSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class LoginController   {

	@Autowired
	AuthSecurity authSecurity;

	@PreAuthorize("hasAuthority('HELLO')")
	@GetMapping("/hello")
//	@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
	@ResponseBody
	public String hello() {


		return "Hello, "+authSecurity.getName();
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/autorizado")
//	@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
	@ResponseBody
	public String autorizado() {


		return "autorizado, "+authSecurity.getName();
	}
	@GetMapping("/config")

	@ResponseBody
	public String config() {
		return "{ \"nome\": \"Fulano de Tal\", \"idade\": 30, \"endereco\": \"Rua Exemplo, 123\", \"telefone\": \"(00) 1234-5678\" }";
	}

}