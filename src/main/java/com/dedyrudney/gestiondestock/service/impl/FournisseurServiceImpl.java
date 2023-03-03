package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.CommandeFournisseurRepository;
import com.dedyrudney.gestiondestock.Repository.FournisseurRepository;
import com.dedyrudney.gestiondestock.dto.FournisseurDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.exception.InvalidOperationException;
import com.dedyrudney.gestiondestock.model.CommandeClient;
import com.dedyrudney.gestiondestock.service.FournisseurService;
import com.dedyrudney.gestiondestock.validator.FournisseurValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {
    
    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;
    
    @Autowired
    public FournisseurServiceImpl(
            FournisseurRepository fournisseurRepository,
            CommandeFournisseurRepository commandeFournisseurRepository
    ){
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }
    
    @Override
    public FournisseurDTO save(FournisseurDTO fournisseurDTO) {
        List<String> errors = FournisseurValidator.validate(fournisseurDTO);
        if (!errors.isEmpty()){
            log.error("Fournisseur is not valid {}", fournisseurDTO);
            throw new InvalidEntityException(
                    "Le fournisseur n'est pas valide",
                    ErrorCodes.FOURNISSEUR_NOT_VALID,
                    errors
            );
        }
        return FournisseurDTO.fromEntity(
                fournisseurRepository.save(
                        FournisseurDTO.toEntity(fournisseurDTO)
                )
        );
    }

    @Override
    public FournisseurDTO findById(Integer id) {
        if (id == null){
            log.error("Fournisseur ID is null");
            return null;
        }
        return fournisseurRepository.findById(id)
                .map(FournisseurDTO::fromEntity)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aucun fournisseur avec l'ID = " + id + " n'a ete trouve dans la BDD",
                                ErrorCodes.FOURNISSEUR_NOT_FOUND
                        )
                );
    }

    @Override
    public List<FournisseurDTO> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null){
            log.error("Fournisseur ID is null");
            return;
        }
        List<CommandeClient> commandeFournisseur = commandeFournisseurRepository.findAllByFournisseurId(id);
        if (!commandeFournisseur.isEmpty()){
            throw new InvalidOperationException(
                    "Impossible de supprimer un fournisseur qui a deja des commandes",
                    ErrorCodes.FOURNISSEUR_ALREADY_IN_USE
            );
        }
        fournisseurRepository.deleteById(id);
    }
}
