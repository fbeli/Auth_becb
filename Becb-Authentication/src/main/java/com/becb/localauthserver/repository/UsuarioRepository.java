package com.becb.localauthserver.repository;

import java.util.Optional;
import java.util.UUID;

import com.becb.localauthserver.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

}