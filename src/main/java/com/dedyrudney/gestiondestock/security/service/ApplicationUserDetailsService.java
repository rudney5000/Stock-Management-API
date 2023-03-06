package com.dedyrudney.gestiondestock.security.service;

import com.dedyrudney.gestiondestock.Repository.UtilisateurRepository;
import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.exception.EntityNotFoundException;
import com.dedyrudney.gestiondestock.exception.ErrorCodes;
import com.dedyrudney.gestiondestock.model.Utilisateur;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
//    @Autowired
//    private UtilisateurService utilisateurService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UtilisateurDTO utilisateurDTO = utilisateurService.findByEmail(email);
//
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        utilisateurDTO.getRoles().forEach(rolesDTO -> authorities.add(
//                new SimpleGrantedAuthority(rolesDTO.getRoleName())
//        ));
//
//        return new User(utilisateurDTO.getEmail(), utilisateurDTO.getMoteDePasse(), Collections.emptyList());
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucun utilisateur avec l'email fourni",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                )
        );

        return new User(utilisateur.getEmail(), utilisateur.getMoteDePasse(), Collections.emptyList());
//        return new User("dedy", "dedy", Collections.emptyList());

    }
}
