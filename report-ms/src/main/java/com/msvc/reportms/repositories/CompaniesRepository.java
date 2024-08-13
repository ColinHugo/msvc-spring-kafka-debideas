package com.msvc.reportms.repositories;

import com.msvc.reportms.models.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// @LoadBalancerClient( name = "companies-crud", configuration = LoadBalancerConfiguration.class )
// @FeignClient( name = "companies-crud", url = "${application.config.company-url}" )
@FeignClient( name = "companies-crud" )
public interface CompaniesRepository {

    @GetMapping( "/companies-crud/company/{name}" )
    Optional< Company > getByName( @PathVariable String name );

    @PostMapping( "/companies-crud/company" )
    Optional< Company > postByName( @RequestBody Company company );

    @DeleteMapping( "/companies-crud/company/{name}" )
    void deleteByName( @PathVariable String name );

}