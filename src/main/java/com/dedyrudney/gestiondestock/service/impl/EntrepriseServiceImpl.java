package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.EntrepriseRepository;
import com.dedyrudney.gestiondestock.Repository.RolesRepository;
import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.service.EntrepriseService;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import com.dedyrudney.gestiondestock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService{

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;

    @Autowired
    public EntrepriseServiceImpl(
            EntrepriseRepository entrepriseRepository,
            UtilisateurService utilisateurService,
            RolesRepository rolesRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public EntrepriseDTO save(EntrepriseDTO entrepriseDTO) {
        List<String> errors = EntrepriseValidator.validate(entrepriseDTO);
        if (!errors.isEmpty()){
            log.error("Entreprise is not valid {}", entrepriseDTO);
            throw new InvalidEntityException(
                    "L'entrprise n'est pas valid",
                    ErrorCodes.ENTREPRISE_NOT_VALID,
                    errors
            );
        }
        EntrepriseDTO savedEntreprise = EntrepriseDTO.fromEntity(
                entrepriseRepository.save(EntrepriseDTO.toEntity(entrepriseDTO))
        );

        UtilisateurDTO utilisateur = fromEntreprise(savedEntreprise);
        return null;
    }



    @Override
    public EntrepriseDTO findById(Integer id) {
        if (id == null){
            log.error("Entreprise ID is NULL");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucune entreprise avec l'ID = " + id + " n'a ete trouve dans la BDD",
                                ErrorCodes.ENTREPRISE_NOT_FOUND
                        )
                );
    }

    @Override
    public List<EntrepriseDTO> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Entreprise ID is NULL");
            return;
        }
        entrepriseRepository.deleteById(id);
    }

    private UtilisateurDTO fromEntreprise(EntrepriseDTO entrepriseDTO) {
        return UtilisateurDTO.builder()
                .adresse(entrepriseDTO.getAdresse())
                .nom(entrepriseDTO.getNom())
                .prenom(entrepriseDTO.getCodeFiscal())
                .email(entrepriseDTO.getEmail())
                .moteDePasse(generateRandomPassword())
                .entreprise(entrepriseDTO)
                .dateDeNaissance(Instant.now())
                .photo(entrepriseDTO.getPhoto())
                .build();
    }
    private String generateRandomPassword(){
        return "som3R@nd0mP@$$word";
    }
}
