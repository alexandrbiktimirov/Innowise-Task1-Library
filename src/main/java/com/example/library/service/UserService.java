package com.example.library.service;

import com.example.library.dto.user.RegisterRequest;
import com.example.library.model.User;

public interface UserService {
    User registerUser(RegisterRequest userCreateDto);
    User registerAdmin(RegisterRequest userCreateDto);
}
