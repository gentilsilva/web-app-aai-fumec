package br.com.back_end.aii.gerenciador_escolar.domain.professor;

import br.com.back_end.aii.gerenciador_escolar.domain.formacao.Formacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Page<Professor> findAllByAtivoTrue(Pageable paginacao);

    Optional<Professor> findByIdAndAtivoTrue(Long id);

    List<Professor> findByFormacao(Formacao formacao);

    @Query("""
            SELECT
                CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
            FROM
                Professor p
            WHERE (p.email = :email OR p.cpf = :cpf) AND (:id IS NULL OR p.id <> :id)
            """)
    boolean jaCadastrado(Long id, String cpf, String email);
}
