package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;

import java.util.List;

public interface EntrepriseService {

    EntrepriseDTO save(EntrepriseDTO entrepriseDTO);

    EntrepriseDTO findById(Integer id);

    List<EntrepriseDTO> findAll();

    void delete(Integer id);
}
