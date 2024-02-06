package com.urtaav.services.user;

import com.urtaav.repositories.AnswerRepository;
import com.urtaav.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {

    void storeFile(MultipartFile multipartFile, Long answerId) throws IOException;
}
