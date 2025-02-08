package com.correios.catalog.controllers;

import com.correios.catalog.entities.Category;
import com.correios.catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
