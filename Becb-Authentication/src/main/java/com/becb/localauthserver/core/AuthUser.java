package com.becb.localauthserver.core;

import java.util.Collection;
import java.util.logging.Logger;

import com.becb.localauthserver.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.Getter;

@Getter
public class AuthUser extends User {

    private static final Logger logger = Logger.getLogger(AuthUser.class.getName());
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String fullName;


    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getPassword(), authorities);

        this.fullName = usuario.getName();
        this.userId = usuario.getId();
        logger.info("Login do usuário:  "+usuario.getEmail());
    }

}