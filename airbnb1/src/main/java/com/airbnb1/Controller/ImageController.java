package com.airbnb1.Controller;

import com.airbnb1.Repository.ImageRepository;
import com.airbnb1.Repository.PropertyRepository;
import com.airbnb1.Service.BucketService;
import com.airbnb1.entity.Image;
import com.airbnb1.entity.Property;
import com.airbnb1.entity.PropertyUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private ImageRepository imageRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;

    public ImageController(ImageRepository imageRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imageRepository = imageRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,
                                        @PathVariable String bucketName,
                                        @PathVariable long propertyId,
                                        @AuthenticationPrincipal PropertyUser propertyUser) {

        String imageUrl = bucketService.uploadFile(file, bucketName);
        Property property = propertyRepository.findById(propertyId).get();

        Image img = new Image();
        img.setImageUrl(imageUrl);
        img.setProperty(property);
        img.setPropertyUser(propertyUser);

        Image savedImages = imageRepository.save(img);


        return new ResponseEntity<>(savedImages, HttpStatus.OK);
    }
}










