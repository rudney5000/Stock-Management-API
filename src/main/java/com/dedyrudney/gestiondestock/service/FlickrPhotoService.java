package com.dedyrudney.gestiondestock.service;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrPhotoService {

    String savePhoto(InputStream photo, String title) throws FlickrException;
}
