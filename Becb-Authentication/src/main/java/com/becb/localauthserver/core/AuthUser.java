package com.becb.localauthserver.core;

import java.util.Collection;
import java.util.UUID;

import com.becb.localauthserver.domain.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.Getter;

@Getter
public class AuthUser extends User {

    private static final Logger logger = LoggerFactory.getLogger(AuthUser.class);
    private static final long serialVersionUID = 1L;
    private UUID userId;
    private String fullName;
    private String email;
    private Boolean share;
    private Boolean guide;
    private String instagram;


    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getPassword(), authorities);

        this.fullName = usuario.getName();
        this.userId = usuario.getId();
        this.share = usuario.getShare();
        this.email = usuario.getEmail();
        this.guide = usuario.getGuide();
        this.instagram = usuario.getInstagram();



        logger.info("Login do usuário:  "+usuario.getEmail());
    }

}