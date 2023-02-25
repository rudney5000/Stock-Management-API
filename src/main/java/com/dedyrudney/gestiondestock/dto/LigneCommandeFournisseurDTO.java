package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.LigneCommandeFournisseur;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeFournisseurDTO {

    private Integer id;

    private ArticleDTO article;

    private CommandeFournisseurDTO commandeFournnisseur;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneCommandeFournisseurDTO fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur) {
        if (ligneCommandeFournisseur == null) {
            return null;
        }
        return LigneCommandeFournisseurDTO.builder()
                .id(ligneCommandeFournisseur.getId())
                .article(ArticleDTO.fromEntity(ligneCommandeFournisseur.getArticle()))
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDTO ligneCommandeFournisseurDTO) {
        if (ligneCommandeFournisseurDTO == null) {
            return null;
        }

        LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();
        ligneCommandeFournisseur.setId(ligneCommandeFournisseurDTO.getId());
        ligneCommandeFournisseur.setArticle(ArticleDTO.toEntity(ligneCommandeFournisseurDTO.getArticle()));
        ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDTO.getPrixUnitaire());
        ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDTO.getQuantite());
        ligneCommandeFournisseur.setIdEntreprise(ligneCommandeFournisseurDTO.getIdEntreprise());
        return ligneCommandeFournisseur;
    }
}
