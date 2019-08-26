--
-- File generated with SQLiteStudio v3.2.1 on qui ago 15 14:54:47 2019
--
-- Text encoding used: UTF-8
--

DROP DATABASE IF EXISTS pizzapp;
CREATE DATABASE pizzapp;

USE pizzapp;

CREATE USER 'user'@'%' IDENTIFIED BY 'user12345';

GRANT ALL ON pizzapp.* TO 'user'@'%';

-- Table: clientes
DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (id INTEGER AUTO_INCREMENT, nome VARCHAR (100) NOT NULL, telefone VARCHAR (15) NOT NULL, ano_nascimento INTEGER NOT NULL, PRIMARY KEY(id));
INSERT INTO clientes (id, nome, telefone, ano_nascimento) VALUES (1, 'Zé', '1234', 2001);
INSERT INTO clientes (id, nome, telefone, ano_nascimento) VALUES (2, 'Chico', '45678', 2002);

-- Table: pedidos
DROP TABLE IF EXISTS pedidos;
CREATE TABLE pedidos (
    id        INTEGER  PRIMARY KEY AUTO_INCREMENT,
    idCliente INTEGER  REFERENCES clientes (id),
    dataHora  DATETIME,
    valor     DOUBLE
);
INSERT INTO pedidos (id, idCliente, dataHora, valor) VALUES (1, 1, '2019-08-08T23:59:45.478', 101.1);

-- Table: pedidospizza

-- Table: pizzas
DROP TABLE IF EXISTS pizzas;
CREATE TABLE pizzas (id INTEGER AUTO_INCREMENT, sabor VARCHAR (100) NOT NULL, valor DOUBLE NOT NULL, PRIMARY KEY(id));
INSERT INTO pizzas (id, sabor, valor) VALUES (2, 'mussarela', 10.0);
INSERT INTO pizzas (id, sabor, valor) VALUES (3, 'calabresa', 11.0);
INSERT INTO pizzas (id, sabor, valor) VALUES (4, 'portuguesa', 13.0);
INSERT INTO pizzas (id, sabor, valor) VALUES (5, 'Peperoni', 20.0);
INSERT INTO pizzas (id, sabor, valor) VALUES (6, 'Bacon', 15.0);
INSERT INTO pizzas (id, sabor, valor) VALUES (7, 'Palmito', 13.0);

DROP TABLE IF EXISTS pedidospizza;
CREATE TABLE pedidospizza (id INTEGER AUTO_INCREMENT, idPedido INTEGER REFERENCES pedidos (id) ON DELETE CASCADE, idPizza INTEGER REFERENCES pizzas (id), valor DOUBLE, PRIMARY KEY(id));
INSERT INTO pedidospizza (id, idPedido, idPizza, valor) VALUES (1, 1, 3, 5.0);
INSERT INTO pedidospizza (id, idPedido, idPizza, valor) VALUES (2, 1, 2, 4.0);
INSERT INTO pedidospizza (id, idPedido, idPizza, valor) VALUES (3, 1, 4, 10.0);

