package com.becb.localauthserver.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Entity
@Table(name = "usuarios")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String telefone;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    @Column
    private String country;
    @Column
    private Boolean guide;
    @Column
    private LocalDate born_date;
    @Column
    private String instagram;
    @Column
    private Boolean share;

    @Column
    private String description;

    public void setInstagram(String instagram){
        if(instagram != null && instagram.equalsIgnoreCase("instagram") ){
            this.instagram = null;
        }else{
            this.instagram = instagram;
        }
    }

    public void setGuide(String guide) {
        if(guide.equals("on") || guide.equals("true")) {
            this.guide = true;
        }else{
            this.guide = false;
        }
    }
    public void setGuide(Boolean guide) {
       this.guide = guide;
    }
    public void setShare(String share) {
        if(share.equals("on") || share.equals("true")) {
            this.share = true;
        } else {
            this.share = false;
        }
    }
    public void setShare(Boolean share) {
        this.share = share;
    }

    public void setBorn_date(String date_string) {
        if(date_string != null && !date_string.trim().isEmpty()) {
       born_date = LocalDate.parse(date_string);
        }
    }
    public void setBorn_date(LocalDate date) {

            born_date = date;

    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }



    public void addGrupo(Grupo grupo){
        if(grupos == null){
            grupos = new HashSet<>();
        }
        this.grupos.add(grupo);
    }

}