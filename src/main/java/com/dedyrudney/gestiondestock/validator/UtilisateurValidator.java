package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDTO utilisateurDTO){
        List<String> errors = new ArrayList<>();

        if (utilisateurDTO == null){
            errors.add("Veuillez renseigner votre nom d'utilisateur");
            errors.add("Veuillez renseigner votre prenom d'utilisateur");
            errors.add("Veuillez renseigner votre mot de passe d'utilisateur");
            errors.add("Veuillez renseigner votre adresse d'utilisateur");
            errors.add("Veuillez renseigner votre email d'utilisateur");
            return errors;
        }

        if (!StringUtils.hasLength(utilisateurDTO.getNom())){
            errors.add("Veuillez renseigner votre nom d'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDTO.getPrenom())){
            errors.add("Veuillez renseigner votre prenom d'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDTO.getMoteDePasse())){
            errors.add("Veuillez renseigner votre mot de passe d'utilisateur");
        }
        if (!StringUtils.hasLength(utilisateurDTO.getEmail())){
            errors.add("Veuillez renseigner votre email d'utilisateur");
        }
        if (utilisateurDTO.getDateDeNaissance() == null){
            errors.add("Veuillez renseigner votre date de naissance d'utilisateur");
        }
        if (utilisateurDTO.getAdresse() == null){
            errors.add("Veuillez renseigner votre adresse d'utilisateur");
        }else {
            if (!StringUtils.hasLength(utilisateurDTO.getAdresse().getAdresse1())){
                errors.add("Le champs de l'adresse 1 est obligatoire");
            }
            if (!StringUtils.hasLength(utilisateurDTO.getAdresse().getCodePostale())){
                errors.add("Le champs code postale est obligatoire");
            }
            if (!StringUtils.hasLength(utilisateurDTO.getAdresse().getVille())){
                errors.add("Le champs ville est obligatoire");
            }
            if (!StringUtils.hasLength(utilisateurDTO.getAdresse().getPays())){
                errors.add("Le champs pays est obligatoire");
            }
        }

        return errors;
    }
}
