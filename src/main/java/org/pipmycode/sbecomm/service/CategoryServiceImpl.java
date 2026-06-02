package org.pipmycode.sbecomm.service;

import org.modelmapper.ModelMapper;
import org.pipmycode.sbecomm.exceptions.APIException;
import org.pipmycode.sbecomm.exceptions.ResourceAlreadyExistsException;
import org.pipmycode.sbecomm.model.Category;
import org.pipmycode.sbecomm.payload.CategoryDTO;
import org.pipmycode.sbecomm.payload.CategoryResponse;
import org.pipmycode.sbecomm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.pipmycode.sbecomm.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {



    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new APIException("No categories have been created yet!");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoriesResponse = new CategoryResponse();
        categoriesResponse.setContent(categoryDTOS);
        return categoriesResponse;
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
                // basically means return an optional category cause id might not exist :)
                .orElseThrow(() -> new ResourceNotFoundException("Category with categoryId: " + categoryId + " not found!"));

        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return "Category with categoryId: " + categoryId + " updated successfully!";
    }
}
