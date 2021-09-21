package com.example.java_udemy.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.java_udemy.security.JWTAuthenticationFilter;
import com.example.java_udemy.security.JWTAuthorizationFilter;
import com.example.java_udemy.security.JWTUtil;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserDetailsService detailsService;
	
	
	private static final String[] PUBLIC_MATCHERS	= {
		"/h2-console/**",
	};
	private static final String[] PUBLIC_MATCHES_GET = {
			"/produtos/**",
			"/categorias/**"
	};
	@Override
	protected void configure (HttpSecurity http) throws Exception {
		
		//Para o h2, se estiver no teste ele irá liberar o acesso ao banco h2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		//Desativar o CRSF e configurar o cors
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.GET, PUBLIC_MATCHES_GET).permitAll()      //Permite apenas o get 
		.antMatchers(PUBLIC_MATCHERS).permitAll()												 //Permite fazer tudo com essa url
		.anyRequest().authenticated();	
		
		//Adiciona o filtro que foi criado
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		//Adiciona o flter para autorizar
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, detailsService));

		//Aplicação stateless 
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	
	
	//Sobrecarga de método
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(detailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	//Pra liberar a cross forming
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	//Bean para criptografar 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		 return  new BCryptPasswordEncoder();
	}
}
