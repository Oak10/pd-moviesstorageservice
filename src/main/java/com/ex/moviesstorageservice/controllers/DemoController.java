package com.ex.moviesstorageservice.controllers;

import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DemoController {

    @GetMapping(value="/demo")
    public String adminEndPoint(@RequestHeader("Authorization") String token) throws VerificationException {
        // TODO: Use spring security for this (spring-boot-starter-oauth2-resource-server)
        AccessToken actoken = TokenVerifier.create(
                token.replace("Bearer ", ""), AccessToken.class).getToken();

        System.out.println("Email = [" +  actoken.getEmail() + "]");
        return "Hello From Admin --";
    }


    @GetMapping(value="/manager")
    public String managerEndPoint() {
        return "Hello From Manager --";
    }
}