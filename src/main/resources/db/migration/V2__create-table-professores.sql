CREATE TABLE professores(
    id BIGINT NOT NULL AUTO_INCREMENT,
    matricula VARCHAR(100) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    email_universitario VARCHAR(100) NOT NULL,
    ativo SMALLINT NOT NULL,

    PRIMARY KEY(id)
);