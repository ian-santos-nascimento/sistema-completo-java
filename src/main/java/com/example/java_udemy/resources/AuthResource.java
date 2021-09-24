package com.example.java_udemy.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_udemy.dto.EmailDTO;
import com.example.java_udemy.security.JWTUtil;
import com.example.java_udemy.security.UserSS;
import com.example.java_udemy.services.AuthService;
import com.example.java_udemy.services.UserService;


/**
 * Classe que gera um token para ser revalidado pela aplicação
 * @author Ian
 *
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired 
	private AuthService service;
	
	@RequestMapping(method = RequestMethod.POST, value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization"	, "Barer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/forgot")
	public ResponseEntity<String> forgot(@Valid @RequestBody EmailDTO objDTO){
		service.sendNewPasswordEmail(objDTO.getEmail());
		return ResponseEntity.ok().body("Email enviado com sucesso");
	
	}

}
