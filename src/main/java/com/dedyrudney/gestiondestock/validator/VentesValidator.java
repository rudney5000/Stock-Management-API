package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.VentesDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VentesValidator {

    public static List<String> validate(VentesDTO ventesDTO) {
        List<String> errors = new ArrayList<>();
        if (ventesDTO == null) {
            errors.add("Veuillez renseigner le code de la commande");
            errors.add("Veuillez renseigner la date de la commande");
            return errors;
        }

        if (!StringUtils.hasLength(ventesDTO.getCode())) {
            errors.add("Veuillez renseigner le code de la commande");
        }
        if (ventesDTO.getDateVente() == null) {
            errors.add("Veuillez renseigner la date de la commande");
        }

        return errors;
    }
}
