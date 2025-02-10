package com.correios.catalog.services;

import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.entities.Category;
import com.correios.catalog.apis.FakeStoreApi;
import com.correios.catalog.exceptions.problems.EntityAlreadyExistsException;
import com.correios.catalog.exceptions.problems.EntityNotFoundException;
import com.correios.catalog.mappers.CategoryMapper;
import com.correios.catalog.repositories.CategoryRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private FakeStoreApi fakeStoreApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        String strProdcuts = this.fakeStoreApi.getProducts().block();

        List<Category> products = parseCategory(strProdcuts);

        logger.info(products);

        List<Category> categoryEntityList = this.categoryRepository.findAll();

        List<CategoryDto> categoryDtoList = this.categoryMapper.toListDto(categoryEntityList);

        logger.info("Getting all categories");

        return categoryDtoList;
    }

    @Transactional
    public CategoryDto create(CategoryInputDto categoryInputDto) {
        Optional<Category> optionalEntity = this.categoryRepository.findOneByTitle(categoryInputDto.getTitle());

        if (optionalEntity.isPresent()) {
            throw new EntityAlreadyExistsException("Category already exists");
        }

        Category categoryEntity = this.categoryMapper.inputDtoToEntity(categoryInputDto);

        this.categoryRepository.save(categoryEntity);

        CategoryDto categoryDto = this.categoryMapper.toDto(categoryEntity);

        logger.info("Creating category");

        return categoryDto;
    }

    @Transactional(readOnly = true)
    public CategoryDto findOneById(Long id) {
        Optional<Category> optionalEntity = this.categoryRepository.findOneById(id);

        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Category not exists");
        }

        CategoryDto categoryDto = this.categoryMapper.toDto(optionalEntity.get());

        logger.info("Getting category by id");

        return categoryDto;
    }

    private List<Category> parseCategory(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Category>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao parsear a resposta JSON", e);
        }
    }
}
