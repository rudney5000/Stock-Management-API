package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.ENTREPRISE_ENDPOINT;

@Api("entreprises")
public interface EntrepriseApi {

    @PostMapping(ENTREPRISE_ENDPOINT + "/create")
    EntrepriseDTO save(@RequestBody EntrepriseDTO entrepriseDTO);

    @GetMapping(ENTREPRISE_ENDPOINT + "/{id_entreprise}")
    EntrepriseDTO findById(@PathVariable("id_entreprise") Integer id);

    @GetMapping(ENTREPRISE_ENDPOINT + "/all")
    List<EntrepriseDTO> findAll();

    @GetMapping(ENTREPRISE_ENDPOINT + "/delete/{id_entreprise}")
    void delete(@PathVariable("id_entreprise") Integer id);
}
