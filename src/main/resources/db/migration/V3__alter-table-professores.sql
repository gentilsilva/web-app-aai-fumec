ALTER TABLE professores
    ADD COLUMN cpf VARCHAR(14) UNIQUE NOT NULL,
    ADD COLUMN formacao ENUM("ADMINISTRACAO", "BIOMEDICINA", "CIENCIA_COMPUTACAO", "DIREITO", "DESIGN", "ESTETICA", "PSICOLOGIA") NOT NULL