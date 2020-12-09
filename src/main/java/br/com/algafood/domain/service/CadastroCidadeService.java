package br.com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.el.stream.Optional;

import br.com.algafood.domain.model.Cidade;
import br.com.algafood.domain.model.Estado;
import br.com.algafood.domain.model.exception.CidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.EntidadeEmUsoException;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.repository.CidadeRepository;
import br.com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private CadastroEstadoService cadasEstado;

	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadasEstado.BuscarOuFalhar(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	@Transactional
	public void remover(Long id) {
		try {
		
			cidadeRepository.deleteById(id);
		
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(id);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Entidade em uso %d", id));
		}
	}

	public Cidade BuscarOuFalhar(Long id) {
		return cidadeRepository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}
}
