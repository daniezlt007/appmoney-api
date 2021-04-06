package com.example.deadsystem.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.deadsystem.event.RecursoCriadoEvent;
import com.example.deadsystem.exceptionhandler.ExceptionHandler.Erro;
import com.example.deadsystem.model.Lancamento;
import com.example.deadsystem.repository.LancamentoRepository;
import com.example.deadsystem.repository.filter.LancamentoFilter;
import com.example.deadsystem.service.LancamentoService;
import com.example.deadsystem.service.exception.PessoaInexistenteOuInativoException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;
	
	public LancamentoResource(LancamentoRepository lancamentoRepository, LancamentoService lancamentoService,
			ApplicationEventPublisher publisher) {
		super();
		this.lancamentoRepository = lancamentoRepository;
		this.lancamentoService = lancamentoService;
		this.publisher = publisher;
	}

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return this.lancamentoRepository.filtrar(lancamentoFilter, pageable); 
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> criarLancamentos(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalva = this.lancamentoService.salvar(lancamento);
		this.publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable("codigo") Long codigo) {
		this.lancamentoRepository.delete(codigo);
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativoException.class})
	public ResponseEntity<Object> handlePessoaInexistenteOuInativoException(PessoaInexistenteOuInativoException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
}
