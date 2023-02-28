package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Integer, Entreprise> {
}
