package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.User;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.UserRepository;
import com.priyanka.BenchMark.dto.request.UserRequest;
import com.priyanka.BenchMark.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Implements UserDetailsService so Spring Security can load users during authentication
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Creates a new user and saves it to the database
    // Password is hashed with BCrypt before saving
    // Throws DuplicateException if username already exists
    public UserResponse createUser(UserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new DuplicateException("Username already exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        // Hash password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser.getId(), savedUser.getName(),savedUser.getUsername());

    }

    // Called by Spring Security during login to load user details by username
    // Returns a UserDetails object containing username, hashed password
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

}
