package com.urtaav.controllers;

import com.urtaav.services.user.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController
{

    @Autowired
    private ImageService imageService;
    @PostMapping("/image/{answerId}")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file, @PathVariable Long answerId){
            try {
                imageService.storeFile(file,answerId);
                return ResponseEntity.ok("Image stored successfully");
            } catch (IOException e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }
}
