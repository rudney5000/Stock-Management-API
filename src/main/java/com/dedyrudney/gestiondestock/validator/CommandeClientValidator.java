package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDTO commandeClientDTO) {
        List<String> errors = new ArrayList<>();
        if (commandeClientDTO == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            errors.add("Veuillez renseigner l'etat de la commande");
            errors.add("Veuillez renseigner le client");
            return errors;
        }

        if (!StringUtils.hasLength(commandeClientDTO.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (commandeClientDTO.getDateCommande() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }
        if (!StringUtils.hasLength(commandeClientDTO.getEtatCommande().toString())) {
            errors.add("Veuillez renseigner l'etat de la commande");
        }
        if (commandeClientDTO.getClient() == null || commandeClientDTO.getClient().getId() == null) {
            errors.add("Veuillez renseigner le client");
        }

        return errors;
    }
}
