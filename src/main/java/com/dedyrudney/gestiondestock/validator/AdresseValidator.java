package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.AdresseDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {
    public static List<String> validate(AdresseDTO adresseDTO) {
        List<String> errors = new ArrayList<>();

        if (adresseDTO == null) {
            errors.add("Veuillez renseigner l'adresse 1'");
            errors.add("Veuillez renseigner la ville'");
            errors.add("Veuillez renseigner le pays'");
            errors.add("Veuillez renseigner le code postal'");
            return errors;
        }
        if (!StringUtils.hasLength(adresseDTO.getAdresse1())) {
            errors.add("Veuillez renseigner l'adresse 1'");
        }
        if (!StringUtils.hasLength(adresseDTO.getVille())) {
            errors.add("Veuillez renseigner la ville'");
        }
        if (!StringUtils.hasLength(adresseDTO.getPays())) {
            errors.add("Veuillez renseigner le pays'");
        }
        if (!StringUtils.hasLength(adresseDTO.getAdresse1())) {
            errors.add("Veuillez renseigner le code postal'");
        }
        return errors;
    }
}
