package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.ClientApi;
import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController implements ClientApi {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        return null;
    }

    @Override
    public ClientDTO findById(Integer id) {
        return null;
    }

    @Override
    public List<ClientDTO> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
