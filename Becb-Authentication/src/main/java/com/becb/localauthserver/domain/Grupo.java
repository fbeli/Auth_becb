package com.becb.localauthserver.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "grupo")
public class Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

   @ManyToMany(fetch = FetchType.LAZY)
   @JsonBackReference
    @JoinTable(name = "grupo_permissao", joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();



    @ManyToMany(mappedBy = "grupos")
    @JsonBackReference
    private Set<Usuario> users;

    public void addUser(Usuario usuario) {
        if(users == null)
            users = new HashSet<>();
        users.add(usuario);
    }
}
