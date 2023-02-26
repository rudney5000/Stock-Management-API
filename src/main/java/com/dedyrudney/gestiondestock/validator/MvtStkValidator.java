package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.MvStckDTO;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {

    public static List<String> validate(MvStckDTO mvStckDTO) {
        List<String> errors = new ArrayList<>();
        if (mvStckDTO == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
            errors.add("Veuillez renseigner la quantite du mouvenent");
            errors.add("Veuillez renseigner l'article");
            errors.add("Veuillez renseigner le type du mouvement");

            return errors;
        }
        if (mvStckDTO.getDateMvt() == null) {
            errors.add("Veuillez renseigner la date du mouvenent");
        }
        if (mvStckDTO.getQuantite() == null || mvStckDTO.getQuantite().compareTo(BigDecimal.ZERO) == 0) {
            errors.add("Veuillez renseigner la quantite du mouvenent");
        }
        if (mvStckDTO.getArticle() == null || mvStckDTO.getArticle().getId() == null) {
            errors.add("Veuillez renseigner l'article");
        }
        if (!StringUtils.hasLength(mvStckDTO.getTypeMvt().name())) {
            errors.add("Veuillez renseigner le type du mouvement");
        }

        return errors;
    }
}
