package br.com.back_end.aii.gerenciador_escolar.domain.materia;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query("""
            SELECT
                CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END
            FROM
                Materia m
            WHERE (m.codigo = :codigo OR m.semestre = :semestre) AND (:id IS NULL OR m.id <> :id)
            """)
    boolean jaCadastrado(Long id, String codigo, Integer semestre);

    List<Materia> findByFormacao(Formacao formacao);
}
