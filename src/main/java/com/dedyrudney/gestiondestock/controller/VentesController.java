package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.VentesApi;
import com.dedyrudney.gestiondestock.dto.VentesDTO;
import com.dedyrudney.gestiondestock.service.VentesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VentesController implements VentesApi {

    private VentesService ventesService;

    @Autowired
    public VentesController(VentesService ventesService) {
        this.ventesService = ventesService;
    }

    @Override
    public VentesDTO save(VentesDTO ventesDTO) {
        return ventesService.save(ventesDTO);
    }

    @Override
    public VentesDTO findById(Integer id) {
        return ventesService.findById(id);
    }

    @Override
    public VentesDTO findByCode(String code) {
        return ventesService.findByCode(code);
    }

    @Override
    public List<VentesDTO> findAll() {
        return ventesService.findAll();
    }

    @Override
    public void delete(Integer id) {
        ventesService.delete(id);
    }
}
