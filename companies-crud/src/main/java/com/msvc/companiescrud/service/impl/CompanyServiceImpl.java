package com.msvc.companiescrud.service.impl;

import com.msvc.companiescrud.models.Category;
import com.msvc.companiescrud.models.Company;
import com.msvc.companiescrud.repository.CompanyRepository;
import com.msvc.companiescrud.service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company create( Company company ) {

        company.getWebSites().forEach( webSite -> {
            if ( Objects.isNull( webSite.getCategory() ) ) {
                webSite.setCategory( Category.NONE );
            }
        } );

        return companyRepository.save( company );
    }

    @Override
    public Company readByName( String name ) {
        return companyRepository.findByName( name )
                .orElseThrow( () -> new NoSuchElementException( "Company not found!" ) );
    }

    @Override
    public Company update( Company company, String name ) {

        Company companyToUpdate = readByName( name );
        companyToUpdate.setLogo( company.getLogo() );
        companyToUpdate.setFoundationDate( company.getFoundationDate() );
        companyToUpdate.setFounder( company.getFounder() );

        return companyRepository.save( companyToUpdate );

    }

    @Override
    public void delete( String name ) {

        Company companyToDelete = readByName( name );

        companyRepository.delete( companyToDelete );

    }

}