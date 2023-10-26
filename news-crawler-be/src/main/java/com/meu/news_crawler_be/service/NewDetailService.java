package com.meu.news_crawler_be.service;

import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewDetailService {
    Page<HomePageNewsResponseDTO> findHomePageNews(Pageable pageable);
    void crawlNewsDataFromTuoiTreNews();
    NewsDetailResponseDTO findNewsDetailById(String id);
    Page<HomePageNewsResponseDTO> findNewsByCategory_Id(String id, Pageable pageable);

}
