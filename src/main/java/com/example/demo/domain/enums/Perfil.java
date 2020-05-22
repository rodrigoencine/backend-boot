package com.example.demo.domain.enums;

public enum Perfil {
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer id;
	private String descricao;
	private Perfil(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Perfil estado : Perfil.values()) {
			if(estado.getId().equals(cod)) {
				return estado;
			}
		}
		throw new IllegalArgumentException("Id invalido" + cod);
	}
}
