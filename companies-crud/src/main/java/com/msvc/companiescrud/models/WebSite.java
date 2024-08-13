package com.msvc.companiescrud.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class WebSite {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    @Column( columnDefinition = "category" )
    @Enumerated( value = EnumType.STRING )
    private Category category;

    private String description;

}