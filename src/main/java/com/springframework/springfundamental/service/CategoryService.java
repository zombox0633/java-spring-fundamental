package com.springframework.springfundamental.service;

import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){return categoryRepository.findAll();}
}
