package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.FournisseurDTO;

import java.util.List;

public interface FournisseurService {

    FournisseurDTO save(FournisseurDTO fournisseurDTO);

    FournisseurDTO findById(Integer id);

    List<FournisseurDTO> findAll();

    void delete(Integer id);
}
