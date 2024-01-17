package com.becb.localauthserver.core;



import java.util.Collection;
import java.util.Collections;

import com.becb.localauthserver.domain.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import lombok.Getter;

@Getter
public class AuthUser extends User {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private String fullName;


    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getEmail(), usuario.getPassword(), authorities);

        this.fullName = usuario.getName();
        this.userId = usuario.getId();
    }

}