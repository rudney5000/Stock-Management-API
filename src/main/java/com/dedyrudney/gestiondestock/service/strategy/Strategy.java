package com.dedyrudney.gestiondestock.service.strategy;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface Strategy<T> {

    T savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException;
}
