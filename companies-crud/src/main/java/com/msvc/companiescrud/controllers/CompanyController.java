package com.msvc.companiescrud.controllers;

import com.msvc.companiescrud.models.Company;
import com.msvc.companiescrud.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping( "/company" )
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping( "{name}" )
    public ResponseEntity< Company > get( @PathVariable String name ) {
        log.info( "GET:company {}", name );
        return new ResponseEntity<>( companyService.readByName( name ), HttpStatus.OK );
    }

    @PostMapping
    public ResponseEntity< Company > post( @RequestBody Company company ) {
        log.info( "POST:company {}", company.getName() );
        return new ResponseEntity<>( companyService.create( company ), HttpStatus.CREATED );
    }

    @PutMapping( "{name}" )
    public ResponseEntity< Company > put( @RequestBody Company company, @PathVariable String name ) {
        log.info( "PUT:company {}", name );
        return new ResponseEntity<>( companyService.update( company, name ), HttpStatus.OK );
    }

    @DeleteMapping( "{name}" )
    public ResponseEntity< Void > delete( @PathVariable String name ) {
        log.info( "DELETE:company {}", name );
        companyService.delete( name );
        return ResponseEntity.noContent().build();
    }

}