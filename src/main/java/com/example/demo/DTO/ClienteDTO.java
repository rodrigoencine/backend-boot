package com.example.demo.DTO;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.example.demo.domain.Cliente;
import com.example.demo.service.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer Id;
	@NotEmpty(message ="Preenchimento obrigatorio")
	@Length(min=5, max=120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	@NotEmpty(message ="Preenchimento obrigatorio")
	@Email
	private String email;
	
	public ClienteDTO() {}
	
	public ClienteDTO(Cliente cliente) {
		this.Id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
