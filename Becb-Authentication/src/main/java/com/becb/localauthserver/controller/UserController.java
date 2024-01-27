package com.becb.localauthserver.controller;

import com.becb.localauthserver.dto.UsuarioDto;
import com.becb.localauthserver.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class UserController {


    @Autowired
    UsuarioService usuarioService;
    @PostMapping("/cadastro")
    public CadastroUsuarioResponse cadastroUsuario(@RequestBody UsuarioDto usuario, HttpServletResponse response) {

        CadastroUsuarioResponse cadastroResponse = new CadastroUsuarioResponse();
        try{
            cadastroResponse.setId(usuarioService.save(usuario).getId());
        } catch (SQLIntegrityConstraintViolationException e) {
            cadastroResponse.setErro(e.getMessage());
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }catch (Exception e) {
            if(e.getCause().getCause().getMessage().startsWith("Duplicate")){
                cadastroResponse.setErro("user already exists");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
            }else {
                cadastroResponse.setErro(e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
       return cadastroResponse;
    }
}
