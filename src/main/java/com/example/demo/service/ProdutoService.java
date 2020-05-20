package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.Repositories.ProdutoRepository;
import com.example.demo.domain.Categoria;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Produto;
import com.example.demo.service.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrado" + id + "tipo" + Pedido.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,
			Integer page, 
			Integer linesPerPage, 
			String orderBy, 
			String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
		List<Categoria> categorias = this.categoriaRepository.findAllById(ids); 
		
		return produtoRepository.search(nome, categorias, pageRequest);
		
	}
}
