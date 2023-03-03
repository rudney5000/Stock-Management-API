package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.VentesDTO;

import java.util.List;

public interface VentesService {

    VentesDTO save(VentesDTO ventesDTO);

    VentesDTO findById(Integer id);

    VentesDTO findByCode(String code);

    List<VentesDTO> findAll();

    void delete(Integer id);
}
