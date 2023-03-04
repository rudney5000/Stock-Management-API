package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.MvStckApi;
import com.dedyrudney.gestiondestock.dto.MvStckDTO;
import com.dedyrudney.gestiondestock.service.MvStckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class MvStckController implements MvStckApi {

    private MvStckService mvStckService;

    @Autowired
    public MvStckController(MvStckService mvStckService) {
        this.mvStckService = mvStckService;
    }

    @Override
    public BigDecimal stockReelArticle(Integer id_article) {
        return mvStckService.stockReelArticle(id_article);
    }

    @Override
    public List<MvStckDTO> mvStckArticle(Integer id_article) {
        return mvStckService.mvStckArticle(id_article);
    }

    @Override
    public MvStckDTO entreeStock(MvStckDTO mvStckDTO) {
        return mvStckService.entreeStock(mvStckDTO);
    }

    @Override
    public MvStckDTO sortieStock(MvStckDTO mvStckDTO) {
        return mvStckService.sortieStock(mvStckDTO);
    }

    @Override
    public MvStckDTO correctionStockPos(MvStckDTO mvStckDTO) {
        return mvStckService.correctionStockPos(mvStckDTO);
    }

    @Override
    public MvStckDTO correctionStockNeg(MvStckDTO mvStckDTO) {
        return mvStckService.correctionStockNeg(mvStckDTO);
    }
}
