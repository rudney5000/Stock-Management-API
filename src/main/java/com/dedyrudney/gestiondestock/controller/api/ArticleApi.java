package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

public interface ArticleApi {

    @PostMapping(
            value = "/articles/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    ArticleDTO save(@RequestBody ArticleDTO articleDTO);

    @GetMapping(
            value = APP_ROOT + "/articles/{id_article}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ArticleDTO findById(@PathVariable("id_article") Integer id);

    @GetMapping(
            value = APP_ROOT + "/articles/{codeArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ArticleDTO findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(
            value = APP_ROOT + "articles/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<ArticleDTO> findAll();

    @DeleteMapping(
            value = APP_ROOT + "/articles/delete/{id_article}"
    )
    void delete(@PathVariable("id_article") Integer id);
}
