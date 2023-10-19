package com.meu.news_crawler_be.repository;

import com.meu.news_crawler_be.entity.NewsDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDetailRepository extends JpaRepository<NewsDetail, String> {
    @Query("SELECT nd FROM NewsDetail nd ORDER BY nd.publishDate DESC")
    List<NewsDetail> findLatestNewsDetails();

    NewsDetail findNewsDetailById(String id);

    List<NewsDetail> findNewsDetailsByCategory_Id(String id);

}
