package com.vikash.blog.services;

import com.vikash.blog.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    //Create
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    //Update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryID);
    //Delete
    void deleteCategory(Integer categoryID);

    //GetOne
    CategoryDTO getCategoryByID(Integer categoryID);

    //GetAll
    List<CategoryDTO> getAllCategory();
}
