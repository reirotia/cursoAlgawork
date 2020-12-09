package br.com.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_INCOMPREENSICEL("/MENSAGEM-INCOMPREENSIVEL","MENSAGEM INCOMPREENSIVEL"),
	RECURSO_NAO_ENCONTRADA ("/Entidade-na0-encontrada", "Entidade Não Encontrada"),
	ENTIDADE_EM_USO("/Entidade-na0-encontrada", "Entidade Não Encontrada"),
	PARAMENTRO_INVALIDO("/PARAMENTO-IVALIDO", "Paramentro invalido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/DADOS-INVALIDO", "Dados inválidos"),
	ERRO_NEGOCIO("/Entidade-erro-negocio", "Erro de negócio");
	
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri= "https://reinaldo" + path;
		this.title = title;
	}
}
