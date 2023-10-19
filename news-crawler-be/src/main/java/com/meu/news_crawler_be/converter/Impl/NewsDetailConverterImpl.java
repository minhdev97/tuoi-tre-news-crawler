package com.meu.news_crawler_be.converter.Impl;

import com.meu.news_crawler_be.converter.NewsDetailConverter;
import com.meu.news_crawler_be.entity.NewsDetail;
import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsDetailConverterImpl implements NewsDetailConverter {

    @Override
    public HomePageNewsResponseDTO newsEntityToHomePageResponse(NewsDetail newsDetail) {
        return HomePageNewsResponseDTO.builder()
                .category(newsDetail.getCategory().getCategoryName())
                .img(newsDetail.getImg())
                .title(newsDetail.getTitle())
                .detailSapo(newsDetail.getDetailSapo())
                .publishDate(newsDetail.getPublishDate())
                .build();
    }

    @Override
    public NewsDetailResponseDTO newsEntityToNewsDetailResponese(NewsDetail newsDetail) {
        return NewsDetailResponseDTO.builder()
                .category(newsDetail.getCategory().getCategoryName())
                .img(newsDetail.getImg())
                .title(newsDetail.getTitle())
                .detailSapo(newsDetail.getDetailSapo())
                .publishDate(newsDetail.getPublishDate())
                .newsContent(newsDetail.getNewsContent())
                .build();
    }
}
