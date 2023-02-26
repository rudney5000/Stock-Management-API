package com.dedyrudney.gestiondestock.validator;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDTO clientDTO){

        List<String> errors = new ArrayList<>();

        if (clientDTO == null){
            errors.add("Veuillez renseigner le nom du client");
            errors.add("Veuillez renseigner le prenom du client");
            errors.add("Veuillez renseigner le mail du client");
            errors.add("Veuillez renseigner le numero de telephone du client");
            return errors;
        }

        if (!StringUtils.hasLength(clientDTO.getNom())){
            errors.add("Veuillez renseigner le nom du client");
        }
        if (!StringUtils.hasLength(clientDTO.getPrenom())){
            errors.add("Veuillez renseigner le prenom du client");
        }
        if (!StringUtils.hasLength(clientDTO.getMail())){
            errors.add("Veuillez renseigner le mail du client");
        }
        if (!StringUtils.hasLength(clientDTO.getNumTel())){
            errors.add("Veuillez renseigner le numero de telephone du client");
        }
        return errors;
    }
}
