package br.com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

}