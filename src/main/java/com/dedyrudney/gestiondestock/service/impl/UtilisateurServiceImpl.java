package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.UtilisateurRepository;
import com.dedyrudney.gestiondestock.dto.ChangeMotDePasseUtilisateurDTO;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import com.dedyrudney.gestiondestock.validator.UtilisateurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository){
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDTO save(UtilisateurDTO utilisateurDTO) {
        List<String> errors = UtilisateurValidator.validate(utilisateurDTO);
        if (!errors.isEmpty()){
            log.error("Utilisateur is not valid {}", utilisateurDTO);
            throw  new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }

        return UtilisateurDTO.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDTO.toEntity(utilisateurDTO)
                )
        );
    }


    @Override
    public UtilisateurDTO findById(Integer id) {
        if (id == null){
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateurRepository.findById(id)
                .map(UtilisateurDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID = " + id + "n'ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
                );
    }

    @Override
    public List<UtilisateurDTO> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Utilisateur ID is null");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDTO findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun utilisateur avec l'email = " + email + "n'ete trouve dans la BDD",
                                ErrorCodes.UTILISATEUR_NOT_FOUND
                        )
                );
    }

    @Override
    public UtilisateurDTO chanceMotDePasse(ChangeMotDePasseUtilisateurDTO changeMotDePasseUtilisateurDTO) {
        validate(changeMotDePasseUtilisateurDTO);
        return null;
    }

    private void validate(ChangeMotDePasseUtilisateurDTO changeMotDePasseUtilisateurDTO){
        if (changeMotDePasseUtilisateurDTO == null){
            log.warn("Impossible de modifier le mot de passe avec un objet NULL");
            throw new InvalidOperationException(
                    "Aucune information n'a ete fourni pour pouvoir changer le mot de passe ",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if (changeMotDePasseUtilisateurDTO.getId() == null){
            log.warn("Impossible de modifier le mot de passe avec un ID NULL");
            throw new InvalidOperationException(
                    "ID utilisateur null: Impossible de modifier le mot le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(changeMotDePasseUtilisateurDTO.getMotDePasse()) || !StringUtils.hasLength(changeMotDePasseUtilisateurDTO.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec un mot de passe NULL");
            throw new InvalidOperationException(
                    " Mot de passe utilisateur NULL:: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!changeMotDePasseUtilisateurDTO.getMotDePasse().equals(changeMotDePasseUtilisateurDTO.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe different");
            throw new InvalidOperationException(
                    "Mots de passe utilisateur non conformes:: Impossible de modifier le mot de passe",
                    ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }

}
