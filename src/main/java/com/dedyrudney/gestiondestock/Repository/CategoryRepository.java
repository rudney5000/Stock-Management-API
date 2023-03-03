package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
