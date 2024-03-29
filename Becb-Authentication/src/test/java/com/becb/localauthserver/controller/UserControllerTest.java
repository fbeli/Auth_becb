package com.becb.localauthserver.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.dto.UsuarioDto;
import com.becb.localauthserver.service.UsuarioService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;


@WebMvcTest(UserController.class)
class UserControllerTest {

    final String CADASTRO_ENDPOINT = "/cadastro";
    final String GETUSER_ENDPOINT = "/user";
    final String CHANGE_PASSWORD_ENDPOINT = "/user/reset_password";

    @MockBean
    private UsuarioService service;

    @Autowired
    private MockMvc mockMvc;

    private UUID uuid;

    @Test
    void health() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/health", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void cadastroUsuario() throws Exception {
        when(service.save(any())).thenReturn(createUser());
        String dto = basicUserDtoString(basicUserDto());
        RequestBuilder requestBuilders = MockMvcRequestBuilders
                .post(CADASTRO_ENDPOINT)
                .content(dto)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilders)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid.toString()));
    }

    @Test
    void cadastroUsuarioFullDto() throws Exception {
        when(service.save(any())).thenReturn(createUser());

        RequestBuilder requestBuilders = MockMvcRequestBuilders
                .post(CADASTRO_ENDPOINT)
                .content(fullUserDto().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilders)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid.toString()));
    }


    private Usuario fullUser(){
        Usuario user =  fullUserDto().getUser();
        user.setId(UUID.randomUUID());
        return user;
    }
    private Usuario createUser(){
        uuid = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setId(uuid);
        return usuario;
    }

    private UsuarioDto basicUserDto(){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setName("Usuario Name");
        usuarioDto.setEmail("email@mail.com");
        usuarioDto.setPassword("password");
        usuarioDto.setTelefone("+351966960358");
        return usuarioDto;
    }
    private String basicUserDtoString(UsuarioDto usuarioDto){
        return  "{ \"name\":\""+usuarioDto.getName()+"\",\"email\":\""+usuarioDto.getEmail()+"\"," +
                "  \"password\":\""+usuarioDto.getPassword()+"\",\"telefone\":\""+usuarioDto.getTelefone()+"\" }   ";
    }

    private UsuarioDto fullUserDto(){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setName("Usuario Name");
        usuarioDto.setEmail("email@mail.com");
        usuarioDto.setPassword("password");
        usuarioDto.setTelefone("+351966960358");
        usuarioDto.setBorn_date("2020-01-14");
        usuarioDto.setGuide("true");
        usuarioDto.setShare("false");
        usuarioDto.setInstagram("@instagramId");
        usuarioDto.setCountry("Brazil");
        return usuarioDto;
    }


    //@Test
    void resetResetPassword() {


    }
}