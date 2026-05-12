package com.priyanka.BenchMark;

import com.priyanka.BenchMark.Entity.Category;
import com.priyanka.BenchMark.Exceptions.DuplicateException;
import com.priyanka.BenchMark.Exceptions.ResourceNotFoundException;
import com.priyanka.BenchMark.Repository.CategoryRepository;
import com.priyanka.BenchMark.Service.CategoryService;
import com.priyanka.BenchMark.dto.request.CategoryRequest;
import com.priyanka.BenchMark.dto.response.CategoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void CreateCategory_shouldReturnCategory() {

        CategoryRequest request = new CategoryRequest();
        request.setName("Upper Body");

        Category saved = new Category();
        saved.setName("Upper Body");

        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertEquals("Upper Body", response.getName());
        assertEquals(saved.getId(), response.getId());

    }

    @Test
    void createCategory_shouldThrowException_whenDuplicate() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Upper Body");

        when(categoryRepository.existsByName("Upper Body")).thenReturn(true);

        assertThrows(DuplicateException.class, () -> {
            categoryService.createCategory(request);
        });
    }

    @Test
    void getCategoryById_shouldReturnCategory() {

        Category saved = new Category();
        saved.setName("Upper Body");
        saved.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(saved));

        CategoryResponse response = categoryService.getCategoryById(1L);
        assertNotNull(response);
        assertEquals("Upper Body", response.getName());
        assertEquals(1L, response.getId());
    }

    @Test
    void getAllCategories_shouldReturnAllCategories() {
        Category saved = new Category();
        saved.setName("Upper Body");
        saved.setId(1L);

        Category savedTwo = new Category();
        savedTwo.setName("Lower Body");
        savedTwo.setId(2L);

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(saved, savedTwo));
        List<CategoryResponse> response = categoryService.getAllCategories();
        assertNotNull(response);
        assertEquals("Upper Body", response.get(0).getName());
        assertEquals("Lower Body", response.get(1).getName());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
    }

    @Test
    void getCategoryById_shouldThrowException() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(99L);
        });
    }

    @Test
    void getAllCategories_shouldReturnEmptyList_whenNoCategories() {

        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryResponse> response = categoryService.getAllCategories();

        assertNotNull(response);
        assertEquals(0, response.size());
    }
}
