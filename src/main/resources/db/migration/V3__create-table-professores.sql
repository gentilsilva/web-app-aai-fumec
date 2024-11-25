CREATE TABLE professores(
    id CHAR(36)     NOT NULL,
    matricula VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    emailPessoal VARCHAR(255) NOT NULL,
    emailUniversitario VARCHAR(255) NOT NULL,
    ativo INTEGER,

    PRIMARY KEY(id)
);