package com.meu.news_crawler_be.repository;

import com.meu.news_crawler_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findCategoryByCategoryName(String categoryName);
    Category findCategoryById(String id);
}
