package br.com.algafood.domain.repository;

import java.util.List;

import br.com.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> listar();

	Permissao busca(Long id);

	Permissao salvar(Permissao permissao);

	void remover(Permissao permissao);
	
}
