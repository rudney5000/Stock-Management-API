package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeClientDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

@Api("commandesClients")
public interface CommandeClientApi {

    @PostMapping(APP_ROOT + "commandesclients/create")
    ResponseEntity<CommandeClientDTO> save(@RequestBody CommandeClientDTO commandeClientDTO);

    @PatchMapping(APP_ROOT + "/commandesclients/update/etat/{id_commande}/{etatCommande}")
    ResponseEntity<CommandeClientDTO> updateEtatCommande(@PathVariable("id_commande") Integer id_commande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(APP_ROOT + "/commandesclients/update/quantite/{id_commande}/{id_ligneCommande}/{quantite}")
    ResponseEntity<CommandeClientDTO> updateQuantiteCommande(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(APP_ROOT + "/commandesclients/update/client/{id_commande}/{id_client}/")
    ResponseEntity<CommandeClientDTO> updateClient(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_client") Integer id_client);

    @PatchMapping(APP_ROOT + "/commandesclients/update/article/{id_commande}/{id_ligneCommande}/{id_article}")
    ResponseEntity<CommandeClientDTO> updateArticle(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande, @PathVariable("id_article") Integer new_idArticle);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/article/{id_commande}/{id_ligneCommande}/")
    ResponseEntity<CommandeClientDTO> deleteArticle(@PathVariable("id_commande") Integer id_commande, @PathVariable("id_ligneCommande") Integer id_ligneCommande);

    @GetMapping(APP_ROOT + "/commandesclients/{id_commandeClient}")
    ResponseEntity<CommandeClientDTO> findById(@PathVariable("id_commandeClient") Integer id);

    @GetMapping(APP_ROOT + "/commandesclients/filter/{codeCommandeClient}")
    ResponseEntity<CommandeClientDTO> findByCode(@PathVariable("codeCommandeClient") String code);

    @GetMapping(APP_ROOT + "/commandesclients/all")
    ResponseEntity<List<CommandeClientDTO>> findAll();

    @GetMapping(APP_ROOT + "/commandesclients/lignesCommande/{id_commande}")
    ResponseEntity<List<LigneCommandeClientDTO>> findAllLignesCommandesClientByCommandeClientId(@PathVariable("id_commande") Integer id_commande);

    @DeleteMapping(APP_ROOT + "/commandesclients/delete/{id_commandeClient}")
    ResponseEntity<Void> delete(@PathVariable("id_commandeClient") Integer id);
}
