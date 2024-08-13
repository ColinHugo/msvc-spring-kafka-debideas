package com.msvc.companiescrud.service;

import com.msvc.companiescrud.models.Company;

public interface CompanyService {

    Company create( Company company );

    Company readByName( String name );

    Company update( Company company, String name );

    void delete( String name );

}