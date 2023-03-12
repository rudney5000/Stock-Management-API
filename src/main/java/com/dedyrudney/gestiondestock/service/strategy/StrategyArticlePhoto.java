package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.ArticleService;
import com.dedyrudney.gestiondestock.service.FlickrPhotoService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service
@Slf4j
public class StrategyArticlePhoto implements Strategy<ArticleDTO>{

    private FlickrPhotoService flickrPhotoService;
    private ArticleService articleService;

    @Autowired
    public StrategyArticlePhoto(FlickrPhotoService flickrPhotoService, ArticleService articleService) {
        this.flickrPhotoService = flickrPhotoService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDTO savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ArticleDTO articleDTO = articleService.findById(id);
        String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur de l'enregistrement de la photo de l'article", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDTO.setPhoto(urlPhoto);
        return articleService.save(articleDTO);
    }
}
