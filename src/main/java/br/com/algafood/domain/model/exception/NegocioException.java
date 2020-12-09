package br.com.algafood.domain.model.exception;


public class NegocioException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String message, Throwable causa) {
		super(message,causa);
	}
	
}
