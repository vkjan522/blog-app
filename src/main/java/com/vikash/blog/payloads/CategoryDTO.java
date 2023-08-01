package com.vikash.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private int categoryID;
    @NotEmpty
    @Size(min = 5, max = 20,message = "Category Details must be between 5 to 20 character!!")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 5, max = 20,message = "Category Details must be between 5 to 20 character!!")
    private String categoryDescription;
}
