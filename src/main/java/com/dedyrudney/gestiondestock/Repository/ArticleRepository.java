package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Integer, Article> {

}
