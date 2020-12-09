package br.com.algafood.domain.model.exception;



public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException(Long id) {
		this(String.format("Cidade não encontrado %d", id));
	}
}
