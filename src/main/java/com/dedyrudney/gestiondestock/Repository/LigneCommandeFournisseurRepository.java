package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {
    List<LigneCommandeFournisseur> findAllCommandeFournisseurId(Integer id);

    List<LigneCommandeFournisseur> findAllByArticle_Id(Integer id_commande);
}
