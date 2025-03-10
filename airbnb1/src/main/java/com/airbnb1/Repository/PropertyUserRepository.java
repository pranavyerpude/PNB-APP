package com.airbnb1.Repository;


import com.airbnb1.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PropertyUserRepository extends JpaRepository<PropertyUser,Long> {

    Optional<PropertyUser> findByUsername(String username);










}
