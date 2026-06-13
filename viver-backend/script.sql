-- Cria a base de dados do projeto VIVER+
DROP DATABASE IF EXISTS viver_db;
CREATE DATABASE viver_db;
USE viver_db;

-- Cria a tabela de utilizadores (agora com suporte à data de nascimento real)
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

-- ==========================================
-- NOVAS TABELAS PARA INTERAÇÃO SOCIAL
-- ==========================================

-- Tabela para guardar as Curtidas (Gostos)
CREATE TABLE curtidas (
    usuario_id INT NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (usuario_id, post_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES postagens(id) ON DELETE CASCADE
);

-- Tabela para guardar as Respostas (Comentários)
CREATE TABLE respostas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conteudo TEXT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES postagens(id) ON DELETE CASCADE
);

-- Insere um post de teste para o feed não ficar vazio
INSERT INTO postagens (conteudo, usuario_id) 
VALUES ('Olá, esta é a minha primeira publicação no VIVER+! Que alegria estar aqui.', 1);