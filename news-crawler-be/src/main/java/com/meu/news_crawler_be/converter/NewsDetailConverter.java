package com.meu.news_crawler_be.converter;


import com.meu.news_crawler_be.entity.NewsDetail;
import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;

public interface NewsDetailConverter {
    public HomePageNewsResponseDTO newsEntityToHomePageResponse(NewsDetail newsDetail);
    public NewsDetailResponseDTO newsEntityToNewsDetailResponese(NewsDetail newsDetail);
}
