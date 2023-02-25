package com.dedyrudney.gestiondestock.dto;

import com.dedyrudney.gestiondestock.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RolesDTO {

    private Integer id;

    private String roleName;

    @JsonIgnore
    private UtilisateurDTO utilisateur;

    public static RolesDTO fromEntity(Roles roles) {
        if (roles == null) {
            return null;
        }
        return RolesDTO.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .build();
    }

    public static Roles toEntity(RolesDTO rolesDTO) {
        if (rolesDTO == null) {
            return null;
        }
        Roles roles = new Roles();
        roles.setId(rolesDTO.getId());
        roles.setRoleName(rolesDTO.getRoleName());
        roles.setUtilisateur(UtilisateurDTO.toEntity(rolesDTO.getUtilisateur()));
        return roles;
    }
}
