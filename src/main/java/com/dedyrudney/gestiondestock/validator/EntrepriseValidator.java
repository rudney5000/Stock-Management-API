package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDTO entrepriseDTO) {
        List<String> errors = new ArrayList<>();
        if (entrepriseDTO == null) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
            errors.add("Veuillez reseigner la description de l'entreprise");
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
            errors.add("Veuillez reseigner l'email de l'entreprise");
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
            errors.addAll(AdresseValidator.validate(null));
            return errors;
        }

        if (!StringUtils.hasLength(entrepriseDTO.getNom())) {
            errors.add("Veuillez renseigner le nom de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDTO.getDescription())) {
            errors.add("Veuillez reseigner la description de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDTO.getCodeFiscal())) {
            errors.add("Veuillez reseigner le code fiscal de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDTO.getEmail())) {
            errors.add("Veuillez reseigner l'email de l'entreprise");
        }
        if (!StringUtils.hasLength(entrepriseDTO.getNumTel())) {
            errors.add("Veuillez reseigner le numero de telephone de l'entreprise");
        }

        errors.addAll(AdresseValidator.validate(entrepriseDTO.getAdresse()));
        return errors;
    }

}
