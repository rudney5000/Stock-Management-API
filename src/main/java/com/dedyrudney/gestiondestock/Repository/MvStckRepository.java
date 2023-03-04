package com.dedyrudney.gestiondestock.Repository;

import com.dedyrudney.gestiondestock.model.MvStck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MvStckRepository extends JpaRepository<MvStck, Integer> {

    @Query("select sum(m.quantite) from MvStck m where m.article.id = :id_article")
    BigDecimal stockReelArticle(@Param("id_article") Integer id_article);

    List<MvStck> findAllByArticle_Id(Integer id_article);
}
