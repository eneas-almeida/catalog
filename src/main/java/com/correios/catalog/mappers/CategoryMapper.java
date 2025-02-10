package com.correios.catalog.mappers;

import com.correios.catalog.configs.ModelMapperConfig;
import com.correios.catalog.dtos.CategoryDto;
import com.correios.catalog.dtos.CategoryInputDto;
import com.correios.catalog.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapperConfig modelMapper;

    public Category toEntity(CategoryDto categoryDto) {
        return modelMapper.modelMapper().map(categoryDto, Category.class);
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.modelMapper().map(category, CategoryDto.class);
    }

    public List<CategoryDto> toListDto(List<Category> categories) {
        return categories.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Category inputDtoToEntity(CategoryInputDto categoryInputDto) {
        return modelMapper.modelMapper().map(categoryInputDto, Category.class);
    }
}
