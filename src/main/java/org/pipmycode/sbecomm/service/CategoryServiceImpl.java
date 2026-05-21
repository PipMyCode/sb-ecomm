package org.pipmycode.sbecomm.service;

import org.pipmycode.sbecomm.exceptions.ResourceAlreadyExistsException;
import org.pipmycode.sbecomm.model.Category;
import org.pipmycode.sbecomm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.pipmycode.sbecomm.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());

        if (existingCategory != null) {
            throw new ResourceAlreadyExistsException("Category with name: " + category.getCategoryName() + " already exists!");
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with categoryId: " + categoryId + " not found!"));
        categoryRepository.delete(existingCategory);
        return "Category with categoryId: " + categoryId + " deleted successfully!";
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with categoryId: " + categoryId + " not found!"));

        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return "Category with categoryId: " + categoryId + " updated successfully!";
    }
}
