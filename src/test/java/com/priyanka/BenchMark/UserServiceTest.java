package com.priyanka.BenchMark;

import com.priyanka.BenchMark.Entity.User;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.UserRepository;
import com.priyanka.BenchMark.Service.UserService;
import com.priyanka.BenchMark.dto.request.CategoryRequest;
import com.priyanka.BenchMark.dto.request.UserRequest;
import com.priyanka.BenchMark.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldReturnUser(){
        UserRequest request = new UserRequest();
        request.setUsername("person");
        request.setName("Person");
        request.setPassword("password");

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setId(1L);

        when(userRepository.existsByUsername("person")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse userResponse = userService.createUser(request);

        assertNotNull(userResponse);
        assertEquals("Person", userResponse.getName());
        assertEquals("person", userResponse.getUsername());
        assertEquals(1L, userResponse.getId());


    }
    @Test
    void createUser_shouldThrowException_whenDuplicate(){
        UserRequest request = new UserRequest();
        request.setUsername("person");

        when(userRepository.existsByUsername("person")).thenReturn(true);
        assertThrows(DuplicateException.class, () -> userService.createUser(request));

    }
    @Test
    void loadUserByUsername_shouldReturnUserDetails(){
        User user = new User();
        user.setName("Person");
        user.setUsername("person");
        user.setPassword("password");
        user.setId(1L);


        when(userRepository.findByUsername("person")).thenReturn(Optional.of(user));

        UserDetails details = userService.loadUserByUsername("person");

        assertNotNull(details);
        assertEquals("person", details.getUsername());
        assertEquals("password", details.getPassword());

    }

    @Test
    void loadUserByUsername_shouldThrowException_whenNotFound(){
        when(userRepository.findByUsername("person")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("person"));

    }

}
