package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentesRepository extends JpaRepository<Integer, Ventes> {
}
