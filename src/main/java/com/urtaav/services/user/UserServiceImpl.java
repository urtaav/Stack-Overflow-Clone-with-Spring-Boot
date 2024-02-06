package com.urtaav.services.user;

import com.urtaav.dtos.SignupRequest;
import com.urtaav.dtos.UserDTO;
import com.urtaav.entities.User;
import com.urtaav.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user);
        UserDTO createUserDTO = new UserDTO();
        createUserDTO.setId(createdUser.getId());
        createUserDTO.setName(createdUser.getName());
        createUserDTO.setEmail(createdUser.getEmail());
        return createUserDTO;
    }

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
