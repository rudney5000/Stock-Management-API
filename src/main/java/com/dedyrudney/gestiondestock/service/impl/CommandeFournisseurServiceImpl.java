package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.ArticleRepository;
import com.dedyrudney.gestiondestock.Repository.CommandeFournisseurRepository;
import com.dedyrudney.gestiondestock.Repository.FournisseurRepository;
import com.dedyrudney.gestiondestock.Repository.LigneCommandeFournisseurRepository;
import com.dedyrudney.gestiondestock.dto.*;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.model.*;
import com.dedyrudney.gestiondestock.service.CommandeFournisseurService;
import com.dedyrudney.gestiondestock.service.MvStckService;
import com.dedyrudney.gestiondestock.validator.ArticleValidator;
import com.dedyrudney.gestiondestock.validator.CommandeFournisseurValidator;
import lombok.extern.slf4j.Slf4j;
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
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvStckService mvStckService;

    public CommandeFournisseurServiceImpl(
            CommandeFournisseurRepository commandeFournisseurRepository,
            LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
            FournisseurRepository fournisseurRepository,
            ArticleRepository articleRepository,
            MvStckService mvStckService
    ) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvStckService = mvStckService;
    }

    @Override
    public CommandeFournisseurDTO save(CommandeFournisseurDTO commandeFournisseurDTO) {
        List<String> errors = CommandeFournisseurValidator.validate(commandeFournisseurDTO);
        if (!errors.isEmpty()){
            log.error("Commande fournisseur n'est pas valide");
            throw new InvalidEntityException(
                    "La commande fournisseur n'est pas valide",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID,
                    errors
            );
        }
        if (commandeFournisseurDTO.getId() != null && commandeFournisseurDTO.isCommandeLivree()){
            throw new InvalidOperationException(
                    "Impossible de modifier la commande lorsqu'elle est livree",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(commandeFournisseurDTO.getFournisseur().getId());
        if (fournisseur.isEmpty()){
            log.warn("Fournisseur with ID {} was not found in the BDD", commandeFournisseurDTO.getFournisseur().getId());
            throw new EntityNotFoundException(
                    "Aucun fournisseur avec l'ID" + commandeFournisseurDTO.getFournisseur().getId() + "n'a ete trouve dans la BDD",
                    ErrorCodes.FOURNISSEUR_NOT_FOUND
            );
        }

        List<String> articlesErrors = new ArrayList<>();
        if (commandeFournisseurDTO.getLigneCommandeFournisseurs() != null){
            commandeFournisseurDTO.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                if (ligCmdFrs.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdFrs.getArticle().getId());
                    if (article.isEmpty()){
                        articlesErrors.add("L'article avec l'ID" + ligCmdFrs.getArticle().getId() + "n'existe pas dans la BDD");
                    }
                }else {
                    articlesErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                }
            });
        }
        if (!articlesErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException(
                    "Article n'existe pas dans la BDD",
                    ErrorCodes.ARTICLE_NOT_FOUND,
                    articlesErrors
            );
        }
        commandeFournisseurDTO.setDateCommande(Instant.now());
        CommandeFournnisseur saveCmdFrs = commandeFournisseurRepository.save(CommandeFournisseurDTO.toEntity(commandeFournisseurDTO));

        if (commandeFournisseurDTO.getLigneCommandeFournisseurs() != null){
            commandeFournisseurDTO.getLigneCommandeFournisseurs().forEach(ligCmdFrs -> {
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDTO.toEntity(ligCmdFrs);
                ligneCommandeFournisseur.setCommandeFournnisseur(saveCmdFrs);
                ligneCommandeFournisseur.setIdEntreprise(saveCmdFrs.getIdEntreprise());
                LigneCommandeFournisseur saveLigne = ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

                effectuerEntree(saveLigne);
            });
        }
        return CommandeFournisseurDTO.fromEntity(saveCmdFrs);
    }

    @Override
    public CommandeFournisseurDTO updateEtatCommande(Integer id_commande, EtatCommande etatCommande) {
        checkIdCommande(id_commande);
        if (!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'etat de la commande fournisseur is NULL");
            throw new InvalidEntityException(
                    "Impossible de modifier l'etat de la commandde avec un etat null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        CommandeFournisseurDTO commandeFournisseur = checkEtaCommande(id_commande);
        commandeFournisseur.setEtatCommande(etatCommande);

        CommandeFournnisseur savedCommande = commandeFournisseurRepository.save(CommandeFournisseurDTO.toEntity(commandeFournisseur));
        if (commandeFournisseur.isCommandeLivree()){
            updateMvtStck(id_commande);
        }
        return CommandeFournisseurDTO.fromEntity(savedCommande);
    }

    @Override
    public CommandeFournisseurDTO updateQuantiteCommande(
            Integer id_commande,
            Integer id_ligneCommande,
            BigDecimal quantite
    ) {

        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("l'ID de la ligne de commande is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec une quantite Null ou ZERO",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }

        CommandeFournisseurDTO commandeFournisseur = checkEtaCommande(id_commande);
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(id_ligneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO updateFournisseur(
            Integer id_commande,
            Integer id_fournisseur
    ) {
        checkIdCommande(id_commande);
        if (id_fournisseur == null){
            log.error("l'ID du fournisseur is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un ID fournisseur NULL",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        CommandeFournisseurDTO commandeFournisseur = checkEtaCommande(id_commande);
        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(id_fournisseur);
        if (fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucun fournisseur n'a ete trouve avec l'ID = " + id_fournisseur,
                    ErrorCodes.FOURNISSEUR_NOT_FOUND
            );
        }
        commandeFournisseur.setFournisseur(FournisseurDTO.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDTO.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDTO.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDTO updateArticle(
            Integer id_commande,
            Integer id_ligneCommande,
            Integer id_article
    ) {
        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);
        checkIdArticle(id_article, "nouvel");

        CommandeFournisseurDTO commandeFournisseur = checkEtaCommande(id_commande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseur = findLigneCommandeFournisseur(id_ligneCommande);

        Optional<Article> articleOptional = articleRepository.findById(id_article);
        if (articleOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune article n'a ete trouve avec l'ID" + id_article,
                    ErrorCodes.ARTICLE_NOT_FOUND
            );
        }

        List<String> errors = ArticleValidator.validate(ArticleDTO.fromEntity(articleOptional.get()));
        if (!errors.isEmpty()){
            throw new InvalidEntityException(
                    "Article invalid",
                    ErrorCodes.ARTICLE_NOT_VALID,
                    errors
            );
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseur.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO deleteArticle(
            Integer id_commande,
            Integer id_ligneCommande
    ) {
        checkIdCommande(id_commande);
        checkIdLigneCommande(id_ligneCommande);

        CommandeFournisseurDTO commandeFournisseur = checkEtaCommande(id_commande);
        findLigneCommandeFournisseur(id_ligneCommande);
        ligneCommandeFournisseurRepository.deleteById(id_ligneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDTO findById(Integer id) {
        if (id == null){
            log.error("Commande fournisseur ID is NULL");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucune commande fournisseur n'a ete trouve avec l'ID = " + id,
                        ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public CommandeFournisseurDTO findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("Commande fournisseur CODE is NULL");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournnisseurByCode(code)
                .map(CommandeFournisseurDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune commande fournisseur n'a ete trouve avec le CODE " + code,
                                ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
                        )
                );
    }

    @Override
    public List<CommandeFournisseurDTO> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer id_commande) {
        return ligneCommandeFournisseurRepository.findAllCommandeFournisseurId(id_commande).stream()
                .map(LigneCommandeFournisseurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Commande fournisseur ID is NULL");
            return;
        }
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllCommandeFournisseurId(id);
        if (!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException(
                    "Impossible de supprimer une commande fournisseur deja utilisee",
                    ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE
            );
        }
        commandeFournisseurRepository.deleteById(id);
    }

    private CommandeFournisseurDTO checkEtaCommande(Integer id_commande){
        CommandeFournisseurDTO commandeFournisseur = findById(id_commande);
        if (commandeFournisseur.isCommandeLivree()){
            throw new InvalidOperationException(
                    "Impossible de modifier la commande lorsqu'elle est livree",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
        return commandeFournisseur;
    }

    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer id_ligneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(id_ligneCommande);
        if (ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException(
                    "Aucune ligne de commande fournisseur n'a ete trouve avec l'ID " + id_ligneCommande,
                    ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND
            );
        }
        return ligneCommandeFournisseurOptional;
    }

    private void checkIdCommande(Integer id_commande){
        if (id_commande == null){
            log.error("Commande fournisseur ID is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un ID null",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
    }

    private void checkIdLigneCommande(Integer id_ligneCommande){
        if (id_ligneCommande == null){
            log.error("L'ID de la ligne commande is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec une ligne de commande NULL",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
    }

    private void checkIdArticle(Integer id_article, String msg){
        if (id_article == null){
            log.error("L'ID de " + msg + " is NULL");
            throw new InvalidOperationException(
                    "Impossible de modifier l'etat de la commande avec un " + msg + " ID article NULL",
                    ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE
            );
        }
    }

    private void updateMvtStck(Integer id_commande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseur = ligneCommandeFournisseurRepository.findAllCommandeFournisseurId(id_commande);
        ligneCommandeFournisseur.forEach(lig -> {
            effectuerEntree(lig);
        });
    }
    private void effectuerEntree(LigneCommandeFournisseur lig) {
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
