package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.CategoryDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {

    public static List<String> validate(CategoryDTO categoryDTO){
        List<String> errors = new ArrayList<>();

        if (categoryDTO == null || StringUtils.hasLength(categoryDTO.getCode())){
            errors.add("Veuillez renseigner le code de la categorie");
        }
        return errors;
    }
}
