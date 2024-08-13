package com.msvc.reportms.service.impl;

import com.msvc.reportms.helpers.ReportHelper;
import com.msvc.reportms.models.Company;
import com.msvc.reportms.models.WebSite;
import com.msvc.reportms.repositories.CompaniesFallbackRepository;
import com.msvc.reportms.repositories.CompaniesRepository;
import com.msvc.reportms.service.ReportService;
import com.msvc.reportms.streams.ReportPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    private final CompaniesRepository companiesRepository;
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final ReportPublisher reportPublisher;

    private final ReportHelper reportHelper;

    @Override
    public String makeReport( String name ) {

        // return this.reportHelper.readTemplate( this.companiesRepository.getByName( name ).orElseThrow() );

        CircuitBreaker circuitBreaker = this.circuitBreakerFactory.create( "companies-circuitbreaker" );

        return circuitBreaker.run(
                () -> this.makeReportMain( name ),
                throwable -> this.makeReportFallback( name, throwable )
        );

    }

    @Override
    public String saveReport( String report ) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
        List< String > placeHolders = this.reportHelper.getPlaceHoldersFromTemplate( report );
        List< WebSite > webSites = Stream.of( placeHolders.get( 3 ) )
                .map( webSite -> WebSite.builder().name( webSite ).build() )
                .toList();

        Company company = Company
                .builder()
                .name( placeHolders.get( 0 ) )
                .foundationDate( LocalDate.parse( placeHolders.get( 1 ), format ) )
                .founder( placeHolders.get( 2 ) )
                .webSites( webSites )
                .build();

        this.reportPublisher.publishReport( report );

        this.companiesRepository.postByName( company );

        return "Saved";

    }

    @Override
    public void deleteReport( String name ) {
        this.companiesRepository.deleteByName( name );
    }

    private String makeReportMain( String name ) {
        return reportHelper.readTemplate( this.companiesRepository.getByName( name ).orElseThrow() );
    }

    private String makeReportFallback( String name, Throwable error ) {
        log.warn( error.getMessage() );
        return reportHelper.readTemplate( this.companiesFallbackRepository.getByName( name ) );
    }

}