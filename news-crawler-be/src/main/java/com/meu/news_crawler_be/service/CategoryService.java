package com.meu.news_crawler_be.service;

import com.meu.news_crawler_be.payload.res.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> findAll();
    CategoryResponseDTO findByName(String categoryName);
    void cloneCategoriesFromTuoiTreNews();
}
