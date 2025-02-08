package com.correios.catalog.controllers;

import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> handleFindAll() {
        try {
            List<CategoryDto> categories = categoryService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> handleCreate(@RequestBody CategoryInputDto categoryInputDto) {
        try {
            CategoryDto categoryDto = categoryService.create(categoryInputDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
