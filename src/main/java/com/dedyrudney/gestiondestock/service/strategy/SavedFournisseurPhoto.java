package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.dto.FournisseurDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.FlickrPhotoService;
import com.dedyrudney.gestiondestock.service.FournisseurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("fournisseurStrategy")
@Slf4j
public class SavedFournisseurPhoto implements Strategy<FournisseurDTO> {

    private FlickrPhotoService flickrPhotoService;
    private FournisseurService fournisseurService;

    @Autowired
    public SavedFournisseurPhoto(FlickrPhotoService flickrPhotoService, FournisseurService fournisseurService) {
        this.flickrPhotoService = flickrPhotoService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDTO savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        FournisseurDTO fournisseurDTO = fournisseurService.findById(id);
        String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        fournisseurDTO.setPhoto(urlPhoto);
        return fournisseurService.save(fournisseurDTO);
    }
}
