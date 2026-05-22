package org.pipmycode.sbecomm.service;

import org.pipmycode.sbecomm.model.Category;
import org.pipmycode.sbecomm.payload.CategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
   CategoryResponse getAllCategories();


    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    String updateCategory(Category category, Long categoryId);
}
