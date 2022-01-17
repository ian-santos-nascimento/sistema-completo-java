package com.example.java_udemy.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,JWTUtil jwtUtil,UserDetailsService userDetailsService ) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	
	//Irá filtrar o token, pegar o usuário passado e buscar no banco para verificar se já existe
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
		
		//Pega o nome da autorização passada no Header no request 
		String header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer ")) {
			
			//getAuthentication() vai receber o token passado na request e armazenado no auth do springSecurity
			//Caso seja válido é passado, caso não retorna nulo
			
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			
			if(auth != null) {
				
				//Permite a autenticação do token
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		//Aqui irá permitir a passagem normal para o resto da aplicação
		chain.doFilter(request, response);
	}


	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		//Verifica se é valido, busca pelo usuario no banco e retorna com suas autorizações
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		}
		return null;
	}


}
