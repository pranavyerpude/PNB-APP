package com.airbnb1.Controller;

import com.airbnb1.Repository.BookingRepository;
import com.airbnb1.Repository.PropertyRepository;

import com.airbnb1.Service.TwilioSmsService;
import com.airbnb1.entity.Booking;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private  PropertyRepository propertyRepository;

    @Autowired
    private TwilioSmsService twilioSmsService;


    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking,
                                                 @AuthenticationPrincipal PropertyUser propertyUser,
                                                 @PathVariable("propertyId") long propertyId) {

        booking.setPropertyUser(propertyUser);

        Property property = propertyRepository.findById(propertyId).get();
        int nightlyPrice = property.getNightlyPrice();
        int totalNights = booking.getTotalNights();
        int totalPrice = nightlyPrice * totalNights;

        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);

        Booking createdBooking = bookingRepository.save(booking);

        twilioSmsService.sendSMS("+917875064429", "Your Ticket Booked");
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);

    }

}




