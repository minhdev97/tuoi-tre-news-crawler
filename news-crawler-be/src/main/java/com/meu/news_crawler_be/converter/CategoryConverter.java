package com.meu.news_crawler_be.converter;

import com.meu.news_crawler_be.entity.Category;
import com.meu.news_crawler_be.payload.res.CategoryResponseDTO;

public interface CategoryConverter {
    public CategoryResponseDTO entityToResponse(Category category);
}
