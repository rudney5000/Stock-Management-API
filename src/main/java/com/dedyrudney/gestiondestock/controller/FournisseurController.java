package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.FournisseurApi;
import com.dedyrudney.gestiondestock.dto.FournisseurDTO;
import com.dedyrudney.gestiondestock.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FournisseurController implements FournisseurApi {

    private FournisseurService fournisseurService;

    @Autowired
    public FournisseurController(FournisseurService fournisseurService){
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDTO save(FournisseurDTO fournisseurDTO) {
        return fournisseurService.save(fournisseurDTO);
    }

    @Override
    public FournisseurDTO findById(Integer id) {
        return fournisseurService.findById(id);
    }

    @Override
    public List<FournisseurDTO> findAll() {
        return fournisseurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        fournisseurService.delete(id);
    }
}
