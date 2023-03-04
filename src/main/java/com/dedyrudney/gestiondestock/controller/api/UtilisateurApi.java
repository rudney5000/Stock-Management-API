package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.UTILISATEUR_ENDPOINT;

@Api("utilisateurs")
public interface UtilisateurApi {

    @PostMapping(
            UTILISATEUR_ENDPOINT + "/create"
    )
    UtilisateurDTO save(@RequestBody UtilisateurDTO utilisateurDTO);

    @PostMapping(
            UTILISATEUR_ENDPOINT + "/{id_utilisateur}"
    )
    UtilisateurDTO findById(@PathVariable("id_utilisateur") Integer id);

    @GetMapping(
            UTILISATEUR_ENDPOINT + "/all"
    )
    List<UtilisateurDTO> findAll();

    @DeleteMapping(UTILISATEUR_ENDPOINT + "/delete/{id_utilisateur}")
    void delete(@PathVariable("id_utilisateur") Integer id);
}
