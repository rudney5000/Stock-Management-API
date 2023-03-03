package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.CommandeClient;
import com.dedyrudney.gestiondestock.model.CommandeFournnisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournnisseur, Integer> {

    Optional<CommandeFournnisseur> findCommandeFournnisseurByCode(String code);

    List<CommandeClient> findAllByFournisseurId(Integer id);
}
