package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.CommandeClientApi;
import com.dedyrudney.gestiondestock.dto.CommandeClientDTO;
import com.dedyrudney.gestiondestock.dto.LigneCommandeClientDTO;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import com.dedyrudney.gestiondestock.service.CommandeCLientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CommandeClientController implements CommandeClientApi {

    private CommandeCLientService commandeCLientService;

    @Autowired
    public CommandeClientController(CommandeCLientService commandeCLientService) {
        this.commandeCLientService = commandeCLientService;
    }

    @Override
    public ResponseEntity<CommandeClientDTO> save(CommandeClientDTO commandeClientDTO) {
        return ResponseEntity.ok(commandeCLientService.save(commandeClientDTO));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> updateEtatCommande(Integer id_commande, EtatCommande etatCommande) {
        return ResponseEntity.ok(commandeCLientService.updateEtatCommande(id_commande, etatCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> updateQuantiteCommande(Integer id_commande, Integer id_ligneCommande, BigDecimal quantite) {
        return ResponseEntity.ok(commandeCLientService.updateQuantiteCommande(id_commande, id_ligneCommande, quantite));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> updateClient(Integer id_commande, Integer id_client) {
        return ResponseEntity.ok(commandeCLientService.updateClient(id_commande, id_client));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> updateArticle(Integer id_commande, Integer id_ligneCommande, Integer new_idArticle) {
        return ResponseEntity.ok(commandeCLientService.updateArticle(id_commande, id_ligneCommande, new_idArticle));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> deleteArticle(Integer id_commande, Integer id_ligneCommande) {
        return ResponseEntity.ok(commandeCLientService.deleteArticle(id_commande, id_ligneCommande));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> findById(Integer id) {
        return ResponseEntity.ok(commandeCLientService.findById(id));
    }

    @Override
    public ResponseEntity<CommandeClientDTO> findByCode(String code) {
        return ResponseEntity.ok(commandeCLientService.findByCode(code));
    }

    @Override
    public ResponseEntity<List<CommandeClientDTO>> findAll() {
        return ResponseEntity.ok(commandeCLientService.findAll());
    }

    @Override
    public ResponseEntity<List<LigneCommandeClientDTO>> findAllLignesCommandesClientByCommandeClientId(Integer id_commande) {
        return ResponseEntity.ok(commandeCLientService.findAllLignesCommandesClientByCommandeClientId(id_commande));
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) {
        commandeCLientService.delete(id);
        return ResponseEntity.ok().build();
    }
}
