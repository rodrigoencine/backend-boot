package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.CategoriaDTO;
import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.domain.Categoria;
import com.example.demo.service.exceptions.DataIntegrityException;
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
	
	public Categoria update(Categoria categoria) {
		Categoria newCategoria = buscar(categoria.getId());
		this.updateData(newCategoria, categoria);
		return repo.save(newCategoria);
	}
	
	public void delete(Integer id) {
		this.buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
		
	}
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		return repo.findAll(pageRequest);
	}
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	private void updateData(Categoria newCategoria, Categoria categoria) {
		newCategoria.setNome(categoria.getNome());
	}
}
