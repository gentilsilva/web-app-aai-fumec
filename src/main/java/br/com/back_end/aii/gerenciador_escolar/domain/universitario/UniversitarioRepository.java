package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UniversitarioRepository extends JpaRepository<Universitario, Long> {
    Page<Universitario> findAllByAtivoTrue(Pageable paginacao);

    Optional<Universitario> findByIdAndAtivoTrue(Long id);

    @Query("""
            SELECT
                CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END
            FROM
                Universitario u
            WHERE (u.email = :email OR u.cpf = :cpf) AND (:id IS NULL OR u.id <> :id)
            """)
    boolean jaCadastrado(Long id, String cpf, String email);
}
