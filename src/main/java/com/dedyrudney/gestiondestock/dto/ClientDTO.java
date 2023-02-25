package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ClientDTO {

    private Integer id;

    private String nom;

    private String prenom;

    private AdresseDTO adresse;

    private String photo;

    private String mail;

    private String numTel;

    private Integer idEntreprise;

    @JsonIgnore
    private List<CommandeClientDTO> commandeClients;

    public static ClientDTO fromEntity(Client client) {
        if (client == null) {
            return null;
        }
        return ClientDTO.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .adresse(AdresseDTO.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .mail(client.getMail())
                .numTel(client.getNumTel())
                .idEntreprise(client.getIdEntreprise())
                .build();
    }

    public static Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) {
            return null;
        }
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        client.setAdresse(AdresseDTO.toEntity(clientDTO.getAdresse()));
        client.setPhoto(clientDTO.getPhoto());
        client.setMail(clientDTO.getMail());
        client.setNumTel(clientDTO.getNumTel());
        client.setIdEntreprise(clientDTO.getIdEntreprise());
        return client;
    }

}
