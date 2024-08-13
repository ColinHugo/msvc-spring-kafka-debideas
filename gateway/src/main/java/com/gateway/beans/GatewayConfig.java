package com.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
public class GatewayConfig {

    @Bean
    @Profile( "eureka-off" )
    public RouteLocator routerLocatorEurekaOff( RouteLocatorBuilder builder ) {

        return builder
                .routes()
                .route( route -> route
                        .path( "/companies-crud/company/*" )
                        .uri( "http://localhost:8000" )
                )
                .route( route -> route
                        .path( "/report-ms/report/*" )
                        .uri( "http://localhost:8010" )
                )
                .route( route -> route
                        .path( "/report-ms/report/*" )
                        .uri( "http://localhost:8010" )
                )
                .build();
    }

    @Bean
    @Profile( "eureka-on" )
    public RouteLocator routerLocatorEurekaOn( RouteLocatorBuilder builder ) {

        return builder
                .routes()
                .route( route -> route
                        .path( "/companies-crud/company/**" )
                        .uri( "lb://companies-crud" )
                )
                .route( route -> route
                        .path( "/report-ms/report/**" )
                        .uri( "lb://report-ms" )
                )
                .build();

    }

    @Bean
    @Profile( "eureka-on-cb" )
    public RouteLocator routeLocatorEurekaOnCB( RouteLocatorBuilder builder ) {

        return builder
                .routes()
                .route( route -> route
                        .path( "/companies-crud/company/**" )
                        .filters( filter -> {
                            filter.circuitBreaker( config -> config
                                    .setName( "gateway-cb" )
                                    .setStatusCodes( Set.of( "500", "400" ) )
                                    .setFallbackUri( "forward:/companies-crud-fallback/company/*" ) );
                            return filter;
                        } )
                        .uri( "lb://companies-crud" )
                )
                .route( route -> route
                        .path( "/report-ms/report/**" )
                        .uri( "lb://report-ms" )
                )
                .route( route -> route
                        .path( "/companies-crud-fallback/company/**" )
                        .uri( "lb://companies-crud-fallback" )
                )
                .build();

    }

}