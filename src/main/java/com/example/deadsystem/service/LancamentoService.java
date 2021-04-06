package com.example.deadsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.deadsystem.model.Lancamento;
import com.example.deadsystem.model.Pessoa;
import com.example.deadsystem.repository.LancamentoRepository;
import com.example.deadsystem.repository.PessoaRepository;
import com.example.deadsystem.service.exception.PessoaInexistenteOuInativoException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = this.pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativoException();
		}
		return this.lancamentoRepository.save(lancamento);
	}
	
}
