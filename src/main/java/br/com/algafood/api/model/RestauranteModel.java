 	package br.com.algafood.api.model;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteModel {

	private Long id;
	@NotBlank
	private String nome;
	@PositiveOrZero
	private BigDecimal taxaFrete;
	@Valid
	@NotNull
	private CozinhaModel cozinha;

	
}
