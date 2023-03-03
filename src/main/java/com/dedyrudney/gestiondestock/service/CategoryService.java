package com.dedyrudney.gestiondestock.service;

import com.dedyrudney.gestiondestock.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO findById(Integer id);

    CategoryDTO findByCode(String code);

    List<CategoryDTO> findAll();

    void delete(Integer id);
}
