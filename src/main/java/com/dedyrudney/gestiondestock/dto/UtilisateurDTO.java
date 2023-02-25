package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.Adresse;
import com.dedyrudney.gestiondestock.model.Entreprise;
import com.dedyrudney.gestiondestock.model.Roles;
import com.dedyrudney.gestiondestock.model.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateurDTO {

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateDeNaissance;

    private String moteDePasse;

    private AdresseDTO adresse;

    private String photo;

    private EntrepriseDTO entreprise;

    private List<RolesDTO> roles;

    public static UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        return UtilisateurDTO.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .moteDePasse(utilisateur.getMoteDePasse())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .adresse(AdresseDTO.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDTO.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                        .map(RolesDTO::fromEntity)
                                        .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDTO utilisateurDTO) {
        if (utilisateurDTO == null) {
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDTO.getId());
        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setPrenom(utilisateurDTO.getPrenom());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setMoteDePasse(utilisateurDTO.getMoteDePasse());
        utilisateur.setDateDeNaissance(utilisateurDTO.getDateDeNaissance());
        utilisateur.setAdresse(AdresseDTO.toEntity(utilisateurDTO.getAdresse()));
        utilisateur.setPhoto(utilisateurDTO.getPhoto());
        utilisateur.setEntreprise(EntrepriseDTO.toEntity(utilisateurDTO.getEntreprise()));

        return utilisateur;
    }
}
