package com.becb.localauthserver.service;

import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.domain.UsuarioRepository;
import com.becb.localauthserver.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    public Usuario save(UsuarioDto usuarioDto) throws SQLIntegrityConstraintViolationException {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuarioDto.setPassword(encoder.encode( usuarioDto.getPassword()));
        return usuarioRepository.save(convertToEntity(usuarioDto));
    }

    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setName(usuarioDto.getName());
        usuario.setPassword(usuarioDto.getPassword());
        return usuario;
    }


}
