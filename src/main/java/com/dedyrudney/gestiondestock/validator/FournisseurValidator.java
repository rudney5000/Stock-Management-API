package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.dto.FournisseurDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDTO fournisseurDTO){

        List<String> errors = new ArrayList<>();

        if (fournisseurDTO == null){
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner le prenom du client");
            errors.add("Veuillez renseigner le mail du client");
            errors.add("Veuillez renseigner le numero de telephone du client");
            return errors;
        }

        if (!StringUtils.hasLength(fournisseurDTO.getNom())){
            errors.add("Veuillez renseigner le nom du client");
        }
        if (!StringUtils.hasLength(fournisseurDTO.getPrenom())){
            errors.add("Veuillez renseigner le prenom du client");
        }
        if (!StringUtils.hasLength(fournisseurDTO.getMail())){
            errors.add("Veuillez renseigner le mail du client");
        }
        if (!StringUtils.hasLength(fournisseurDTO.getNumTel())){
            errors.add("Veuillez renseigner le numero de telephone du client");
        }
        return errors;
    }
}
