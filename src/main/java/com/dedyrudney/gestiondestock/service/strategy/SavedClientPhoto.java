package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.ClientService;
import com.dedyrudney.gestiondestock.service.FlickrPhotoService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SavedClientPhoto implements Strategy<ClientDTO> {

    private FlickrPhotoService flickrPhotoService;
    private ClientService clientService;

    @Autowired
    public SavedClientPhoto(FlickrPhotoService flickrPhotoService, ClientService clientService) {
        this.flickrPhotoService = flickrPhotoService;
        this.clientService = clientService;
    }

    @Override
    public ClientDTO savedPhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        ClientDTO clientDTO = clientService.findById(id);
        String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if (StringUtils.hasLength(urlPhoto)) {
            throw new InvalidOperationException("Erreur de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDTO.setPhoto(urlPhoto);
        return clientService.save(clientDTO);
    }
}
