package br.com.back_end.aii.gerenciador_escolar.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByTokenIgnoreCase(String codigo);

    Optional<Usuario> findByEmailIgnoreCase(String email);
}
