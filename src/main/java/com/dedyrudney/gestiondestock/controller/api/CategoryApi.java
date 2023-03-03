package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.CategoryDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

public interface CategoryApi {

    @PostMapping(
            value = APP_ROOT + "/categories/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryDTO save(@RequestBody CategoryDTO categoryDTO);

    @GetMapping(
            value = APP_ROOT + "/categories/{id_category}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryDTO findById(@PathVariable("id_category") Integer id_category);

    @GetMapping(
            value = APP_ROOT + "/categories/filter/{code_category}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    CategoryDTO findByCode(@PathVariable("code_category") String codeCategory);

    @GetMapping(
            value = APP_ROOT + "/categories/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CategoryDTO> findAll();

    @DeleteMapping(
            value = APP_ROOT + "/categories/delete/{id_category}"
    )
    void delete(@PathVariable("id_category") Integer id);
}
