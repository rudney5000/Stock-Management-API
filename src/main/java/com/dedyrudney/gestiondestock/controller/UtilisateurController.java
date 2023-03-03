package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.UtilisateurApi;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtilisateurController implements UtilisateurApi {

    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService){
        this.utilisateurService = utilisateurService;
    }

    @Override
    public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
        return utilisateurService.save(utilisateurDTO);
    }

    @Override
    public UtilisateurDTO findById(Integer id) {
        return utilisateurService.findById(id);
    }

    @Override
    public List<UtilisateurDTO> findAll() {
        return utilisateurService.findAll();
    }

    @Override
    public void delete(Integer id) {
        utilisateurService.delete(id);
    }
}
