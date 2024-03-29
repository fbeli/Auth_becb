package com.becb.localauthserver.dto;

import com.becb.localauthserver.domain.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDto extends Usuario {

    String grupoId;

    @JsonIgnore
    public  Usuario getUser(){

        fixInstagram();
        
        Usuario usuario = new Usuario();
        usuario.setEmail(getEmail());
        usuario.setPassword(getPassword());
        usuario.setId(getId());
        usuario.setGuide(getGuide());
        usuario.setName(getName());
        usuario.setBorn_date(getBorn_date());
        usuario.setShare(getShare());
        usuario.setCountry(getCountry());
        usuario.setTelefone(getTelefone());
        usuario.setDescription(getDescription());
        usuario.setInstagram(getInstagram());
        usuario.setGrupos(getGrupos());
        return usuario;
    }
    private void fixInstagram(){
        if(getInstagram() != null){
            if(!getInstagram().contains("@"))
                setInstagram("@"+getInstagram());
        } else {
            setInstagram("@");
        }
    }
    public UsuarioDto() {}

    public UsuarioDto(Usuario usuario) {
        if (usuario != null) {
            this.setId(usuario.getId());
            this.setEmail(usuario.getEmail());
            this.setName(usuario.getName());
            this.setShare(usuario.getShare());
            this.setCountry(usuario.getCountry());
            this.setTelefone(usuario.getTelefone());
            this.setBorn_date(usuario.getBorn_date());
            this.setGuide(usuario.getGuide());
            this.setInstagram(usuario.getInstagram());
            this.setDescription(usuario.getDescription());
            this.setGrupos(usuario.getGrupos());

        }
    }



}
