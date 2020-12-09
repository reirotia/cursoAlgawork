package br.com.algafood.domain.repository;

import java.util.List;

import br.com.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listar();

	FormaPagamento busca(Long id);

	FormaPagamento salvar(FormaPagamento formaPagamento);

	void remover(FormaPagamento formaPagamento);

}
