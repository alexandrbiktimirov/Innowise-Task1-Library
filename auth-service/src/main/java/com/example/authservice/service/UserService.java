package com.example.authservice.service;

import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.model.Role;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(RegisterRequest userCreateDto) {
        if (userRepository.existsByUsername(userCreateDto.username())){
            throw new UserAlreadyExistsException("User with username: '" + userCreateDto.username() + "' already exists");
        }

        User user = new User();

        user.setUsername(userCreateDto.username());
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));
        user.setRoles(Set.of(Role.USER));

        return userRepository.save(user);
    }

    // Needed only for demonstration purposes
    @Transactional
    public User registerAdmin(RegisterRequest userCreateDto) {
        User user = new User();

        user.setUsername(userCreateDto.username());
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));
        user.setRoles(Set.of(Role.ADMIN));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        String[] roles = user.getRoles().stream().map(Role::name).toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
