create table cidade (
	id bigint not null auto_increment, 
	nome varchar(200) not null, 
	estado_id bigint not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

create table estado (
	id bigint not null auto_increment, 
	nome varchar(200) not null, 
	primary key (id)
) engine=InnoDB default charset=utf8;

