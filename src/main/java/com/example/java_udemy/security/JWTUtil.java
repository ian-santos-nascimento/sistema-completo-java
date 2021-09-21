package com.example.java_udemy.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component  //Pq pode ser injetada em outras classes
public class JWTUtil {

	//Importados do aplication.properties
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
				}
	
	public boolean tokenValido(String token) {
		
		//Esse Claims adquire a reivindicação do token(username e o tempo de sessão)
		Claims claims = getClaims(token);
		
		//Recupera os atributos do token, verificando antes se eles são válidos(diferente de null)
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(username != null && expirationDate != null &&  now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			String username = claims.getSubject();
			return username;
		}
		return null;
	}

	private Claims getClaims(String token) {
		
		try {
			//Recupera os claimns a partir do token(Se for inválido lança uma excessão)
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}catch(Exception e) {
			return null;
		}
	}

}
