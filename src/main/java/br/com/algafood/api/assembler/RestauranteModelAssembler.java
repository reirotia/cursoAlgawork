package br.com.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.algafood.api.model.CozinhaModel;
import br.com.algafood.api.model.RestauranteModel;
import br.com.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public RestauranteModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteModel.class);
	}

	public List<RestauranteModel> toCollectionsModel(List<Restaurante> restaurantes) {
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
}
