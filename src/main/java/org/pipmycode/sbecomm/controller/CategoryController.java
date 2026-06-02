package org.pipmycode.sbecomm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pipmycode.sbecomm.model.Category;
import org.pipmycode.sbecomm.payload.CategoryDTO;
import org.pipmycode.sbecomm.payload.CategoryResponse;
import org.pipmycode.sbecomm.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping("/public/categories")
    // @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories() {
           CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
            String status = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
           @Valid @RequestBody CategoryDTO categoryDTO,
            @PathVariable Long categoryId
    ) {
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

}
