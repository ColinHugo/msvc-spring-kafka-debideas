package com.msvc.reportms.controllers;

import com.msvc.reportms.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping( "/report" )
public class ReportController {

    private final ReportService reportService;

    @GetMapping( "{name}" )
    public ResponseEntity< Map< String, String > > getReport( @PathVariable String name ) {

        Map< String, String > response = Map.of( "report", this.reportService.makeReport( name ) );

        return ResponseEntity.ok( response );

    }

    @PostMapping()
    public ResponseEntity< String > postReport( @RequestBody String report ) {
        return ResponseEntity.ok( this.reportService.saveReport( report ) );
    }

    @DeleteMapping( "{name}" )
    public ResponseEntity< Void > deleteReport( @PathVariable String name ) {
        this.reportService.deleteReport( name );
        return ResponseEntity.noContent().build();
    }

}