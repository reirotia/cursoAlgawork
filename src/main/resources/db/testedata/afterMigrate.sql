set foreign_key_checks=0;
delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;  

alter table cidade auto_increment=1;
alter table cozinha auto_increment=1;
alter table estado auto_increment=1;
alter table forma_pagamento auto_increment=1;
alter table grupo auto_increment=1;
alter table permissao auto_increment=1;
alter table produto auto_increment=1;
alter table restaurante auto_increment=1;
alter table usuario auto_increment=1;


insert into cozinha (nome) values ('brasileira');
insert into cozinha (nome) values ('japonesa');
insert into  forma_pagamento (id,descricao) values (1,'Cartão de Crédito');
insert into  forma_pagamento (id,descricao) values (2,'Cartão Débito');
insert into  forma_pagamento (id,descricao) values (3,'Dinheiro');
insert into estado(id,nome) values (1,'BA');
insert into estado(id,nome) values (2,'SE');
insert into estado(id,nome) values (3,'SP');
insert into cidade(id,nome,estado_id) values (1,'Salvador',1);
insert into cidade(id,nome,estado_id) values (2,'Feira de Santana',1);
insert into cidade(id,nome,estado_id) values (3,'Aracaju',2);
insert into cidade(id,nome,estado_id) values (4,'São Paulo',3);
insert into restaurante (id, nome, taxa_frete, cozinha_id,endereco_bairro,endereco_cep,endereco_logradouro,endereco_complemento,endereco_numero,endereco_cidade_id,data_cadastro,data_atualizacao) values (1, 'Thai Gourmet', 10, 1,'Jardins das Margaridas', '41502-200','Joaquim Ferreiro','não há','1287',1, UTC_timestamp,UTC_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id,data_cadastro,data_atualizacao) values (2, 'Thai Delivery', 9.50, 1,UTC_timestamp,UTC_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id,data_cadastro,data_atualizacao) values (3, 'Tuk Tuk Comida Indiana', 15, 2,UTC_timestamp,UTC_timestamp);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,1);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,2);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,3);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (2,3);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,1);
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);
set foreign_key_checks=1;
