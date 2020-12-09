package br.com.algafood.domain.model.exception;


public class RestauranteNaoEncontradaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradaException(Long id) {
		this(String.format("Restaurante não encontrado %d", id));
	}
}
