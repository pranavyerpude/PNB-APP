package com.airbnb1.Controller;

import com.airbnb1.Repository.PropertyRepository;
import com.airbnb1.entity.Property;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    private PropertyRepository propertyRepository;
    public PropertyController(PropertyRepository propertyRepository) {

        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/{locationName}")
    public ResponseEntity<List<Property>>findPropertyByLocation(@PathVariable ("locationName") String locationName){
        List<Property> properties = propertyRepository.findPropertyByLocation(locationName);
          return new ResponseEntity<>(properties, HttpStatus.OK);

    }
}
