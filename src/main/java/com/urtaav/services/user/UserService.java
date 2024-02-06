package com.urtaav.services.user;
import com.urtaav.dtos.SignupRequest;
import com.urtaav.dtos.UserDTO;
import com.urtaav.entities.User;

public interface UserService {
    UserDTO createUser(SignupRequest signupRequest);
    boolean findByEmail(String email);
}
