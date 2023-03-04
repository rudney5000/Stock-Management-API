package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.PhotoApi;
import com.flickr4java.flickr.FlickrException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoController implements PhotoApi {

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String title) throws FlickrException {
        return null;
    }
}
