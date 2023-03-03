package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.ArticleApi;
import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import com.dedyrudney.gestiondestock.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController implements ArticleApi {

    private ArticleService articleService;

    @Autowired
    public ArticleController(
            ArticleService articleService
    ){
        this.articleService = articleService;
    }

    @Override
    public ArticleDTO save(ArticleDTO articleDTO) {
        return articleService.save(articleDTO);
    }

    @Override
    public ArticleDTO findById(Integer id) {
        return articleService.findById(id);
    }

    @Override
    public ArticleDTO findByCodeArticle(String codeArticle) {
        return articleService.findByCodeArticle(codeArticle);
    }

    @Override
    public List<ArticleDTO> findAll() {
        return articleService.findAll();
    }

    @Override
    public void delete(Integer id) {
        articleService.delete(id);
    }
}
