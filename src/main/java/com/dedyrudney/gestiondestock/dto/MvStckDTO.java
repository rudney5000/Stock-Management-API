package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.Article;
import com.dedyrudney.gestiondestock.model.MvStck;
import com.dedyrudney.gestiondestock.model.SourceMvtStk;
import com.dedyrudney.gestiondestock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvStckDTO {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDTO article;

    private TypeMvtStk typeMvt;

    private SourceMvtStk sourceMvt;

    private Integer idEntreprise;

    public static MvStckDTO fromEntity(MvStck mvStck) {
        if (mvStck == null) {
            return null;
        }

        return MvStckDTO.builder()
                .id(mvStck.getId())
                .dateMvt(mvStck.getDateMvt())
                .quantite(mvStck.getQuantite())
                .article(ArticleDTO.fromEntity(mvStck.getArticle()))
                .typeMvt(mvStck.getTypeMvt())
                .sourceMvt(mvStck.getSourceMvt())
                .idEntreprise(mvStck.getIdEntreprise())
                .build();
    }

    public static MvStck toEntity(MvStckDTO mvStckDTO) {
        if (mvStckDTO == null) {
            return null;
        }

        MvStck mvtStk = new MvStck();
        mvtStk.setId(mvStckDTO.getId());
        mvtStk.setDateMvt(mvStckDTO.getDateMvt());
        mvtStk.setQuantite(mvStckDTO.getQuantite());
        mvtStk.setArticle(ArticleDTO.toEntity(mvStckDTO.getArticle()));
        mvtStk.setTypeMvt(mvStckDTO.getTypeMvt());
        mvtStk.setSourceMvt(mvStckDTO.getSourceMvt());
        mvtStk.setIdEntreprise(mvStckDTO.getIdEntreprise());
        return mvtStk;
    }
}
