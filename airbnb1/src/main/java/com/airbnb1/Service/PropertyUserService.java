package com.airbnb1.Service;

import com.airbnb1.Repository.PropertyUserRepository;
import com.airbnb1.entity.PropertyUser;
import com.airbnb1.payload.LoginDto;
import com.airbnb1.payload.PropertyUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyUserService {

    @Autowired
    private PropertyUserRepository propertyUserRepository;
    @Autowired
    private JWTService jwtService;

    public PropertyUser addUser(PropertyUserDto propertyUserDto){

        PropertyUser user = new PropertyUser();

        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setEmail(propertyUserDto.getEmail());
        user.setUsername(propertyUserDto.getUsername());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10)));
        user.setUserRole(propertyUserDto.getUserRole());

        PropertyUser savedUser = propertyUserRepository.save(user);
         return savedUser;

    }

    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser = propertyUserRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()) {
            PropertyUser propertyUser = opUser.get();
            if (BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword())) {
                return jwtService.generateToken(propertyUser);
            }
        }

        return  null;

    }
}
