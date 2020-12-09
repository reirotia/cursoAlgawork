package br.com.algafood.api.exceptionHandler;

import java.lang.ref.Reference;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.com.algafood.domain.model.exception.EntidadeEmUsoException;
import br.com.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.model.exception.NegocioException;

@ControllerAdvice
public class apiExeceptionHandler extends ResponseEntityExceptionHandler {

	public static String MSG_ERRO_GENERICA_USUARIO_FINAL = "Algum erro interno ocorreu procure o administrador do sistema";
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		if (rootCause instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) rootCause, headers,
					status, request);
		}

		if (rootCause instanceof NoHandlerFoundException) {
			return handleNoHandlerFoundException((NoHandlerFoundException) rootCause, headers, status, request);
		}
		
		if (rootCause instanceof MethodArgumentNotValidException ) {
			return handleMethodArgumentNotValid((MethodArgumentNotValidException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSICEL;
		String detail = "O corpo da requisição esta inválido verifique";
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
	    		.map(FieldError -> Problem.Field.builder()
	    				.name(FieldError.getField())
	    				.userMessage(FieldError.getDefaultMessage())
	    				.build())
	    		.collect(Collectors.toList());
	    		
	        
	    Problem problem = createProblemBuilder(status, problemType, detail)
	        .userMessage(detail)
	        .fields(problemFields)	
	        .build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}      



	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = "Ocorreu um erro interno inesperado no sistema. "
				+ "Tente novamente e se o problema persistir, entre em contato " + "com o administrador do sistema.";

		// Importante colocar o printStackTrace (pelo menos por enquanto, que não
		// estamos
		// fazendo logging) para mostrar a stacktrace no console
		// Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam
		// importantes
		// para você durante, especialmente na fase de desenvolvimento
		ex.printStackTrace();

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {

		    return Problem.builder()
		        .timestamp(OffsetDateTime.now())
		        .status(status.value())
		        .type(problemType.getUri())
		        .title(problemType.getTitle())
		        .detail(detail);
		}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.PARAMENTRO_INVALIDO;

		String detail = String.format(
				"O parâmetro de URL '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADA;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSICEL;

		String path = joinPath(ex.getPath());

		String detail = String.format("A propriddade '%s' naõ existe corrija ou remova", path);

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	private String joinPath(List<com.fasterxml.jackson.databind.JsonMappingException.Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSICEL;
		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriddade '%s' Recebeu valor '%s', que é de um tipo invalido"
						+ "corrija e informe um valor compativel com o tipo %s",
				path, ex.getValue(), ex.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> trataEntidadeExeceptionHandler(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADA;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> entidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> trataNeocioExeceptionHandler(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
	    
	    if (body == null) {
	        body = Problem.builder()
	            .timestamp(OffsetDateTime.now())
	            .title(status.getReasonPhrase())
	            .status(status.value())
	            .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
	            .build();
	    } else if (body instanceof String) {
	        body = Problem.builder()
	            .timestamp(OffsetDateTime.now())
	            .title((String) body)
	            .status(status.value())
	            .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
	            .build();
	    }
	    
	    return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}
