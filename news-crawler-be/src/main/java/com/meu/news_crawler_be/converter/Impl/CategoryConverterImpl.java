package com.meu.news_crawler_be.converter.Impl;

import com.meu.news_crawler_be.converter.CategoryConverter;
import com.meu.news_crawler_be.entity.Category;
import com.meu.news_crawler_be.payload.res.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverterImpl implements CategoryConverter {
    @Override
    public CategoryResponseDTO entityToResponse(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
