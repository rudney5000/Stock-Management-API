package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    ArticleDTO save(ArticleDTO articleDTO);

    ArticleDTO findById(Integer id);

    ArticleDTO findByCodeArticle(String codeArticle);

    List<ArticleDTO> findAll();

    void delete(Integer id);
}
