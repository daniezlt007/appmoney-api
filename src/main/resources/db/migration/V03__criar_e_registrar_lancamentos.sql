create table lancamento(
codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
descricao VARCHAR(50) NOT NULL,
data_vencimento DATE NOT NULL,
data_pagamento DATE,
valor DECIMAL(10,2) NOT NULL,
observacao VARCHAR(50),
tipo VARCHAR(20) NOT NULL,
codigo_categoria BIGINT NOT NULL,
codigo_pessoa BIGINT NOT NULL, 
FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo),
FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo)
);

insert into lancamento (descricao,data_vencimento,data_pagamento,valor, observacao, tipo, codigo_categoria, codigo_pessoa) 
values 
('Pagamento Fies', '2021-03-15','2021-03-15',485.50,'Pagamento via banco','DESPESA', 6,1),
('Salário', '2021-03-10','2021-03-10',6500.0,'Salário do Mês','RECEITA', 10,1),
('Mesada', '2021-03-01','2021-03-05',130.0,'Mesada','DESPESA', 11,3),
('Mesada Esposa', '2021-03-10','2021-03-10',1000.0,'Mesada para gastos no shopping','DESPESA', 12,2);