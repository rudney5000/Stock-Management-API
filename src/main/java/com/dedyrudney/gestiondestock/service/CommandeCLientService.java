package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeClientDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeCLientService {

    CommandeClientDTO save(CommandeClientDTO commandeClientDTO);

    CommandeClientDTO updateEtatCommande(Integer id_commande, EtatCommande etatCommande);

    CommandeClientDTO updateQuantiteCommande(Integer id_commande, Integer id_ligneCommande, BigDecimal quantite);

    CommandeClientDTO updateClient(Integer id_commande, Integer id_client);

    CommandeClientDTO updateArticle(Integer id_commande, Integer id_ligneCommande, Integer new_idArticle);

    CommandeClientDTO deleteArticle(Integer id_commande, Integer id_ligneCommande);

    CommandeClientDTO findById(Integer id);

    CommandeClientDTO findByCode(String code);

    List<CommandeClientDTO> findAll();

    List<LigneCommandeClientDTO> findAllLignesCommandesClientByCommandeClientId(Integer id_commande);

    void delete(Integer id);
}
