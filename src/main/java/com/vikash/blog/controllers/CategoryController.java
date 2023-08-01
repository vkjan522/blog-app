package com.vikash.blog.controllers;

import com.vikash.blog.payloads.ApiResponse;
import com.vikash.blog.payloads.CategoryDTO;
import com.vikash.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //create - POST
    @PostMapping("/create/")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categorydto = this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(categorydto, HttpStatus.CREATED);
    }

    //update - PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable("id") Integer id) {
        CategoryDTO updated = this.categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok(updated);
    }
    //delete - DELETE

    @DeleteMapping("/delete/{cat_ID}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("cat_ID") Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity(new ApiResponse("category Deleted Successfully",true),HttpStatus.OK);
    }
    //getONE - GET

    @GetMapping("/getOne/{cid}")
    public ResponseEntity<CategoryDTO> getOne(@PathVariable("cid") Integer id){
        CategoryDTO categoryByID = this.categoryService.getCategoryByID(id);
        return ResponseEntity.ok(categoryByID);
    }
    //getALL - GET

    @GetMapping("all/")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        List<CategoryDTO> allCategory = this.categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.OK);
    }
}
