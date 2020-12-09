create table forma_pagamento (
	id bigint not null auto_increment, 
	descricao varchar(200), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo (
	id bigint not null auto_increment, 
	nome varchar(200) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao (
	grupo_id bigint not null, 
	permissao_id bigint not null
) engine=InnoDB default charset=utf8;

create table permissao (
	id bigint not null auto_increment, 
	decricao varchar(200), 
	nome varchar(200), 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table produto (
	id bigint not null auto_increment, 
	ativo bit not null, 
	descricao varchar(200), 
	nome varchar(200) not null, 
	preco decimal(19,2) not null, 
	restaurante_id bigint not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante (
	id bigint not null auto_increment, 
	data_atualizacao datetime not null, 
	data_cadastro datetime not null, 
	endereco_bairro varchar(200), 
	endereco_cep varchar(200), 
	endereco_complemento varchar(200), 
	endereco_logradouro varchar(200), 
	endereco_numero varchar(200), 
	nome varchar(200) not null, 
	taxa_frete decimal(19,2), 
	cozinha_id bigint not null, 
	endereco_cidade_id bigint, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante_forma_pagamento (
	restaurante_id bigint not null, 
	forma_pagamento_id bigint not null
) engine=InnoDB default charset=utf8;

create table usuario (
	id bigint not null auto_increment, 
	data_cadastro datetime not null, 
	nome varchar(200) not null, 
	senha varchar(10) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario_grupo (
	usuario_id bigint not null, 
	grupo_id bigint not null
) engine=InnoDB default charset=utf8;
