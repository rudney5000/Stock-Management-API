package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.FournisseurDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.FOURNISSEUR_ENDPOINT;

@Api("fournisseur")
public interface FournisseurApi {

    @PostMapping(
            FOURNISSEUR_ENDPOINT + "/create"
    )
    FournisseurDTO save(@RequestBody FournisseurDTO fournisseurDTO);

    @GetMapping(
            FOURNISSEUR_ENDPOINT + "/{id_fournisseur}"
    )
    FournisseurDTO findById(@PathVariable("id_fournisseur") Integer id);

    @GetMapping(
            FOURNISSEUR_ENDPOINT + "/all"
    )
    List<FournisseurDTO> findAll();

    @DeleteMapping(
            FOURNISSEUR_ENDPOINT + "/delete/{id_fournisseur}"
    )
    void delete(@PathVariable("id_fournisseur") Integer id);
}
