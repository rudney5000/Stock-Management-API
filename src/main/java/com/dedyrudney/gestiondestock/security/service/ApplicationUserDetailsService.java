package com.dedyrudney.gestiondestock.security.service;

import com.dedyrudney.gestiondestock.dto.UtilisateurDTO;
import com.dedyrudney.gestiondestock.security.model.ExtendedUser;
import com.dedyrudney.gestiondestock.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UtilisateurDTO utilisateurDTO = utilisateurService.findByEmail(email);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        utilisateurDTO.getRoles().forEach(rolesDTO -> authorities.add(new SimpleGrantedAuthority(rolesDTO.getRoleName())));

        return new ExtendedUser(utilisateurDTO.getEmail(), utilisateurDTO.getMoteDePasse(), utilisateurDTO.getEntreprise().getId(), authorities);
    }

}
