package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.MvStckDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

@Api("mvtstck")
public interface MvStckApi {

    @GetMapping(APP_ROOT + "/mvtstck/stockreel/{id_article}")
    BigDecimal stockReelArticle(@PathVariable("id_article") Integer id_article);

    @GetMapping(APP_ROOT + "/mvtstck/filter/article/{id_article}")
    List<MvStckDTO> mvStckArticle(@PathVariable("id_article") Integer id_article);

    @PostMapping(APP_ROOT + "/mvtstck/entree")
    MvStckDTO entreeStock(@RequestBody MvStckDTO mvStckDTO);

    @PostMapping(APP_ROOT + "/mvtstck/sortie")
    MvStckDTO sortieStock(@RequestBody MvStckDTO mvStckDTO);

    @PostMapping(APP_ROOT + "/mvtstck/correctionpos")
    MvStckDTO correctionStockPos(@RequestBody MvStckDTO mvStckDTO);

    @PostMapping(APP_ROOT + "/mvtstck/correctionneg")
    MvStckDTO correctionStockNeg(@RequestBody MvStckDTO mvStckDTO);
}
