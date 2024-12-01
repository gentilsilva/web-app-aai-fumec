package br.com.back_end.aii.gerenciador_escolar.domain.professor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Page<Professor> findAllByAtivoTrue(Pageable paginacao);

    Optional<Professor> findByIdAndAtivoTrue(Long id);

    List<Professor> findByFormacao(Formacao formacao);
}
