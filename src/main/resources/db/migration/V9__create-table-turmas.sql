CREATE TABLE turmas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    professor_id BIGINT,
    materia_id BIGINT,
    nome VARCHAR(255),
    FOREIGN KEY (professor_id) REFERENCES professores(id),
    FOREIGN KEY (materia_id) REFERENCES materias(id)
);
