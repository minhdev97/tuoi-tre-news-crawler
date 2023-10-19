package com.meu.news_crawler_be.service;

import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;

import java.util.List;

public interface NewDetailService {
    List<HomePageNewsResponseDTO> findHomePageNews();
    void crawlNewsDataFromTuoiTreNews();
    NewsDetailResponseDTO findNewsDetailById(String id);
    List<HomePageNewsResponseDTO> findNewsDetailsByCategory_Id(String id);

}
