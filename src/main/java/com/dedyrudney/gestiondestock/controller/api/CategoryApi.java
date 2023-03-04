package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import com.dedyrudney.gestiondestock.dto.CategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

@Api("categories")
public interface CategoryApi {

    @PostMapping(
            value = APP_ROOT + "/categories/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Enregistrer une categorie(Ajouter ou bien Modifier)",
            notes = "Cette methode permet d'enregistrer ou de modifier une categorie",
            response = CategoryDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " L'objet categorie cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet categorie n'est pas valide")
    })
    CategoryDTO save(@RequestBody CategoryDTO categoryDTO);

    @GetMapping(
            value = APP_ROOT + "/categories/{id_category}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Recherche une categorie par ID",
            notes = "Cette methode permet de rechercher une categorie par son ID",
            response = CategoryDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " La categorie a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la BDD avec l'ID fourni")
    })
    CategoryDTO findById(@PathVariable("id_category") Integer id_category);

    @GetMapping(
            value = APP_ROOT + "/categories/filter/{code_category}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Recherche une categorie par CODE",
            notes = "Cette methode permet de rechercher une categorie par son CODE",
            response = CategoryDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " La categorie a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune categorie n'existe dans la BDD avec le CODE fourni")
    })
    CategoryDTO findByCode(@PathVariable("code_category") String codeCategory);

    @GetMapping(
            value = APP_ROOT + "/categories/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Renvoi la liste des categories",
            notes = "Cette methode permet de rechercher la liste des categories qui existent dans la BDD",
            responseContainer = "List<CategoryDTO>"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " La liste des categories / Une liste vide")
    })
    List<CategoryDTO> findAll();

    @DeleteMapping(
            value = APP_ROOT + "/categories/delete/{id_category}"
    )
    @ApiOperation(
            value = "Supprimer une category",
            notes = "Cette methode permet de spprimer une categorie par son ID",
            response = ArticleDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " La category a ete supprime"),
    })
    void delete(@PathVariable("id_category") Integer id);
}
