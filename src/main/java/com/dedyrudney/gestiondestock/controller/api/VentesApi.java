package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.VentesDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.VENTES_ENDPOINT;

@Api("ventes")
public interface VentesApi {

    @PostMapping(VENTES_ENDPOINT + "/create")
    VentesDTO save(@PathVariable VentesDTO ventesDTO);

    @GetMapping(VENTES_ENDPOINT + "/{id_vente}")
    VentesDTO findById(@PathVariable("id_vente") Integer id);

    @GetMapping(VENTES_ENDPOINT + "/{codeVente}")
    VentesDTO findByCode(@PathVariable("codeVente") String code);

    @GetMapping(VENTES_ENDPOINT + "/all")
    List<VentesDTO> findAll();

    @DeleteMapping(VENTES_ENDPOINT + "/delete/{id_vente}")
    void delete(@PathVariable("id_vente") Integer id);
}
