package com.dedyrudney.gestiondestock.service.strategy;

import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.flickr4java.flickr.FlickrException;
import lombok.Getter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory;
    private Strategy strategy;
    @Getter
    private String context;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public StrategyPhotoContext(String context) {

    }

    public Object savedPhoto(String context, Integer id, InputStream photo, String titre) throws FlickrException {
        determinContext(context);
        return strategy.savedPhoto(id, photo, titre);
    }

    private void determinContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "article":
                strategy = beanFactory.getBean(beanName, SavedArticlePhoto.class);
                break;
            case "client":
                strategy = beanFactory.getBean(beanName, SavedClientPhoto.class);
                break;
            case "fournisseur":
                strategy = beanFactory.getBean(beanName, SavedFournisseurPhoto.class);
                break;
            case "entreprise":
                strategy = beanFactory.getBean(beanName, SavedEntreprisePhoto.class);
                break;
            case "utilisateur":
                strategy = beanFactory.getBean(beanName, SavedUtilisateurPhoto.class);
                break;
            default: throw new InvalidOperationException("Context Inconnue pour l'enregistrement de la photo", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }

}
