package com.springframework.springfundamental.service;

import com.springframework.springfundamental.constants.ErrorMessage;
import com.springframework.springfundamental.dto.CategoryRequest;
import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.exception.InvalidException;
import com.springframework.springfundamental.exception.NotFoundException;
import com.springframework.springfundamental.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){return categoryRepository.findAll();}

    public Category getCategoryById(String id){
        return categoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Category")));
    }

    public Category saveCategory(CategoryRequest request){
        try {
            var category = new Category();
            category.setName(request.name());
            category.setLastOpId(UUID.fromString(request.lastOpId()));

            categoryRepository.save(category);
            return category;
        }catch (InvalidException e){
            log.error("Invalid request: {}", e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error("Failed to create category: {}", e.getMessage(), e);
            throw new InvalidException("Failed to create category");
        }
    }

    public Category updateCategory(String id, CategoryRequest request){
        var existingCategory = getCategoryById(id);
        existingCategory.setName(request.name());
        existingCategory.setLastOpId(UUID.fromString(request.lastOpId()));

        categoryRepository.save((existingCategory));
        return existingCategory;
    }

    public void deleteCategory(String id){
        var existingCategory = getCategoryById(id);
        categoryRepository.delete(existingCategory);
    }
}

