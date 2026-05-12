package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.UserService;
import com.priyanka.BenchMark.dto.request.CategoryRequest;
import com.priyanka.BenchMark.dto.request.UserRequest;
import com.priyanka.BenchMark.dto.response.CategoryResponse;
import com.priyanka.BenchMark.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Handles user endpoints
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //Creates a new user, @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
