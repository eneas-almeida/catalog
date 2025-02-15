package com.correios.catalog.controllers;

import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> handleFindAll() {
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> handleCreate(@RequestBody CategoryInputDto categoryInputDto) {
        CategoryDto categoryDto = categoryService.create(categoryInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryDto.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> handleFindOneById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findOneById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
    }
}
