package com.meu.news_crawler_be.service.Impl;

import com.meu.news_crawler_be.converter.CategoryConverter;
import com.meu.news_crawler_be.entity.Category;
import com.meu.news_crawler_be.payload.res.CategoryResponseDTO;
import com.meu.news_crawler_be.repository.CategoryRepository;
import com.meu.news_crawler_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> categoryConverter.entityToResponse(category))
                .collect(Collectors.toList());    }

    @Override
    public CategoryResponseDTO findByName(String categoryName) {
        return categoryConverter.entityToResponse(categoryRepository.findCategoryByCategoryName(categoryName));
    }

    @Override
    public void cloneCategoriesFromTuoiTreNews() {
        List<String> categoryNameList = Arrays.asList(
                "Trang chủ", "Thời sự", "Thế giới", "Pháp luật", "Kinh doanh",
                "Công nghệ", "Xe", "Du lịch", "Nhịp sống trẻ", "Văn hoá",
                "Giải trí", "Thể thao", "Giáo dục", "Nhà đất", "Sức khoẻ",
                "Giả thật", "Bạn đọc"
        );

        for (String categoryName : categoryNameList) {
            Category category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setCategoryName(categoryName);
            categoryRepository.save(category);
        }
    }

}
