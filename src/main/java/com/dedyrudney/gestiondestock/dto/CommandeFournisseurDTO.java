package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.CommandeFournnisseur;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommandeFournisseurDTO {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private Integer idEntreprise;

    private FournisseurDTO fournisseur;

    private List<LigneCommandeFournisseurDTO> ligneCommandeFournisseurs;

    public static CommandeFournisseurDTO fromEntity(CommandeFournnisseur commandeFournisseur) {
        if (commandeFournisseur == null) {
            return null;
        }
        return CommandeFournisseurDTO.builder()
                .id(commandeFournisseur.getId())
                .code(commandeFournisseur.getCode())
                .dateCommande(commandeFournisseur.getDateCommande())
                .fournisseur(FournisseurDTO.fromEntity(commandeFournisseur.getFournisseur()))
                .etatCommande(commandeFournisseur.getEtatCommande())
                .idEntreprise(commandeFournisseur.getIdEntreprise())
                .build();
    }

    public static CommandeFournnisseur toEntity(CommandeFournisseurDTO commandeFournisseurDTO) {
        if (commandeFournisseurDTO == null) {
            return null;
        }
        CommandeFournnisseur commandeFournisseur = new CommandeFournnisseur();
        commandeFournisseur.setId(commandeFournisseurDTO.getId());
        commandeFournisseur.setCode(commandeFournisseurDTO.getCode());
        commandeFournisseur.setDateCommande(commandeFournisseurDTO.getDateCommande());
        commandeFournisseur.setFournisseur(FournisseurDTO.toEntity(commandeFournisseurDTO.getFournisseur()));
        commandeFournisseur.setIdEntreprise(commandeFournisseurDTO.getIdEntreprise());
        commandeFournisseur.setEtatCommande(commandeFournisseurDTO.getEtatCommande());
        return commandeFournisseur;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
