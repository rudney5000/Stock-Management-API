package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Integer, Client> {
}
