package com.dedyrudney.gestiondestock.controller;

import com.dedyrudney.gestiondestock.controller.api.CategoryApi;
import com.dedyrudney.gestiondestock.dto.CategoryDTO;
import com.dedyrudney.gestiondestock.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        return categoryService.save(categoryDTO);
    }

    @Override
    public CategoryDTO findById(Integer id_category) {
        return categoryService.findById(id_category);
    }

    @Override
    public CategoryDTO findByCode(String codeCategory) {
        return categoryService.findByCode(codeCategory);
    }

    @Override
    public List<CategoryDTO> findAll() {
        return categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        categoryService.delete(id);
    }
}
