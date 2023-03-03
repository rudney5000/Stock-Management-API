package com.dedyrudney.gestiondestock.service.impl;

import com.dedyrudney.gestiondestock.Repository.EntrepriseRepository;
import com.dedyrudney.gestiondestock.Repository.RolesRepository;
import com.dedyrudney.gestiondestock.Repository.UtilisateurRepository;
import com.dedyrudney.gestiondestock.dto.EntrepriseDTO;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.exception.InvalidEntityException;
import com.dedyrudney.gestiondestock.service.EntrepriseService;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import com.dedyrudney.gestiondestock.validator.EntrepriseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional(rollbackOn = Exception.class)
@Service
@Slf4j
public class EntrepriseServiceImpl {

}
