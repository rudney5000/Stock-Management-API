package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.MvStck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MvStckRepository extends JpaRepository<Integer, MvStck> {
}
