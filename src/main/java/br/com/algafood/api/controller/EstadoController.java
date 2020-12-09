package br.com.algafood.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algafood.api.exceptionHandler.Problem;
import br.com.algafood.domain.model.Estado;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.NegocioException;
import br.com.algafood.domain.repository.EstadoRepository;
import br.com.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {


	@Autowired
	private CadastroEstadoService estadoService;

	@GetMapping
	public List<Estado> listar() {

		return estadoService.listar();
	}

	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return estadoService.BuscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado Adicionar(@RequestBody @Valid Estado estado) {
		return estadoService.salvar(estado);
	}

	@PutMapping("/{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {

		Estado estadoAtual = estadoService.BuscarOuFalhar(id);
		BeanUtils.copyProperties(estado, estadoAtual);
		return  estadoService.salvar(estadoAtual) ;

	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void Excluir(@PathVariable Long id) {
		estadoService.remover(id);
	}
	


}
