package com.example.library.service;

import com.example.library.dto.user.UserCreateDto;
import com.example.library.model.User;

public interface UserService {
    User register(UserCreateDto userCreateDto);
}
