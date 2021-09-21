package com.example.java_udemy.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.java_udemy.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
			
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
		try {
			
			//Faço input das credenciais com o request nos atributos da classe CredenciaisDTO
			CredenciaisDTO credenciais = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
			
			//Gero um token de autenticação passando meu email e senha dos atributos adquiridos no CredenciaisDTO
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciais.getEmail(),credenciais.getSenha(), new ArrayList<>());
			
			//Faço a verificação usando o Authentication e faço o retorno
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
			
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
		//Adquire o username da resposta do auth
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		
		//Transforma num token
		String token = jwtUtil.generateToken(username);
		
		//Adiciona ao Header da resposta
		res.addHeader("Authorization", "Bearer " + token);
	}
	
	
	//Caso retorne um erro, será tratado assim:
	
	private class JWTAuthenticationFailureHandler implements  AuthenticationFailureHandler {
		
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			//Adicionamos na mão o erro certo(como padrão ele retorna o erro errado)
			response.setStatus(404);
			
			//Tipo da resposta que iremos dar
			response.setContentType("application/json");
			
			//Escrevemos a resposta usando o "json" abaixo 
			response.getWriter().append(json());
		}
	
		private String json() {
			long  date = new Date().getTime();
			return "{\"timestamp\": " + date + ", "
	        + "\"status\": 401, "
	        + "\"error\": \"Não autorizado\", "
	        + "\"message\": \"Email ou senha inválidos\", "
	        + "\"path\": \"/login\"}";
			}
	}

}
