package br.com.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.algafood.api.assembler.RestauranteModelAssembler;
import br.com.algafood.api.assembler.RestauranteModelDesabler;
import br.com.algafood.api.model.RestauranteModel;
import br.com.algafood.api.model.input.RestauranteInput;
import br.com.algafood.domain.model.Restaurante;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.NegocioException;
import br.com.algafood.domain.repository.RestauranteRepository;
import br.com.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired 
	private RestauranteModelAssembler restauranteModelAssembler;  
	
	@Autowired 
	private RestauranteModelDesabler restauranteModelDesabler;
	
	@GetMapping
	public List<RestauranteModel> listar() {

		List<Restaurante> restaurantes = restauranteRepository.findAll(); 
		return restauranteModelAssembler.toCollectionsModel(restaurantes);
	}

	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		Restaurante restaurante = cadastroRestaurante.BuscarOuFalhar(id);

		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restaurante = restauranteModelDesabler.toDomainObject(restauranteInput);
		
		return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
	}

	@PutMapping("/{id}")
	public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
		

		try {
			//Restaurante restaurante = restauranteModelDesabler.toDomainObject(restauranteInput);
			Restaurante restauranteAtual = cadastroRestaurante.BuscarOuFalhar(id);
			
			restauranteModelDesabler.copyToDomainObject(restauranteInput, restauranteAtual);
			//BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
			//		"produtos");			
			return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public void remover(@PathVariable Long id) {
		try {
			cadastroRestaurante.remover(id);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public RestauranteModel atualizarPacial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		
		/*
		Restaurante retauranteAtual = cadastroRestaurante.BuscarOuFalhar(id);
	
		
		merge(campos, retauranteAtual);
		try {
			return atualizar(id, retauranteAtual);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		*/
	return null;
	}
/*
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper obeMapper = new ObjectMapper();
		Restaurante restauranteOrigem = obeMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
*/

	
	

}
