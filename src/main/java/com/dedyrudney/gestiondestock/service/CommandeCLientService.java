package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;

import java.util.List;

public interface CommandeCLientService {

    CommandeClientDTO save(CommandeClientDTO commandeClientDTO);

    CommandeClientDTO findById(Integer id);

    CommandeClientDTO findByCode(String code);

    List<CommandeClientDTO> findAll();

    void delete(Integer id);
}
