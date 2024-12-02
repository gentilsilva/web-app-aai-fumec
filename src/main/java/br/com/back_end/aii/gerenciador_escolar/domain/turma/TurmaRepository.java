package br.com.back_end.aii.gerenciador_escolar.domain.turma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    @Query("SELECT c FROM Turma c WHERE (c.professor.id = :id)")
    Page<DadosListagemTurma> buscaPersonalizadaTurma(Long id, Pageable paginacao);

}
