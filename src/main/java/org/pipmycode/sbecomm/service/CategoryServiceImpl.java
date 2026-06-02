package org.pipmycode.sbecomm.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

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
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());

        if (existingCategory != null) {
            throw new ResourceAlreadyExistsException("Category with name: " + categoryDTO.getCategoryName() + " already exists!");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with categoryId: " + categoryId + " not found!"));
        categoryRepository.delete(existingCategory);
        return "Category with categoryId: " + categoryId + " deleted successfully!";
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                // basically means return an optional category cause id might not exist :)
                .orElseThrow(() -> new ResourceNotFoundException("Category with categoryId: " + categoryId + " not found!"));

        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }
}
