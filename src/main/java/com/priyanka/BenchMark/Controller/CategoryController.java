package com.priyanka.BenchMark.Controller;

import com.priyanka.BenchMark.Service.CategoryService;
import com.priyanka.BenchMark.dto.request.CategoryRequest;
import com.priyanka.BenchMark.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Handles category endpoints
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    //Creates a new category, @Valid triggers validation on the request body
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //Returns all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return  new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    //Returns one category by its id and throws ResourceNotFoundException if id is not found
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse response = categoryService.getCategoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
