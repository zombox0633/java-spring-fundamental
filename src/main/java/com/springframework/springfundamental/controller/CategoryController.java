package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.dto.CategoryRequest;
import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "APIs for managing category data.")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "")
    @Operation(summary = "Retrieve All Category Data",
            description = "Returns a list of all category available in the system.")
    public List<Category> getCategory(){return categoryService.getAllCategory();}

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Category Data by Id",
            description = "Returns category data for the specified Id.")
    public Category getCategoryById(@PathVariable("id") String id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add New Category Data", description = "Creates and saves new category data in the system.")
    public Category createCategory(@Valid @RequestBody CategoryRequest request){
        return categoryService.saveCategory(request);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update Category Data", description = "Updates category data for the specified ID.")
    public Category updateCategory(@Valid @PathVariable("id") String id, @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete Category Data",
            description = "Deletes category data from the system based on the specified ID.")
    public void deleteCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
    }
}
