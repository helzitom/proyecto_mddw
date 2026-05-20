package com.retrogaming.webapp.repository;

import com.retrogaming.webapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByUsuarioAndPassword(
            String usuario,
            String password
    );

    boolean existsByUsuario(String usuario);

    boolean existsByCorreo(String correo);
}