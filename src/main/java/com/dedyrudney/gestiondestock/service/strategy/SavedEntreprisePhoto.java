package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.EntrepriseService;
import com.dedyrudney.gestiondestock.service.FlickrPhotoService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class SavedEntreprisePhoto implements Strategy <EntrepriseDTO> {

    private FlickrPhotoService flickrPhotoService;
    private EntrepriseService entrepriseService;

    @Autowired
    public SavedEntreprisePhoto(FlickrPhotoService flickrPhotoService, EntrepriseService entrepriseService) {
        this.flickrPhotoService = flickrPhotoService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDTO savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDTO entrepriseDTO = entrepriseService.findById(id);
        String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        entrepriseDTO.setPhoto(urlPhoto);
        return entrepriseService.save(entrepriseDTO);
    }
}
