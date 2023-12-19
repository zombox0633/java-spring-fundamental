package com.springframework.springfundamental.controller;

import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(value = "")
    public List<Category> getCategory(){return categoryService.getAllCategory();}
}
