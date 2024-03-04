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
import java.util.UUID;

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



        if (usuarioDto.getGrupoId() == null){
                if( usuarioDto.getGuide())
                    usuarioDto.setGrupoId("2");
                else
                    usuarioDto.setGrupoId("1");
        }

        Grupo grupo = grupoRepository.findById(Long.parseLong(usuarioDto.getGrupoId())).orElse(null);
        usuarioDto.setGrupos(new HashSet<>());
        usuarioDto.addGrupo(grupo);
        assert grupo != null;
        grupo.addUser(usuarioDto.getUser());
        return usuarioDto.getUser();
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    public Usuario findById(UUID id) {
        return usuarioRepository.findById(id).orElse(null);
    }



}
