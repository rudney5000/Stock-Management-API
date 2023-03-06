package com.dedyrudney.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeMotDePasseUtilisateurDTO {

    private Integer id;

    private String motDePasse;

    private String confirmMotDePasse;
}
