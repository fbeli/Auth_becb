package com.becb.localauthserver.controller;

import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.dto.UsuarioDto;
import com.becb.localauthserver.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

@RestController
public class UserController {


    @Autowired
    UsuarioService usuarioService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/cadastro")
    public CadastroUsuarioResponse cadastroUsuario(@RequestBody UsuarioDto usuario, HttpServletResponse response) {

        logger.info("Receiving new user: {}", usuario.toString());
        CadastroUsuarioResponse cadastroResponse = new CadastroUsuarioResponse();

        try{
            Usuario savedUser = usuarioService.save(usuario);
            cadastroResponse.setId(savedUser.getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            cadastroResponse.setErro(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }catch (Exception e) {
            if(e.getCause().getCause().getMessage() != null && e.getCause().getCause().getMessage().startsWith(
                    "Duplicate")){
                cadastroResponse.setErro("user already exists");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }else {
                cadastroResponse.setErro(e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
       return cadastroResponse;
    }

    @GetMapping("/health")
    public String health() {
        return "true";
    }

    @GetMapping("/user/get_by_id/{id}")
    public UsuarioDto getUser(@PathVariable String id) {

        Usuario user = usuarioService.findById(UUID.fromString(id));
        UsuarioDto dto = null;
        if(user != null)
            dto = new UsuarioDto(user);

        return dto;

    }

    @GetMapping("/user/get_by_email/")
    public UsuarioDto getUserByEmail(@RequestParam String email) {

        Usuario user = usuarioService.findByEmail(email);
        UsuarioDto dto = null;
        if(user != null)
            dto = new UsuarioDto(user);

        return dto;

    }

}
