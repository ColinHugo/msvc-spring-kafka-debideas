package com.fallback.companies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping( "/company" )
public class CompanyController {

    private static final Company DEFAULT_COMPANY = Company
            .builder()
            .id( 0L )
            .founder( "Fallback" )
            .name( "Fallback company" )
            .logo( "http://default-logo.com" )
            .foundationDate( LocalDate.now() )
            .webSites( Collections.emptyList() )
            .build();

    @GetMapping( "{name}" )
    public ResponseEntity< Company > get( @PathVariable String name ) {
        log.warn( "GET in fallback company {}", name );
        return ResponseEntity.ok( DEFAULT_COMPANY );
    }

}