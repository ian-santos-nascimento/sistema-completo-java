package com.example.java_udemy.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private Integer cod;
	private String desc;
	
	TipoCliente(int cod, String desc) {
		this.cod = cod;
		this.desc = desc;
		
	}

	public int getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente .values()) {
			if ( cod.equals(x.getCod())) {
				return x; 
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
		
	}
	
	
}