-- Cria a base de dados do projeto VIVER+
DROP DATABASE IF EXISTS viver_db;
CREATE DATABASE viver_db;
USE viver_db;

-- Cria a tabela de utilizadores
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    data_nascimento DATE NOT NULL
);

-- Insere uma utilizadora de teste para podermos fazer o primeiro login
INSERT INTO usuarios (nome, email, senha, data_nascimento) 
VALUES ('Dona Maria', 'maria@viver.com', '1234', '1950-05-20');

-- Cria a tabela de postagens (Feed de Momentos)
CREATE TABLE postagens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conteudo TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);