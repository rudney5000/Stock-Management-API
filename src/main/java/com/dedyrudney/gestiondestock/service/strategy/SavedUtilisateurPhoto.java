package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.FlickrPhotoService;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("utilisateurStrategy")
@Slf4j
public class SavedUtilisateurPhoto implements Strategy<UtilisateurDTO> {

    private FlickrPhotoService flickrPhotoService;
    private UtilisateurService utilisateurService;

    @Autowired
    public SavedUtilisateurPhoto(FlickrPhotoService flickrPhotoService, UtilisateurService utilisateurService) {
        this.flickrPhotoService = flickrPhotoService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDTO savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        UtilisateurDTO utilisateurDTO = utilisateurService.findById(id);
        String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        utilisateurDTO.setPhoto(urlPhoto);
        return utilisateurService.save(utilisateurDTO);
    }
}
