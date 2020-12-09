package br.com.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.algafood.api.model.input.RestauranteInput;
import br.com.algafood.domain.model.Cozinha;
import br.com.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelDesabler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		modelMapper.map(restauranteInput, restaurante);
	}
}
