package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO findById(Integer id);

    List<ClientDTO> findAll();

    void delete(Integer id);
}
