package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.ArticleRepository;
import com.dedyrudney.gestiondestock.Repository.LigneVenteRepository;
import com.dedyrudney.gestiondestock.Repository.VentesRepository;
import com.dedyrudney.gestiondestock.dto.LigneVenteDTO;
import com.dedyrudney.gestiondestock.dto.VentesDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.model.Article;
import com.dedyrudney.gestiondestock.model.LigneVente;
import com.dedyrudney.gestiondestock.model.Ventes;
import com.dedyrudney.gestiondestock.service.VentesService;
import com.dedyrudney.gestiondestock.validator.VentesValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;

    @Autowired
    public VentesServiceImpl(
            ArticleRepository articleRepository,
            VentesRepository ventesRepository,
            LigneVenteRepository ligneVenteRepository
    ) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
    }

    @Override
    public VentesDTO save(VentesDTO ventesDTO) {
        List<String> errors = VentesValidator.validate(ventesDTO);
        if (!errors.isEmpty()){
            log.error("Ventes n'est pas valide");
            throw  new InvalidEntityException("", ErrorCodes.VENTE_NOT_VALID);
        }

        List<String> articleErrors = new ArrayList<>();

        ventesDTO.getLigneVentes().forEach(ligneVenteDTO -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDTO.getArticle().getId());
            if (article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDTO.getArticle().getId() + " n'a ete trouve dans la BDD");
            }
        });
        if (!articleErrors.isEmpty()){
            log.error("One or more article not found in the DB, {}", errors);
            throw new InvalidEntityException(
                    "Un ou plusieurs articles n'ont pas ete trouve dans la BDD",
                    ErrorCodes.VENTE_NOT_VALID,
                    errors
            );
        }
        Ventes savedVentes = ventesRepository.save(VentesDTO.toEntity(ventesDTO));

        ventesDTO.getLigneVentes().forEach(ligneVenteDTO -> {
            LigneVente ligneVente = LigneVenteDTO.toEntity(ligneVenteDTO);
            ligneVente.setVente(savedVentes);
            ligneVenteRepository.save(ligneVente);
        });
        return VentesDTO.fromEntity(savedVentes);
    }

    @Override
    public VentesDTO findById(Integer id) {
        if (id == null){
            log.warn("Ventes ID is NULL");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune Vente n'a ete trouve dans la BDD",
                                ErrorCodes.VENTE_NOT_FOUND
                        )
                );
    }

    @Override
    public VentesDTO findByCode(String code) {
        if (!StringUtils.hasLength(code)){
            log.error("Ventes CODE is NULL");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune vente n'a ete trouve avec le CODE " + code,
                                ErrorCodes.VENTE_NOT_VALID
                        )
                );
    }

    @Override
    public List<VentesDTO> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Ventes ID is NULL");
            return;
        }
        ventesRepository.deleteById(id);
    }
}
