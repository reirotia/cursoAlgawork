create table pedido (
	id bigint auto_increment not null,
    subtotal decimal(10,2) not null,
    taxa_frete decimal(10,2) not null,
    valor_total decimal(10,2) not null,
    data_criacao datetime not null,
    dataConfirmacao datetime,
    dataCancelamento datetime,
    dataEntrega datetime,
    usuario_cliente_id bigint not null,
    restaurante_id bigint not null,
    statusPedido varchar(10) not null,
	forma_pagamento_id bigint not null,
	pedido_endereco_cidade_id bigint not null,
    endereco_cidade_id bigint(20) not null,
    endereco_cep varchar(9) not null,
    endereco_logradouro varchar(100) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60) null,
    endereco_bairro varchar(60) not null, 
    primary key (id), 
    constraint fk_pedido_endereco_cidade foreign key (pedido_endereco_cidade_id) references cidade(id),
    constraint fk_usuario_cliente foreign key (usuario_cliente_id) references usuario (id),
    constraint fk_restaurante foreign key (restaurante_id) references restaurante (id),
    constraint fk_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento(id)
) engine=InnoDB default charset=utf8;

create table item_pedido (
	id bigint auto_increment not null,
    quantidade bigint not null,
    preco_unitario decimal(10,2) not null,
    preco_total decimal(10,2) not null,
    observacao varchar(100),
    pedido_id bigint not null, 
    produto_id bigint not null,
    primary key (id),
    unique key uk_item_pedido_produto (pedido_id, produto_id),

    constraint fk_pedido foreign key (pedido_id) references pedido(id),
    constraint fk_produto foreign key (produto_id) references produto(id)
)engine=InnoDB default charset=utf8;
