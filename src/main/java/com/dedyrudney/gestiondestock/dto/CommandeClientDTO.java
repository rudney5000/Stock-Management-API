package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.CommandeClient;
import com.dedyrudney.gestiondestock.model.EtatCommande;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class CommandeClientDTO {

    private Integer id;

    private String code;

    private Instant dateCommande;

    private EtatCommande etatCommande;

    private Integer idEntreprise;

    private ClientDTO client;

    private List<LigneCommandeClientDTO> ligneCommandeClients;

    public static CommandeClientDTO fromEntity(CommandeClient commandeClient) {
        if (commandeClient == null) {
            return null;
        }
        return CommandeClientDTO.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .etatCommande(commandeClient.getEtatCommande())
                .client(ClientDTO.fromEntity(commandeClient.getClient()))
                .idEntreprise(commandeClient.getIdEntreprise())
                .build();

    }

    public static CommandeClient toEntity(CommandeClientDTO commandeClientDTO) {
        if (commandeClientDTO == null) {
            return null;
        }
        CommandeClient commandeClient = new CommandeClient();
        commandeClient.setId(commandeClientDTO.getId());
        commandeClient.setCode(commandeClientDTO.getCode());
        commandeClient.setClient(ClientDTO.toEntity(commandeClientDTO.getClient()));
        commandeClient.setDateCommande(commandeClientDTO.getDateCommande());
        commandeClient.setEtatCommande(commandeClientDTO.getEtatCommande());
        commandeClient.setIdEntreprise(commandeClientDTO.getIdEntreprise());
        return commandeClient;
    }

    public boolean isCommandeLivree() {
        return EtatCommande.LIVREE.equals(this.etatCommande);
    }
}
