package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DTO.ClienteDTO;
import com.example.demo.DTO.ClienteNewDTO;
import com.example.demo.Repositories.ClienteRepository;
import com.example.demo.Repositories.EnderecoRepository;
import com.example.demo.domain.Cidade;
import com.example.demo.domain.Cliente;
import com.example.demo.domain.Endereco;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.domain.enums.TipoCliente;
import com.example.demo.security.UserSS;
import com.example.demo.service.exceptions.AuthorizationException;
import com.example.demo.service.exceptions.DataIntegrityException;
import com.example.demo.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		System.out.println(id);
		UserSS user = UserService.authenticated();
		System.out.println("KKKKKK" + user.getId());
		System.out.println("kjkjkj" + user.hasRole(Perfil.ADMIN));
		if(user == null || (!user.hasRole(Perfil.ADMIN) && !id.equals(user.getId()))) {
		
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrado! Id: " + id + "Tipo" + Cliente.class.getName()));
		
	}
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		cliente = clienteRepository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}
	
	public Cliente update(Cliente categoria) {
		Cliente newObj = buscar(categoria.getId());
		this.updateData(newObj, categoria);
		return clienteRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		this.buscar(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
		
	}
	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(),clienteDTO.getEmail(),null,null, null);
	}
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cliente = new Cliente(null,objDTO.getNome(),objDTO.getEmail(),objDTO.getCpfOuCnpj(),TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), 
				objDTO.getComplemento(), 
				objDTO.getBairro(), 
				objDTO.getCep(), 
				cliente, 
				cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() !=null) {
			cliente.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() !=null) {
			cliente.getTelefones().add(objDTO.getTelefone3());
		}
		return cliente;
				
	}
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
