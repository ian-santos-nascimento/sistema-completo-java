package com.example.java_udemy.domain.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer numero;
	private String role;
	
	
	Perfil(int numero, String role) {
		this.numero = numero;
		this.role = role;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static Perfil toEnum(Integer numero) {
		if(numero == null) {
			return null;
		}
		
		for(Perfil x : Perfil .values()) {
			if ( numero.equals(x.getNumero())) {
				return x; 
			}
		}
		throw new IllegalArgumentException("Id inválido: " + numero);
		
	}
	
}
