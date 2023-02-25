package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.LigneCommandeClient;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeClientDTO {

    private Integer id;

    private ArticleDTO article;

    private CommandeClientDTO commandeClient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;

    private Integer idEntreprise;

    public static LigneCommandeClientDTO fromEntity(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null) {
            return null;
        }
        return LigneCommandeClientDTO.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDTO.fromEntity(ligneCommandeClient.getArticle()))
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .idEntreprise(ligneCommandeClient.getIdEntreprise())
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDTO ligneCommandeClientDTO) {
        if (ligneCommandeClientDTO == null) {
            return null;
        }

        LigneCommandeClient ligneCommandeClient = new LigneCommandeClient();
        ligneCommandeClient.setId(ligneCommandeClientDTO.getId());
        ligneCommandeClient.setArticle(ArticleDTO.toEntity(ligneCommandeClientDTO.getArticle()));
        ligneCommandeClient.setPrixUnitaire(ligneCommandeClientDTO.getPrixUnitaire());
        ligneCommandeClient.setQuantite(ligneCommandeClientDTO.getQuantite());
        ligneCommandeClient.setIdEntreprise(ligneCommandeClientDTO.getIdEntreprise());
        return ligneCommandeClient;
    }
}
