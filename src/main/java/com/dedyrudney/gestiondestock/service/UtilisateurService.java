package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;

import java.util.List;

public interface UtilisateurService {

    UtilisateurDTO save(UtilisateurDTO utilisateurDTO);

    UtilisateurDTO findById(Integer id);

    List<UtilisateurDTO> findAll();

    void delete(Integer id);

    UtilisateurDTO findByEmail(String email);

//    UtilisateurDTO chanceMotPasse(Cha)
}
