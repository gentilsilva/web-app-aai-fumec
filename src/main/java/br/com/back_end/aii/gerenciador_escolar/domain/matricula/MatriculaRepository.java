package br.com.back_end.aii.gerenciador_escolar.domain.matricula;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Query("SELECT m FROM Matricula m WHERE (m.universitario.id = :id)")
    Page<DadosListagemMatricula> buscaPersonalizadaTurma(Long id, Pageable paginacao);
}
