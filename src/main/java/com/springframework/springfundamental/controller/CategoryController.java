package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.dto.CategoryRequest;
import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.service.CategoryService;
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
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "")
    public List<Category> getCategory(){return categoryService.getAllCategory();}

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(@PathVariable("id") String id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@Valid @RequestBody CategoryRequest request){
        return categoryService.saveCategory(request);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@Valid @PathVariable("id") String id, @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, request);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") String id){
        categoryService.deleteCategory(id);
    }
}
