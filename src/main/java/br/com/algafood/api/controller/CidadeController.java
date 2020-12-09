package br.com.algafood.api.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.algafood.domain.model.Cidade;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.EstadoNaoEncontradaException;
import br.com.algafood.domain.model.exception.NegocioException;
import br.com.algafood.domain.repository.CidadeRepository;
import br.com.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{id}")
	public Cidade budcar(@PathVariable Long id) {
		return cadastroCidade.BuscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
		try {
			return cadastroCidade.salvar(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Cidade salvar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
		try {
			Cidade cidadeAtual = cadastroCidade.BuscarOuFalhar(id);
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return cadastroCidade.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{id}")
	private void remover(@PathVariable Long Id) {
		cadastroCidade.remover(Id);
	}


	
	

}
