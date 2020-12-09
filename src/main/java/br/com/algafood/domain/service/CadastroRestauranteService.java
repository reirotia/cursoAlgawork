package br.com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algafood.domain.model.Cozinha;
import br.com.algafood.domain.model.Restaurante;
import br.com.algafood.domain.model.exception.EntidadeEmUsoException;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.RestauranteNaoEncontradaException;
import br.com.algafood.domain.repository.CozinhaRepository;
import br.com.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository cadastroRestaurante;

	@Autowired
	private CadastroCozinhaService cozinhaCadastro;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaCadastro.buscarOuFalhar(cozinhaId);
		restaurante.setCozinha(cozinha);
		return cadastroRestaurante.save(restaurante);
	}

	@Transactional
	public void remover(Long id) {

		try {
			cadastroRestaurante.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradaException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Entidade em uso %d ", id));
		}
	}

	public Restaurante BuscarOuFalhar(Long id) {
		return cadastroRestaurante.findById(id).orElseThrow(() -> new RestauranteNaoEncontradaException(id));
	}
}
