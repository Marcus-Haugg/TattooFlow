-- Schema TattooFlow
-- Execute este script no banco de dados 'tattooflow' para recriar todas as tabelas

CREATE TABLE IF NOT EXISTS usuarios (
    id    SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS estilos (
    id          SERIAL PRIMARY KEY,
    nome_estilo VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS clientes (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    cpf       VARCHAR(14)  NOT NULL,
    email     VARCHAR(100) NOT NULL,
    celular   VARCHAR(14),
    data_nasc VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS tatuadores (
    id      SERIAL PRIMARY KEY,
    nome    VARCHAR(100) NOT NULL,
    email   VARCHAR(100) NOT NULL,
    celular VARCHAR(14)
);

CREATE TABLE IF NOT EXISTS tatuagens (
    id          SERIAL PRIMARY KEY,
    tatuador_id INTEGER NOT NULL,
    estilo_id   INTEGER NOT NULL,
    descricao   VARCHAR(500),
    data_criacao VARCHAR(10),
    CONSTRAINT fk_tatuagens_tatuador FOREIGN KEY (tatuador_id) REFERENCES tatuadores(id),
    CONSTRAINT fk_tatuagens_estilo   FOREIGN KEY (estilo_id)   REFERENCES estilos(id)
);

CREATE TABLE IF NOT EXISTS sessoes (
    id               SERIAL PRIMARY KEY,
    cliente_id       INTEGER NOT NULL,
    tatuador_id      INTEGER NOT NULL,
    tatuagem_id      INTEGER NOT NULL,
    data_hora        VARCHAR(20),
    duracao_minutos  VARCHAR(10),
    descricao        VARCHAR(500),
    CONSTRAINT fk_sessoes_cliente  FOREIGN KEY (cliente_id)  REFERENCES clientes(id),
    CONSTRAINT fk_sessoes_tatuador FOREIGN KEY (tatuador_id) REFERENCES tatuadores(id)
);

-- Inserir usuario padrao (senha: admin)
INSERT INTO usuarios (email, senha)
SELECT 'admin@tattooflow.com', MD5('admin')
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE email = 'admin@tattooflow.com');
