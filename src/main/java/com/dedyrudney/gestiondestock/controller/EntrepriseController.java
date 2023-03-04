package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.EntrepriseApi;
import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import com.dedyrudney.gestiondestock.service.EntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EntrepriseController implements EntrepriseApi {

    private EntrepriseService entrepriseService;

    @Autowired
    public EntrepriseController(EntrepriseService entrepriseService) {
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        return entrepriseService.save(entrepriseDTO);
    }

    @Override
    public EntrepriseDTO findById(Integer id) {
        return entrepriseService.findById(id);
    }

    @Override
    public List<EntrepriseDTO> findAll() {
        return entrepriseService.findAll();
    }

    @Override
    public void delete(Integer id) {
        entrepriseService.delete(id);
    }
}
