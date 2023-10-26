package com.meu.news_crawler_be.repository;

import com.meu.news_crawler_be.entity.NewsDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsDetailRepository extends JpaRepository<NewsDetail, String>, PagingAndSortingRepository<NewsDetail, String> {
    @Query("SELECT nd FROM NewsDetail nd ORDER BY nd.publishDate DESC")
    Page<NewsDetail> findLatestNewsDetails(Pageable pageable);

    NewsDetail findNewsDetailById(String id);

    NewsDetail findByTitle(String title);

    Page<NewsDetail> findNewsByCategory_Id(String id, Pageable pageable);
}
