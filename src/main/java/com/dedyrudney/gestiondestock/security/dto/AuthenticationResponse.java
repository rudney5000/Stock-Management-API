package com.dedyrudney.gestiondestock.security.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AuthenticationResponse {

    private String accessToken;

//    private String nom;
//
//    private String prenom;
//
//    private String email;
//
//    private Instant dateDeNaissance;
//
//    private String moteDePasse;
}
