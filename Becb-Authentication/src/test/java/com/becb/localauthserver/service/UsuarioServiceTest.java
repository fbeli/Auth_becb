package com.becb.localauthserver.service;

import com.becb.localauthserver.domain.ResetRequest;
import com.becb.localauthserver.domain.Usuario;
import com.becb.localauthserver.dto.ResetRequestDto;
import com.becb.localauthserver.repository.ResetRepository;
import com.becb.localauthserver.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @MockBean
    ResetRepository resetRepository;

    @Test
    void changePassword() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setPassword("password");
        dto.setCode("code");
        dto.setEmail("email");

        Usuario myData = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(myData));
        when(resetRepository.findByEmail(any())).thenReturn(createResetRequests());

        ResetRequestDto retorno = usuarioService.changePassword(dto);
        assertNotNull(retorno);
        assertEquals("Password changed successfully", retorno.getMessage());
    }

    @Test
    void changePasswordCodeNotFound() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setPassword("password");
        dto.setCode("code");
        dto.setEmail("email");

        ArrayList<ResetRequest> lista = createResetRequests();
        lista.get(0).setCode("code1");
        lista.get(1).setCode("code1");

        Usuario myData = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(myData));
        when(resetRepository.findByEmail(any())).thenReturn(lista);

        ResetRequestDto retorno = usuarioService.changePassword(dto);
        assertNotNull(retorno);
        assertEquals("Code is not valid", retorno.getMessage());
    }

    @Test
    void changePasswordTime() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setPassword("password");
        dto.setCode("code");
        dto.setEmail("email");

        ArrayList<ResetRequest> lista = createResetRequests();
        lista.get(0).setRequestDate(LocalDateTime.now().minusHours(1));
        lista.get(1).setRequestDate(LocalDateTime.now().minusHours(1));

        Usuario myData = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(myData));
        when(resetRepository.findByEmail(any())).thenReturn(lista);

        ResetRequestDto retorno = usuarioService.changePassword(dto);
        assertNotNull(retorno);
        assertEquals("Code is not valid", retorno.getMessage());
    }

    @Test
    void changePasswordEmailNotFound() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setPassword("password");
        dto.setCode("code");
        dto.setEmail("email");

        Usuario myData = Mockito.mock(Usuario.class);
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(myData));
        when(resetRepository.findByEmail(any())).thenReturn(null);

        ResetRequestDto retorno = usuarioService.changePassword(dto);
        assertNotNull(retorno);
        assertEquals("Reset to Email not found, ask for it again", retorno.getMessage());
    }

    private ArrayList<ResetRequest> createResetRequests() {
        ArrayList<ResetRequest> resetRequests = new ArrayList<>();
        ResetRequest resetRequest = new ResetRequest();
        resetRequest.setEmail("email");
        resetRequest.setCode("code");
        resetRequest.setRequestDate(LocalDateTime.now().minusMinutes(20));
        resetRequests.add(resetRequest);


        ResetRequest resetRequest2 = new ResetRequest();
        resetRequest2.setEmail("email");
        resetRequest2.setCode("code");
        resetRequest2.setRequestDate(LocalDateTime.now().minusMinutes(40));
        resetRequests.add(resetRequest2);

        return resetRequests;
    }


}