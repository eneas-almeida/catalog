package com.correios.catalog.services;

import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.entities.Category;
import com.correios.catalog.exceptions.DefaultException;
import com.correios.catalog.mappers.CategoryMapper;
import com.correios.catalog.repositories.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() throws DefaultException {
        try {

            List<Category> categoryEntityList = categoryRepository.findAll();

            List<CategoryDto> categoryDtoList = categoryMapper.toListDtos(categoryEntityList);

            logger.info("Getting all categories");

            return categoryDtoList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }

    @Transactional
    public CategoryDto create(CategoryInputDto categoryInputDto) throws DefaultException {
        try {
            Category categoryEntity = categoryMapper.inputDtoToEntity(categoryInputDto);

            categoryRepository.save(categoryEntity);

            CategoryDto categoryDto = categoryMapper.toDto(categoryEntity);

            logger.info("Creating category");

            return categoryDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DefaultException(e.getMessage());
        }
    }
}
