package com.priyanka.BenchMark.Service;

import com.priyanka.BenchMark.Entity.Category;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.CategoryRepository;
import com.priyanka.BenchMark.dto.request.CategoryRequest;
import com.priyanka.BenchMark.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // Creates a new category and saves it to the database
    // Throws DuplicateException if a category with the same name already exists
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        //Check for duplicates
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new DuplicateException("Category already exists: " + categoryRequest.getName());
        }

        //DTO request to entity
        Category category = new Category();
        category.setName(categoryRequest.getName());


        Category savedCategory = categoryRepository.save(category);

        //saved entity to response
        return new CategoryResponse(savedCategory.getId(), savedCategory.getName());
    }

    //Returns a category by id
    //Throws ResourceNotFoundException if the id doesn't exist
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        return new CategoryResponse(category.getId(), category.getName());
    }

    //Returns a list of response DTO for all categories
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName()
                )).toList();
    }


}
