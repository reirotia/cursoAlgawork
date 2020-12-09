package br.com.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.algafood.domain.model.Estado;

public class CidadeMixin {
	
	
	@JsonIgnoreProperties(value ="nome", allowGetters = true)
	private Estado estado;


}
