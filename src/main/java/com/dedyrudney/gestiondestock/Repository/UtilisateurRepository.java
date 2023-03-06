package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

//    @Query("select u from Utilisateur u where u.email = : email")
    Optional<Utilisateur> findUtilisateurByEmail(/*@Param("email")*/ String email);

}
