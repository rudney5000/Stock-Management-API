package com.dedyrudney.gestiondestock.security.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ExtendedUser extends User {

    @Setter
    @Getter
    private Integer id_entreprise;

    public ExtendedUser( String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password, Integer id_entreprise, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
        this.id_entreprise = id_entreprise;
    }
}
