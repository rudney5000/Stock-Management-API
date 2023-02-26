package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDTO articleDTO){
        List<String> errors = new ArrayList<>();

        if (articleDTO == null){
            errors.add("Veuillez renseigner le code de l'article");
            errors.add("Veuillez renseigner la destignation de l'article");
            errors.add("Veuillez renseigner le prix unitaire de l'article");
            errors.add("Veuillez renseigner le taux TVA de l'article");
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
            errors.add("Veuillez selectionner une categorie de l'article");
            return errors;
        }

        if (!StringUtils.hasLength(articleDTO.getCodeArticle())){
            errors.add("Veuillez renseigner le code de l'article");
        }
        if (!StringUtils.hasLength(articleDTO.getDesignation())){
            errors.add("Veuillez renseigner la destignation de l'article");
        }
        if (articleDTO.getPrixUnitaireHt() == null){
            errors.add("Veuillez renseigner le prix unitaire de l'article");
        }
        if (articleDTO.getTauxTva() == null){
            errors.add("Veuillez renseigner le taux TVA de l'article");
        }
        if (articleDTO.getPrixUnitaireTtc() == null){
            errors.add("Veuillez renseigner le prix unitaire TTC de l'article");
        }
        if (articleDTO.getCategory() == null){
            errors.add("Veuillez selectionner une categorie de l'article");
        }
        return errors;
    }
}
