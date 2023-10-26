package com.meu.news_crawler_be.payload.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomePageNewsResponseDTO {
    private String id;
    private String publishDate;
    private String title;
    private String img;
    private String category;
    private String detailSapo;
}
