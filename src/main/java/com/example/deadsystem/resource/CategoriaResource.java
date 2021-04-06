package com.example.deadsystem.resource;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.deadsystem.event.RecursoCriadoEvent;
import com.example.deadsystem.model.Categoria;
import com.example.deadsystem.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public CategoriaResource(CategoriaRepository categoriaRepository, ApplicationEventPublisher publisher) {
		super();
		this.categoriaRepository = categoriaRepository;
		this.publisher = publisher;
	}

	@GetMapping
	public ResponseEntity<?> listar(){
		List<Categoria> categorias = this.categoriaRepository.findAll();
		return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = this.categoriaRepository.save(categoria);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable("codigo") Long codigo) {
		Categoria categoria = this.categoriaRepository.findOne(codigo);
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}

}
