CREATE TABLE universitarios(
    id BIGINT NOT NULL AUTO_INCREMENT,
    matricula VARCHAR(100) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    email_universitario VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    ativo SMALLINT NOT NULL,

    PRIMARY KEY(id)
);