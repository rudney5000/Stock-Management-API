package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.ArticleRepository;
import com.dedyrudney.gestiondestock.Repository.ClientRepository;
import com.dedyrudney.gestiondestock.Repository.CommandeClientRepository;
import com.dedyrudney.gestiondestock.Repository.LigneCommandeClientRepository;
import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeClientDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.model.Article;
import com.dedyrudney.gestiondestock.model.Client;
import com.dedyrudney.gestiondestock.model.CommandeClient;
import com.dedyrudney.gestiondestock.model.LigneCommandeClient;
import com.dedyrudney.gestiondestock.service.CommandeCLientService;
import com.dedyrudney.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeClientServiceImpl implements CommandeCLientService {

    private CommandeClientRepository commandeClientRepository;
    private LigneCommandeClientRepository ligneCommandeClientRepository;
    private ClientRepository clientRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CommandeClientServiceImpl(
            CommandeClientRepository commandeClientRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository,
            ClientRepository clientRepository,
            ArticleRepository articleRepository
    ) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommandeClientDTO save(CommandeClientDTO commandeClientDTO) {
        List<String> errors = CommandeClientValidator.validate(commandeClientDTO);
        if (!errors.isEmpty()){
            log.error("Commande client is not valid");
            throw new InvalidEntityException(
                    "La commande Client n'est pas valide",
                    ErrorCodes.LIGNE_COMMANDE_CLIENT_NOT_FOUND,
                    errors
            );
        }
        Optional<Client> client = clientRepository.findById(commandeClientDTO.getClient().getId());
        if (client.isEmpty()){
            log.warn("Client with ID {} was not found in the BDD", commandeClientDTO.getClient().getId());
            throw new InvalidEntityException(
                    "Aucun client avec l'ID = " + commandeClientDTO.getClient().getId() + "n'a ete trouve dans la BDD",
                    ErrorCodes.CLIENT_NOT_FOUND
            );
        }

        List<String> articleErrors = new ArrayList<>();

        if(commandeClientDTO.getLigneCommandeClients() != null){
            commandeClientDTO.getLigneCommandeClients().forEach(ligneCommandeClient -> {
                if(ligneCommandeClient.getArticle() != null) {
                    Optional<Article> article = articleRepository.findById(ligneCommandeClient.getArticle().getId());
                    if (article.isEmpty()){
                        articleErrors.add(
                                "l'Article avec l'ID = " + ligneCommandeClient.getArticle().getId() + "n'existe pas"
                        );
                    }
                }else {
                    articleErrors.add(
                            "impossible d'enregistrer une commande avec un article null"
                    );
                }
            });
        }
        if (!articleErrors.isEmpty()){
            log.warn("");
            throw  new InvalidEntityException(
                    "l'Article n'existe pas dans la BDD",
                    ErrorCodes.ARTICLE_NOT_FOUND,
                    articleErrors
            );
        }
        CommandeClient savedCmdClt = commandeClientRepository.save(CommandeClientDTO.toEntity(commandeClientDTO));

        if(commandeClientDTO.getLigneCommandeClients() != null){
            commandeClientDTO.getLigneCommandeClients().forEach(ligneCommandeClientDTO -> {
                        LigneCommandeClient ligneCommandeClient = LigneCommandeClientDTO.toEntity(ligneCommandeClientDTO);
                        ligneCommandeClient.setCommandeClient(savedCmdClt);
                        ligneCommandeClientRepository.save(ligneCommandeClient);
                    }
            );
        }
        return CommandeClientDTO.fromEntity(savedCmdClt);
    }

    @Override
    public CommandeClientDTO findById(Integer id) {
        if (id == null){
            log.error("Commande client ID is NULL");
            return null;
        }
        return commandeClientRepository.findById(id)
                .map(CommandeClientDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune commande client n'a ete trouve avec l'ID =" + id,
                                ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                        ));
    }

    @Override
    public CommandeClientDTO findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("Commande client CODE is NULL");
            return null;
        }
        return commandeClientRepository.findCommandeClientByCode(code)
                .map(CommandeClientDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune commande client n'a ete trouve avec le CODE =" + code,
                                ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
                        ));
    }

    @Override
    public List<CommandeClientDTO> findAll() {
        return commandeClientRepository.findAll().stream()
                .map(CommandeClientDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Commande client ID is NULL");
            return;
        }
        commandeClientRepository.deleteById(id);
    }
}
