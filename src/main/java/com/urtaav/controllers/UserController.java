package com.urtaav.controllers;

import com.urtaav.entities.User;
import com.urtaav.repositories.UserRepository;
import com.urtaav.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null ) {

            // Buscar el usuario por nombre de usuario en el UserRepository
            Optional<User> optionalUser = userRepository.findByEmail((String) authentication.getPrincipal());

            if (optionalUser.isPresent()) {
                User currentUser = optionalUser.get();
                return ResponseEntity.ok(currentUser);
            } else {
                // Manejar el caso en el que no se encuentra al usuario en el UserRepository
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            // Manejar el caso en el que no se puede obtener la información del usuario
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
