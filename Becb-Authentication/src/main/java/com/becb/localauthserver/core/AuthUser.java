package com.becb.localauthserver.core;



import java.util.Collections;

import com.becb.localauthserver.domain.Usuario;
import org.springframework.security.core.userdetails.User;


import lombok.Getter;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario) {
        super(usuario.getEmail(), usuario.getPassword(), Collections.emptyList());

        this.fullName = usuario.getName();
        this.userId = usuario.getId();
    }

}