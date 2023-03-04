package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.CommandeFournisseurApi;
import com.dedyrudney.gestiondestock.dto.CommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import com.dedyrudney.gestiondestock.service.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    private CommandeFournisseurService commandeFournisseurService;

    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurService) {
        this.commandeFournisseurService = commandeFournisseurService;
    }

    @Override
    public CommandeFournisseurDTO save(CommandeFournisseurDTO commandeFournisseurDTO) {
        return commandeFournisseurService.save(commandeFournisseurDTO);
    }

    @Override
    public CommandeFournisseurDTO updateEtatCommande(Integer id_commande, EtatCommande etatCommande) {
        return commandeFournisseurService.updateEtatCommande(id_commande, etatCommande);
    }

    @Override
    public CommandeFournisseurDTO updateQuantiteCommande(Integer id_commande, Integer id_ligneCommande, BigDecimal quantite) {
        return commandeFournisseurService.updateQuantiteCommande(id_commande, id_ligneCommande, quantite);
    }

    @Override
    public CommandeFournisseurDTO updateFournisseur(Integer id_commande, Integer id_fournisseur) {
        return commandeFournisseurService.updateFournisseur(id_commande, id_fournisseur);
    }

    @Override
    public CommandeFournisseurDTO updateArticle(Integer id_commande, Integer id_ligneCommande, Integer new_idArticle) {
        return commandeFournisseurService.updateArticle(id_commande, id_ligneCommande, new_idArticle);
    }

    @Override
    public CommandeFournisseurDTO deleteArticle(Integer id_commande, Integer id_ligneCommande) {
        return commandeFournisseurService.deleteArticle(id_commande, id_ligneCommande);
    }

    @Override
    public CommandeFournisseurDTO findById(Integer id) {
        return commandeFournisseurService.findById(id);
    }

    @Override
    public CommandeFournisseurDTO findByCode(String code) {
        return commandeFournisseurService.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDTO> findAll() {
        return commandeFournisseurService.findAll();
    }

    @Override
    public List<LigneCommandeFournisseurDTO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer id_commande) {
        return commandeFournisseurService.findAllLignesCommandesFournisseurByCommandeFournisseurId(id_commande);
    }

    @Override
    public void delete(Integer id) {
        commandeFournisseurService.delete(id);
    }
}
