package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.CommandeFournisseurDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeFournisseurValidator {

    public static List<String> validate(CommandeFournisseurDTO commandeFournisseurDTO) {

        List<String> errors = new ArrayList<>();

        if (commandeFournisseurDTO == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        if (!StringUtils.hasLength(commandeFournisseurDTO.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (commandeFournisseurDTO.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (!StringUtils.hasLength(commandeFournisseurDTO.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if (commandeFournisseurDTO.getFournisseur() == null || commandeFournisseurDTO.getFournisseur().getId() == null) {
            errors.add("Veuillez renseigner le fournisseur");
        }

        return errors;
    }

}
