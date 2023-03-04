package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.ArticleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/articles")
public interface ArticleApi {

    @PostMapping(
            value = APP_ROOT + "/articles/create/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Enregistrer un article(Ajouter ou bien Modifier)",
            notes = "Cette methode permet d'enregistrer ou de modifier un article",
            response = ArticleDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " L'objet article cree / modifie"),
            @ApiResponse(code = 400, message = "L'objet article n'est pas valide")
    })
    ArticleDTO save(@RequestBody ArticleDTO articleDTO);

    @GetMapping(
            value = APP_ROOT + "/articles/{id_article}/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Recherche un article par ID",
            notes = "Cette methode permet de rechercher un article par son ID",
            response = ArticleDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " L'article a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec l'ID fourni")
    })
    ArticleDTO findById(@PathVariable("id_article") Integer id);

    @GetMapping(
            value = APP_ROOT + "/articles/{codeArticle}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Recherche un article par CODE",
            notes = "Cette methode permet de rechercher un article par son CODE",
            response = ArticleDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " L'article a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun article n'existe dans la BDD avec le CODE fourni")
    })
    ArticleDTO findByCodeArticle(@PathVariable("codeArticle") String codeArticle);

    @GetMapping(
            value = APP_ROOT + "articles/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Renvoi la liste des articles",
            notes = "Cette methode permet de rechercher la liste des articles qui existent dans la BDD",
            responseContainer = "List<ArticleDTO>"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " La liste des articles / Une liste vide")
    })
    List<ArticleDTO> findAll();

    @DeleteMapping(
            value = APP_ROOT + "/articles/delete/{id_article}"
    )
    @ApiOperation(
            value = "Supprimer un article",
            notes = "Cette methode permet de spprimer un article par son ID",
            response = ArticleDTO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " L'article a ete supprime"),
    })
    void delete(@PathVariable("id_article") Integer id);
}
