package br.com.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import br.com.algafood.domain.model.Cozinha;
import br.com.algafood.domain.model.FormaPagamento;
import br.com.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<FormaPagamento> listar() {
		return manager.createQuery("from cozinha", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento busca(Long id) {
		return manager.find(FormaPagamento.class, id);
	}

	@Override
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return manager.merge(formaPagamento);
	}

	@Override
	public void remover(FormaPagamento formaPagamento) {
		formaPagamento = busca(formaPagamento.getId());
		manager.remove(formaPagamento);
	}

}
