package com.springframework.springfundamental.service;

import com.springframework.springfundamental.dto.CategoryRequest;
import com.springframework.springfundamental.entity.Category;
import com.springframework.springfundamental.exception.InvalidException;
import com.springframework.springfundamental.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    //mockCategory
    private Category mockCategory(UUID categoryId) {

        var category = new Category();
        category.setId(categoryId);
        category.setName("WaiHussein");
        category.setLastOpId(UUID.fromString("d117360b-1d86-4e94-8fb2-cac24756e6c2"));

        return category;
    }

    //mockCategoryRequest
    private CategoryRequest mockCategoryRequest() {
        return new CategoryRequest("WaiHussein","d117360b-1d86-4e94-8fb2-cac24756e6c2");
    }

    //GET BY ID
    @Test
    void testForGetCategoryById() {
        //given
        var categoryId = UUID.fromString("d35bdae8-36b6-4ad4-925a-a1d18752c0d8");
        var category = mockCategory(categoryId);

        //when
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        var actual = categoryService.getCategoryById(UUID.randomUUID().toString());

        //then
        verify(categoryRepository, times(1)).findById(any(UUID.class));

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(categoryId);
    }

    //CREATE
    @Test
    void testForCreateCategory() {
        //given
        var categoryRequest = mockCategoryRequest();

        //when
        categoryService.saveCategory(categoryRequest);

        //then
        verify(categoryRepository, times(1)).save(categoryArgumentCaptor.capture());

        var actual = categoryArgumentCaptor.getValue();
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("WaiHussein");
        assertThat(actual.getLastOpId()).isEqualTo(UUID.fromString("d117360b-1d86-4e94-8fb2-cac24756e6c2"));
    }

    @Test
    void testForCreateCategory_dbException(){
        //given
        var categoryRequest = mockCategoryRequest();

        //when
        when(categoryRepository.save(any(Category.class))).thenThrow(new RuntimeException("DB is down"));

        //then
        assertThatThrownBy(() -> categoryService.saveCategory(categoryRequest))
                .isInstanceOf(InvalidException.class)
                .hasMessageContaining("Failed to create category");
    }
    //UPDATE
    @Test
    void testForUpdateCategory(){
        //given
        var categoryId = UUID.fromString("1ed26487-5aea-41aa-8bf5-cb1f5d2639e5");
        var category = mockCategory(categoryId);
        var categoryRequest =  mockCategoryRequest();

        //when
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));

        var updatedCategory = categoryService.updateCategory(categoryId.toString(),categoryRequest);

        //then
        verify(categoryRepository, times(1)).save(category);

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("WaiHussein");
        assertThat(updatedCategory.getLastOpId()).isEqualTo(UUID.fromString("d117360b-1d86-4e94-8fb2-cac24756e6c2"));
    }

    //DELETE
    @Test
    void testDeleteCategory() {
        //given
        var categoryId = UUID.fromString("1ed26487-5aea-41aa-8bf5-cb1f5d2639e5");
        var category = mockCategory(categoryId);

        //when
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId.toString());

        //then
        verify(categoryRepository, times(1)).delete(category);
    }

}