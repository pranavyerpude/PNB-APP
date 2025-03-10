package com.airbnb1.Controller;

import com.airbnb1.Repository.FavouriteRepository;
import com.airbnb1.Repository.PropertyRepository;
import com.airbnb1.entity.Favourite;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

private FavouriteRepository favouriteRepository;
private PropertyRepository propertyRepository;
public FavouriteController(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
}
    @PostMapping("/{propertyId}")
    public ResponseEntity<Favourite> addFavourite(@RequestBody Favourite favourite,
                                                  @AuthenticationPrincipal PropertyUser propertyUser,
                                                  @PathVariable("propertyId") long propertyId){

        Optional<Property>  poId = propertyRepository.findById(propertyId);
        Property property = poId.get();

        favourite.setPropertyUser(propertyUser);
        favourite.setProperty(property);

        Favourite savedFavourite = favouriteRepository.save(favourite);
        return new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);

    }
}
