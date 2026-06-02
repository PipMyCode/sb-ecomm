package org.pipmycode.sbecomm.service;

import org.pipmycode.sbecomm.model.Category;
import org.pipmycode.sbecomm.payload.CategoryDTO;
import org.pipmycode.sbecomm.payload.CategoryResponse;



public interface CategoryService {
   CategoryResponse getAllCategories();


    CategoryDTO createCategory(CategoryDTO categoryDTO);

    String deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
