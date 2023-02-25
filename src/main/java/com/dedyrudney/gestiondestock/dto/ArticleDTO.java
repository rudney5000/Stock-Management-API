package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ArticleDTO {

    private Integer id;

    private String codeArticle;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal tauxTva;

    private BigDecimal prixUnitaireTtc;

    private String photo;

    private Integer idEntreprise;

    private CategoryDTO category;

    public static ArticleDTO fromEntity(Article article){
        if(article == null){
            return null;
        }

        return ArticleDTO.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .photo(article.getPhoto())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .tauxTva(article.getTauxTva())
                .idEntreprise(article.getIdEntreprise())
                .category(CategoryDTO.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDTO articleDTO) {
        if (articleDTO == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleDTO.getId());
        article.setCodeArticle(articleDTO.getCodeArticle());
        article.setDesignation(articleDTO.getDesignation());
        article.setPhoto(articleDTO.getPhoto());
        article.setPrixUnitaireHt(articleDTO.getPrixUnitaireHt());
        article.setPrixUnitaireTtc(articleDTO.getPrixUnitaireTtc());
        article.setTauxTva(articleDTO.getTauxTva());
        article.setIdEntreprise(articleDTO.getIdEntreprise());
        article.setCategory(CategoryDTO.toEntity(articleDTO.getCategory()));
        return article;
    }


}
