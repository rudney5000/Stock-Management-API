package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.ArticleRepository;
import com.dedyrudney.gestiondestock.Repository.ClientRepository;
import com.dedyrudney.gestiondestock.Repository.CommandeClientRepository;
import com.dedyrudney.gestiondestock.Repository.LigneCommandeClientRepository;
import com.dedyrudney.gestiondestock.dto.*;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.model.*;
import com.dedyrudney.gestiondestock.service.CommandeCLientService;
import com.dedyrudney.gestiondestock.service.MvStckService;
import com.dedyrudney.gestiondestock.validator.ArticleValidator;
import com.dedyrudney.gestiondestock.validator.CommandeClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
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
    private MvStckService mvStckService;

    @Autowired
    public CommandeClientServiceImpl(
            CommandeClientRepository commandeClientRepository,
            LigneCommandeClientRepository ligneCommandeClientRepository,
            ClientRepository clientRepository,
            ArticleRepository articleRepository,
            MvStckService mvStckService
    ) {
        this.commandeClientRepository = commandeClientRepository;
        this.ligneCommandeClientRepository = ligneCommandeClientRepository;
        this.clientRepository = clientRepository;
        this.articleRepository = articleRepository;
        this.mvStckService = mvStckService;
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
    public CommandeClientDTO updateEtatCommande(Integer id_commande, EtatCommande etatCommande) {
        checkIdCommande(id_commande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'etat de la commande client is NULL");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat NULL",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
        CommandeClientDTO commandeClient = checkEtaCommande(id_commande);
        commandeClient.setEtatCommande(etatCommande);

        CommandeClient savedCmClt = commandeClientRepository.save(CommandeClientDTO.toEntity(commandeClient));
        if (commandeClient.isCommandeLivree()){
            updateMvtStck(id_commande);
        }
        return CommandeClientDTO.fromEntity(savedCmClt);
    }

    @Override
    public CommandeClientDTO updateQuantiteCommande(Integer id_commande, Integer id_ligneCommande, BigDecimal quantite) {
        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("L'ID de la ligne commande est NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec une quantite NULL ou ZERO",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
        CommandeClientDTO commandeClient = checkEtaCommande(id_commande);
        Optional<LigneCommandeClient> ligneCommandeClientOptional = findLigneCommandeClient(id_ligneCommande);

        LigneCommandeClient ligneCommandeClient = ligneCommandeClientOptional.get();
        ligneCommandeClient.setQuantite(quantite);
        ligneCommandeClientRepository.save(ligneCommandeClient);

        return commandeClient;
    }

    @Override
    public CommandeClientDTO updateClient(Integer id_commande, Integer id_client) {
        checkIdCommande(id_commande);
        if (id_client == null){
            log.error("L'ID du client is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un ID NULL",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }

        CommandeClientDTO commandeClient = checkEtaCommande(id_commande);
        Optional<Client> clientOptional = clientRepository.findById(id_client);
        if (clientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun client n'a ete trouve avec l'ID = " + id_client,
                    ErrorCodes.CLIENT_NOT_FOUND
            );
        }
        commandeClient.setClient(ClientDTO.fromEntity(clientOptional.get()));
        return CommandeClientDTO.fromEntity(
                commandeClientRepository.save(CommandeClientDTO.toEntity(commandeClient))
        );
    }

    @Override
    public CommandeClientDTO updateArticle(Integer id_commande, Integer id_ligneCommande, Integer new_idArticle) {
        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);
        checkIdArticle(new_idArticle, "nouvel");

        CommandeClientDTO commandeClient = checkEtaCommande(id_commande);

        Optional<LigneCommandeClient> ligneCommandeClient = findLigneCommandeClient(id_ligneCommande);

        Optional<Article> articleOptional = articleRepository.findById(new_idArticle);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID" + new_idArticle,
                    ErrorCodes.ARTICLE_NOT_FOUND
            );
        }
        List<String> errors = ArticleValidator.validate(ArticleDTO.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw new InvalidEntityException("Article invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeClient ligneCommandeClientToSaved = ligneCommandeClient.get();
        ligneCommandeClientToSaved.setArticle(articleOptional.get());
        ligneCommandeClientRepository.save(ligneCommandeClientToSaved);

        return commandeClient;
    }

    @Override
    public CommandeClientDTO deleteArticle(
            Integer id_commande,
            Integer id_ligneCommande
    ) {
        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);

        CommandeClientDTO commandeClient = checkEtaCommande(id_commande);
        findLigneCommandeClient(id_ligneCommande);
        ligneCommandeClientRepository.deleteById(id_ligneCommande);
        return commandeClient;
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
    public List<LigneCommandeClientDTO> findAllLignesCommandesClientByCommandeClientId(Integer id_commande) {
        return ligneCommandeClientRepository.findAllByCommandeClientId(id_commande).stream()
                .map(LigneCommandeClientDTO::fromEntity)
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

    private CommandeClientDTO checkEtaCommande(Integer id_commande){
        CommandeClientDTO commandeClient = findById(id_commande);
        if (commandeClient.isCommandeLivree()){
            throw new InvalidOperationException(
                    "Impossible de modifier la commande lorsqu'elle est livree",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
        return commandeClient;
    }

    private Optional<LigneCommandeClient> findLigneCommandeClient(Integer id_ligneCommande) {
        Optional<LigneCommandeClient> ligneCommandeClientOptional = ligneCommandeClientRepository.findById(id_ligneCommande);
        if (ligneCommandeClientOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune ligne de commande client n'a ete trouve avec l'ID " + id_ligneCommande,
                    ErrorCodes.COMMANDE_CLIENT_NOT_FOUND
            );
        }
        return ligneCommandeClientOptional;
    }

    private void checkIdCommande(Integer id_commande){
        if (id_commande == null){
            log.error("Commande client ID is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private void checkIdLigneCommande(Integer id_ligneCommande){
        if (id_ligneCommande == null){
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec une ligne de commande NULL",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private void checkIdArticle(Integer id_article, String msg){
        if (id_article == null){
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un " + msg + " ID article NULL",
                    ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE
            );
        }
    }

    private void updateMvtStck(Integer id_commande){
        List<LigneCommandeClient> ligneCommandeClient = ligneCommandeClientRepository.findAllByCommandeClientId(id_commande);
        ligneCommandeClient.forEach(lig -> {
            effectuerSortie(lig);
        });
    }
    private void effectuerSortie(LigneCommandeClient lig) {
        MvStckDTO mvStckDTO = MvStckDTO.builder()
                .article(ArticleDTO.fromEntity(lig.getArticle()))
                .dateMvt(Instant.now())
                .typeMvt(TypeMvtStk.ENTREE)
                .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                .quantite(lig.getQuantite())
                .idEntreprise(lig.getIdEntreprise())
                .build();
        mvStckService.entreeStock(mvStckDTO);
    }
}
