package com.correios.catalog.services;

import com.correios.catalog.entities.Category;
import com.correios.catalog.exceptions.DefaultException;
import com.correios.catalog.repositories.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getCategories() throws DefaultException {
        try {
            logger.info("Getting all categories");

            List<Category> cat = Arrays.asList(new Category(), new Category());

            return cat;
        } catch (Exception e) {
            logger.error("Error getting all categories", e);

            throw new DefaultException("Error getting all categories");
        }
    }
}
