package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.PhotoApi;
import com.dedyrudney.gestiondestock.service.strategy.StrategyPhotoContext;
import com.flickr4java.flickr.FlickrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController implements PhotoApi {

    private StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws FlickrException, IOException {
        return strategyPhotoContext.savedPhoto(context, id, photo.getInputStream(), title);
    }
}
