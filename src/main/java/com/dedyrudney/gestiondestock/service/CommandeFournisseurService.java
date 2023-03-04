package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.CommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeFournisseurService {

    CommandeFournisseurDTO save(CommandeFournisseurDTO commandeFournisseurDTO);

    CommandeFournisseurDTO updateEtatCommande(Integer id_commande, EtatCommande etatCommande);

    CommandeFournisseurDTO updateQuantiteCommande(Integer id_commande, Integer id_ligneCommande, BigDecimal quantite);

    CommandeFournisseurDTO updateFournisseur(Integer id_commande, Integer id_fournisseur);

    CommandeFournisseurDTO updateArticle(Integer id_commande, Integer id_ligneCommande, Integer id_article);

    CommandeFournisseurDTO deleteArticle(Integer id_commande, Integer id_ligneCommande);

    CommandeFournisseurDTO findById(Integer id);

    CommandeFournisseurDTO findByCode(String code);

    List<CommandeFournisseurDTO> findAll();

    List<LigneCommandeFournisseurDTO> findAllLignesCommandesFournisseurByCommandeFournisseurId(Integer id_commande);

    void delete(Integer id);
}
