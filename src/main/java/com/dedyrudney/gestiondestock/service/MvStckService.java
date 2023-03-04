package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.MvStckDTO;

import java.math.BigDecimal;
import java.util.List;

public interface MvStckService {

    BigDecimal stockReelArticle(Integer id_article);

    List<MvStckDTO> mvStckArticle(Integer id_article);

    MvStckDTO entreeStock(MvStckDTO mvStckDTO);

    MvStckDTO sortieStock(MvStckDTO mvStckDTO);

    MvStckDTO correctionStockPos(MvStckDTO mvStckDTO);

    MvStckDTO correctionStockNeg(MvStckDTO mvStckDTO);
}
