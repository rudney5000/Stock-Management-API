package com.dedyrudney.gestiondestock.security.controller;

import com.dedyrudney.gestiondestock.security.dto.AuthenticationRequest;
import com.dedyrudney.gestiondestock.security.dto.AuthenticationResponse;
import com.dedyrudney.gestiondestock.security.service.ApplicationUserDetailsService;
import com.dedyrudney.gestiondestock.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dedyrudney.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticationResponse(@RequestBody AuthenticationRequest authenticationRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authenticationRequest.getLogin(),
//                        authenticationRequest.getPassword()
//                )
//        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());

        final String jwt = JwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken("stand_access_token").build());
    }
}
