package com.becb.localauthserver.service;

import com.becb.localauthserver.domain.Grupo;
import com.becb.localauthserver.domain.GrupoRepository;
import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.domain.UsuarioRepository;
import com.becb.localauthserver.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    GrupoRepository grupoRepository;

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
        Grupo grupo = new Grupo();

        if (usuarioDto.getGrupoId() == null) {
            usuarioDto.setGrupoId("2");
        }
            grupo = grupoRepository.findById(Long.parseLong(usuarioDto.getGrupoId())).orElse(null);
            usuario.setGrupos(new HashSet<>());
            usuario.addGrupo(grupo);

        return usuario;
    }


}
