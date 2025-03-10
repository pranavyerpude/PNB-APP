package com.airbnb1.Controller;

import com.airbnb1.Service.PropertyUserService;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.payload.LoginDto;
import com.airbnb1.payload.PropertyUserDto;
import com.airbnb1.payload.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PropertyUserController {

    private PropertyUserService propertyUserService;

    public PropertyUserController(PropertyUserService propertyUserService) {
        this.propertyUserService = propertyUserService;
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto propertyUserDto) {
        PropertyUser propertyUser = propertyUserService.addUser(propertyUserDto);
        if (propertyUser != null) {
            return new ResponseEntity<>("Registration is successful", HttpStatus.CREATED);
        }
  return new ResponseEntity<>("Something went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

       @PostMapping("/login")
      public ResponseEntity<?>verifyLogin(@RequestBody LoginDto loginDto){

      String token = propertyUserService.verifyLogin(loginDto);

       if(token!=null){

        TokenResponse tokenResponse=new TokenResponse();
        tokenResponse.setToken(token);
           return new ResponseEntity<>(tokenResponse , HttpStatus.OK);

       }
       return new ResponseEntity<>("Invalid Credentials",HttpStatus.UNAUTHORIZED);
 }

 @GetMapping("/profile")
   public  PropertyUser getCurrentPropertyUserProfile(@AuthenticationPrincipal PropertyUser propertyUser){

        return propertyUser;
    }

}




