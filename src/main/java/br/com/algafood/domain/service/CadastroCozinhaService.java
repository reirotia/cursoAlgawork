package br.com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algafood.domain.model.Cozinha;
import br.com.algafood.domain.model.exception.CozinhaNaoEncontradaException;
import br.com.algafood.domain.model.exception.EntidadeEmUsoException;

import br.com.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_CONFLITO = "A cozinha de ID %d esta em uso";
	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CONFLITO, id));
		}
			
	}
	
	public Cozinha buscarOuFalhar(Long id) {
		return  cozinhaRepository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(id));

	}

}
