package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findAllByEmail(String email);

}
