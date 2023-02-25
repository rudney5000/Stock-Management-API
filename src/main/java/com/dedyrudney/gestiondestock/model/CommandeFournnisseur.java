package com.dedyrudney.gestiondestock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "commandefournisseur")
public class CommandeFournnisseur extends AbstractEntity{

    @Column(name = "code")
    private String code;

    @Column(name = "datecommande")
    private Instant dateCommande;

    @Column(name = "etatcommande")
    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @Column(name = "identreprise")
    private Integer idEntreprise;

    @ManyToOne
    @JoinColumn(name = "idfournisseur")
    private Fournnisseur fournisseur;

    @OneToMany(mappedBy = "commandeFournnisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
}
