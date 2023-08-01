package com.vikash.blog.services.impl;

import com.vikash.blog.entities.Category;
import com.vikash.blog.exceptions.ResourceNotFoundException;
import com.vikash.blog.payloads.CategoryDTO;
import com.vikash.blog.repositories.CategoryRepository;
import com.vikash.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    private Category dtoToCategory(CategoryDTO categoryDTO){
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        return category;
    }
    public CategoryDTO categoryToDto(Category category){
        CategoryDTO categoryDTO = this.modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }


    //Create
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.dtoToCategory(categoryDTO);
        Category saved = this.categoryRepository.save(category);
        return this.categoryToDto(saved);
    }

    //Update
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryID) {
        Category category = this.categoryRepository.findById(categoryID).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryID", categoryID));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category saved = this.categoryRepository.save(category);
        CategoryDTO categoryDTO1 = this.categoryToDto(saved);
        return categoryDTO1;
    }

    //Delete
    @Override
    public void deleteCategory(Integer categoryID) {
        Category category = this.categoryRepository.findById(categoryID).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryID", categoryID));

        this.categoryRepository.delete(category);

    }

    //Get One Category
    @Override
    public CategoryDTO getCategoryByID(Integer categoryID) {
        Category category = this.categoryRepository.findById(categoryID).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryID", categoryID));
        CategoryDTO categoryDTO = this.categoryToDto(category);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> all = this.categoryRepository.findAll();
        List<CategoryDTO> collected = all.stream().map(category ->
                this.categoryToDto(category)).collect(Collectors.toList());
        return collected;
    }
}
