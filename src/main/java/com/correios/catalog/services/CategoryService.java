package com.correios.catalog.services;

import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.entities.Category;
import com.correios.catalog.apis.FakeStoreApi;
import com.correios.catalog.exceptions.problems.EntityAlreadyExistsException;
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

    private List<Category> parseCategory(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Category>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao parsear a resposta JSON", e);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        // Síncrona
        String strCategories = this.fakeStoreApi.getProducts().block();
        List<Category> categories = parseCategory(strCategories);
        logger.info(categories);

//             Assíncrona
//             List<Category> categories = new ArrayList<>();
//            this.fakeStoreApi.getProducts().subscribe(
//                response -> {
//                    logger.info(response);
//                    categories.addAll(parseCategory(response));
//                    logger.info(categories);
//                },
//                error -> {
//                    logger.error("Erro ao buscar categorias da API", error);
//                }
//            );

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
}
