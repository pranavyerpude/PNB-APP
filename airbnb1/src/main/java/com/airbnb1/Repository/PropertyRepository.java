package com.airbnb1.Repository;

import com.airbnb1.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("select p from Property p JOIN Location l on p.location=l.id JOIN Country c on p.country=c.id where l.locationName=:locationName or c.countryName=:locationName")
    List<Property> findPropertyByLocation(@PathVariable ("locationName") String locationName);




}