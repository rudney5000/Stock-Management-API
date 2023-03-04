package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.MvStckRepository;
import com.dedyrudney.gestiondestock.dto.MvStckDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.model.TypeMvtStk;
import com.dedyrudney.gestiondestock.service.ArticleService;
import com.dedyrudney.gestiondestock.service.MvStckService;
import com.dedyrudney.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvStckServiceImpl implements MvStckService {

    private MvStckRepository mvStckRepository;
    private ArticleService articleService;

    @Autowired
    public MvStckServiceImpl(
            MvStckRepository mvStckRepository,
            ArticleService articleService
    ) {
        this.mvStckRepository = mvStckRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer id_article) {
        if (id_article == null){
            log.warn("ID article is NULL");
            return BigDecimal.valueOf(-1);
        }
        articleService.findById(id_article);
        return mvStckRepository.stockReelArticle(id_article);
    }

    @Override
    public List<MvStckDTO> mvStckArticle(Integer id_article) {
        return mvStckRepository.findAllByArticle_Id(id_article).stream()
                .map(MvStckDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MvStckDTO entreeStock(MvStckDTO mvStckDTO) {
        return entreePositive(mvStckDTO, TypeMvtStk.ENTREE);
    }

    @Override
    public MvStckDTO sortieStock(MvStckDTO mvStckDTO) {
        return sortieNegative(mvStckDTO, TypeMvtStk.SORTIE);
    }

    @Override
    public MvStckDTO correctionStockPos(MvStckDTO mvStckDTO) {
        return entreePositive(mvStckDTO, TypeMvtStk.CORRECTION_POS);
    }

    @Override
    public MvStckDTO correctionStockNeg(MvStckDTO mvStckDTO) {
        return sortieNegative(mvStckDTO, TypeMvtStk.CORRECTION_NEG);
    }

    private MvStckDTO entreePositive(MvStckDTO mvStckDTO, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(mvStckDTO);
        if (!errors.isEmpty()){
            log.error("Article is not valid {}", mvStckDTO);
            throw new InvalidEntityException(
                    "Le mouvement du stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALID,
                    errors
            );
        }
        mvStckDTO.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvStckDTO.getQuantite().doubleValue())
                )
        );
        mvStckDTO.setTypeMvt(typeMvtStk);
        return MvStckDTO.fromEntity(
                mvStckRepository.save(MvStckDTO.toEntity(mvStckDTO))
        );
    }

    private MvStckDTO sortieNegative(MvStckDTO mvStckDTO, TypeMvtStk typeMvtStk) {
        List<String> errors = MvtStkValidator.validate(mvStckDTO);
        if (!errors.isEmpty()){
            log.error("Article is not valid {}", mvStckDTO);
            throw new InvalidEntityException(
                    "Le mouvement du stock n'est pas valide",
                    ErrorCodes.MVT_STK_NOT_VALID,
                    errors
            );
        }
        mvStckDTO.setQuantite(
                BigDecimal.valueOf(
                        Math.abs(mvStckDTO.getQuantite().doubleValue()) * -1
                )
        );
        mvStckDTO.setTypeMvt(typeMvtStk);
        return MvStckDTO.fromEntity(
                mvStckRepository.save(MvStckDTO.toEntity(mvStckDTO))
        );
    }
}
