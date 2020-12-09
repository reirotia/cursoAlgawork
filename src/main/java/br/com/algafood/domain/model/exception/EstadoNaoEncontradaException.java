package br.com.algafood.domain.model.exception;


public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradaException(Long id) {
		this(String.format("Estado não encontrado %d", id));
	}
}
