package com.msvc.companiescrud.repository;

import com.msvc.companiescrud.models.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSiteRepository extends JpaRepository< WebSite, Long > {
}