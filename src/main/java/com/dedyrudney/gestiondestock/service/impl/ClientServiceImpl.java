package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.ClientRepository;
import com.dedyrudney.gestiondestock.Repository.CommandeClientRepository;
import com.dedyrudney.gestiondestock.dto.ClientDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.model.CommandeClient;
import com.dedyrudney.gestiondestock.service.ClientService;
import com.dedyrudney.gestiondestock.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private CommandeClientRepository commandeClientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, CommandeClientRepository commandeClientRepository){
        this.clientRepository = clientRepository;
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        List<String> errors = ClientValidator.validate(clientDTO);
        if (!errors.isEmpty()){
            log.error("Client is not null {}", clientDTO);
            throw new InvalidEntityException(
                    "Le client n'est pas valide",
                    ErrorCodes.CLIENT_NOT_VALID,
                    errors
            );
        }
        return ClientDTO.fromEntity(
                clientRepository.save(
                        ClientDTO.toEntity(clientDTO)
                )
        );
    }

    @Override
    public ClientDTO findById(Integer id) {
        if (id == null){
            log.error("Client ID is null");
            return null;
        }
        return clientRepository.findById(id)
                .map(ClientDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun CLient avec l'ID = " + id + "n'a ete trouve dans la BDD",
                                ErrorCodes.ARTICLE_NOT_FOUND
                        )
                );
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("CLient ID is null");
            return;
        }
        List<CommandeClient> commandeClients = commandeClientRepository.findAllByCleintID(id);
        if (!commandeClients.isEmpty()){
            throw new InvalidOperationException(
                    "Impossible de supprimer un client qui a deja des commande clients"
            );
        }
        clientRepository.deleteById(id);
    }
}
