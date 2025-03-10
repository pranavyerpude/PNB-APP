package com.airbnb1.Controller;

import com.airbnb1.Repository.PropertyRepository;
import com.airbnb1.Repository.ReviewRepository;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.entity.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReviews(@AuthenticationPrincipal PropertyUser propertyUser,
                                             @PathVariable("propertyId") long propertyId,
                                             @RequestBody Review review) {

        Optional<Property> opProp = propertyRepository.findById(propertyId);
        Property property = opProp.get();

        Review r = reviewRepository.findReviewByUser(propertyUser, property);
     if(r!=null){
         return new ResponseEntity<>("You have already added review for property",HttpStatus.BAD_REQUEST);
     }



        review.setProperty(property);
        review.setPropertyUser(propertyUser);

        reviewRepository.save(review);
        return new ResponseEntity<>("Review added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getPropertyUser(@AuthenticationPrincipal PropertyUser propertyUser){
        List<Review> reviews = reviewRepository.findByPropertyUser(propertyUser);
          return new ResponseEntity<>(reviews,  HttpStatus.OK);


    }
}

















