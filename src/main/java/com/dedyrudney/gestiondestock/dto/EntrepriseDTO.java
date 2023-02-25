package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.Entreprise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EntrepriseDTO {

    private Integer id;

    private String nom;

    private String description;

    private AdresseDTO adresse;

    private String codeFiscal;

    private String photo;

    private String email;

    private String numTel;

    private String steWeb;

    @JsonIgnore
    private List<UtilisateurDTO> utilisateurs;

    public static EntrepriseDTO fromEntity(Entreprise entreprise) {
        if (entreprise == null) {
            return null;
        }
        return EntrepriseDTO.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .description(entreprise.getDescription())
                .adresse(AdresseDTO.fromEntity(entreprise.getAdresse()))
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .email(entreprise.getEmail())
                .numTel(entreprise.getNumTel())
                .steWeb(entreprise.getSteWeb())
                .build();
    }

    public static Entreprise toEntity(EntrepriseDTO entrepriseDTO) {
        if (entrepriseDTO == null) {
            return null;
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setId(entrepriseDTO.getId());
        entreprise.setNom(entrepriseDTO.getNom());
        entreprise.setDescription(entrepriseDTO.getDescription());
        entreprise.setAdresse(AdresseDTO.toEntity(entrepriseDTO.getAdresse()));
        entreprise.setCodeFiscal(entrepriseDTO.getCodeFiscal());
        entreprise.setPhoto(entrepriseDTO.getPhoto());
        entreprise.setEmail(entrepriseDTO.getEmail());
        entreprise.setNumTel(entrepriseDTO.getNumTel());
        entreprise.setSteWeb(entrepriseDTO.getSteWeb());

        return entreprise;
    }
}
