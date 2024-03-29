package com.becb.localauthserver.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "resets")
public class ResetRequest {

    public ResetRequest() {}
    public ResetRequest(String email, String code) {
        this.email = email;
        this.code = code;
        requestDate = LocalDateTime.now();
    }
    @Column
    private String email;

    @Column
    private String code;

    @Column
    private LocalDateTime requestDate;


    @Id
    @GeneratedValue
    private Long id;


}
