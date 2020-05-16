package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.domain.Categoria;
import com.example.demo.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
	   Optional<Categoria> obj = repo.findById(id);
	   
	   return obj.orElseThrow(() -> new ObjectNotFoundException(
			   "Objeto não encontrado! Id:" + id + ", Tipo:" + Categoria.class));
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}
}
