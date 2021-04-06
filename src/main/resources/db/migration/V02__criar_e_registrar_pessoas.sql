create table pessoa(codigo bigint not null primary key auto_increment, nome varchar(50) not null, ativo TINYINT not null, 
logradouro varchar(75),numero varchar(8), complemento varchar(50), bairro varchar(45), cep varchar(8), 
cidade varchar(40), estado varchar(45));

insert into pessoa (nome, ativo, logradouro,numero,complemento,bairro,cep,cidade,estado) values 
('Daniel da Silva',true,'Passagem Princesa Isabel', '203', 'Entre 3 marias e 5ª linha', 'Tenoné', '66820020','Belém','Pará');
insert into pessoa (nome, ativo, logradouro,numero,complemento,bairro,cep,cidade,estado) values 
('Dulcinéia dos Santos Araújo',true,'Passagem Princesa Isabel', '203', 'Entre 3 marias e 5ª linha', 'Tenoné', '66820020','Belém','Pará');
insert into pessoa (nome, ativo, logradouro,numero,complemento,bairro,cep,cidade,estado) values 
('Alana Araújo Brabo',true,'Passagem Princesa Isabel', '203', 'Entre 3 marias e 5ª linha', 'Tenoné', '66820020','Belém','Pará');
