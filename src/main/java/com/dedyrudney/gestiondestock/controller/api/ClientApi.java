package com.dedyrudney.gestiondestock.controller.api;

import com.dedyrudney.gestiondestock.dto.ClientDTO;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dedyrudney.gestiondestock.utils.Constants.APP_ROOT;
@Api("clients")
public interface ClientApi {

    @PostMapping(
            value = APP_ROOT + "clients/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ClientDTO save(@RequestBody ClientDTO clientDTO);

    @GetMapping(
            value = APP_ROOT + "clients/{id_client}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ClientDTO findById(@PathVariable("id_client") Integer id);

    @GetMapping(
            value = APP_ROOT + "clients/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<ClientDTO> findAll();

    @DeleteMapping(
            value = APP_ROOT + "clients/delete/{id_client}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void delete(@PathVariable("id_client") Integer id);
}
