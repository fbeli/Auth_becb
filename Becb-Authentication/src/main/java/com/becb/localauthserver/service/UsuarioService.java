package com.becb.localauthserver.service;

import com.becb.localauthserver.domain.Grupo;
import com.becb.localauthserver.domain.ResetRequest;
import com.becb.localauthserver.dto.ResetRequestDto;
import com.becb.localauthserver.repository.GrupoRepository;
import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.repository.ResetRepository;
import com.becb.localauthserver.repository.UsuarioRepository;
import com.becb.localauthserver.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ResetRepository resetRepository;

    @Autowired
    GrupoRepository grupoRepository;

    public Usuario save(UsuarioDto usuarioDto) throws SQLIntegrityConstraintViolationException {

        usuarioDto.setPassword(encriptPassword(usuarioDto.getPassword()));

        return usuarioRepository.save(convertToEntity(usuarioDto));
    }
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
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


    public ResetRequest saveToReset(ResetRequest resetRequest) {
        return resetRepository.save(resetRequest);
    }

    private String encriptPassword(String password){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode( password);
    }
    public ResetRequestDto changePassword(ResetRequestDto resetRequestDto) {
        List<ResetRequest> resetRequests = resetRepository.findByEmail(resetRequestDto.getEmail());
        if (resetRequests == null) {
            resetRequestDto.setMessage("Reset to Email not found, ask for it again");
            resetRequestDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }else {
            ResetRequest request = resetRequests.stream()
                    .filter(resetRequest -> {
                        return resetRequestDto.getCode().equals(resetRequest.getCode());
                    })
                    .filter(resetRequest -> {
                        return resetRequest.getRequestDate().isAfter(LocalDateTime.now().minusMinutes(30));
                    }).findFirst().orElse(null);


            if (request == null) {
                resetRequestDto.setMessage("Code is not valid");
                resetRequestDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }else {
                Usuario usuario = this.findByEmail(request.getEmail());
                usuario.setPassword(encriptPassword(resetRequestDto.getPassword()));
                this.save(usuario);
                resetRequestDto.setMessage("Password changed successfully");
                resetRequestDto.setStatus(HttpStatus.OK.value());
            }
        }
        return resetRequestDto;
    }




}
