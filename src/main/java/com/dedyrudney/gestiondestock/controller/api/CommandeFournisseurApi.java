package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.CommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeFournisseurDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.CREATE_COMMANDE_FOURNISSEUR_ENDPOINT;

@Api("commandefournisseur")
public interface CommandeFournisseurApi {

    @PostMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT)
    CommandeFournisseurDTO save(@RequestBody CommandeFournisseurDTO commandeFournisseurDTO);

    @PatchMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "update/etat/{id_commande}/{etatCommande}")
    CommandeFournisseurDTO updateEtatCommande(@PathVariable("id_commande") Integer id_commande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/update/quantite/{id_commande}/{id_ligneCommande}/{quantite}")
    CommandeFournisseurDTO updateQuantiteCommande(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/update/fournisseur/{id_commande}/{id_fournisseur}/")
    CommandeFournisseurDTO updateFournisseur(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_fournisseur") Integer id_fournisseur);

    @PatchMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/update/article/{id_commande}/{id_ligneCommande}/{id_article}")
    CommandeFournisseurDTO updateArticle(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande, @PathVariable("id_article") Integer new_idArticle);

    @DeleteMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/article/{id_commande}/{id_ligneCommande}/")
    CommandeFournisseurDTO deleteArticle(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande);

    @GetMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/{id_commandeFournisseur}")
    CommandeFournisseurDTO findById(@PathVariable("id_commandeFournisseur") Integer id);

    @GetMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/filter/{codeCommandeFournisseur}")
    CommandeFournisseurDTO findByCode(@PathVariable("codeCommandeFournisseur") String code);

    @GetMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/all")
    List<CommandeFournisseurDTO> findAll();

    @GetMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/lignesCommande/{id_commande}")
    List<LigneCommandeFournisseurDTO>  findAllLignesCommandesFournisseurByCommandeFournisseurId(@PathVariable("id_commande") Integer id_commande);

    @DeleteMapping(CREATE_COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{id_commandeClient}")
    void delete(@PathVariable("id_commandeClient") Integer id);
}
