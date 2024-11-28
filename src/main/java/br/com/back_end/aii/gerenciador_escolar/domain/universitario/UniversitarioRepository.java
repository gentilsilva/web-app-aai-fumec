package br.com.back_end.aii.gerenciador_escolar.domain.universitario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversitarioRepository extends JpaRepository<Universitario, Long> {
    Page<Universitario> findAllByAtivoTrue(Pageable paginacao);

    Optional<Universitario> findByIdAndAtivoTrue(Long id);
}
