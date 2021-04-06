package com.example.deadsystem.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.deadsystem.model.Pessoa;
import com.example.deadsystem.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public PessoaService(PessoaRepository pessoaRepository) {
		super();
		this.pessoaRepository = pessoaRepository;
	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = this.pessoaRepository.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return this.pessoaRepository.save(pessoaSalva);
	}
	
	public void atualizaPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		this.pessoaRepository.save(pessoaSalva);
	}
	
	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = this.pessoaRepository.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
	
	
	
}
