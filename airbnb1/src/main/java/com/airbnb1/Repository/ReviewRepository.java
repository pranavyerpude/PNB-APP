package com.airbnb1.Repository;

import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("select r from Review r where r.propertyUser = :propertyUser and r.property = :property")
     Review findReviewByUser(@Param("propertyUser") PropertyUser propertyUser, @Param("property") Property property);

    List<Review> findByPropertyUser(PropertyUser propertyUser);

}