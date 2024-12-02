CREATE TABLE matriculas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    universitario_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    data_matricula DATE NOT NULL,
    status ENUM("ATIVO", "TRANCADO") NOT NULL,

    FOREIGN KEY (universitario_id) REFERENCES universitarios(id) ON DELETE CASCADE,
    FOREIGN KEY (turma_id) REFERENCES turmas(id) ON DELETE CASCADE
);