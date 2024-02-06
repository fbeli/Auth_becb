package com.becb.localauthserver.dto;

import com.becb.localauthserver.domain.Usuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDto extends Usuario {

    String grupoId;
}
