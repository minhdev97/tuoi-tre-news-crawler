package com.meu.news_crawler_be.service.Impl;

import com.meu.news_crawler_be.converter.NewsDetailConverter;
import com.meu.news_crawler_be.entity.Category;
import com.meu.news_crawler_be.entity.NewsDetail;
import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;
import com.meu.news_crawler_be.repository.CategoryRepository;
import com.meu.news_crawler_be.repository.NewsDetailRepository;
import com.meu.news_crawler_be.service.NewDetailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsDetailServiceImpl implements NewDetailService {
    @Autowired
    private NewsDetailRepository newsDetailRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsDetailConverter newsDetailConverter;


    @Override
    public List<HomePageNewsResponseDTO> findHomePageNews() {
        List<NewsDetail> newsDetailList = newsDetailRepository.findLatestNewsDetails();
        return newsDetailList.stream().map(newsDetail -> newsDetailConverter.newsEntityToHomePageResponse(newsDetail)).collect(Collectors.toList());
    }



    @Override
    public void crawlNewsDataFromTuoiTreNews() {
        List<NewsDetailResponseDTO> newsDetailsList = new ArrayList<>();
        try {
            String url = "https://tuoitre.vn";
            Document doc = Jsoup.connect(url).timeout(30000).get();
            Elements links = doc.select("a.box-category-link-title");

            for (Element link : links) {
                String href = link.attr("href");
                String newsDetailUrl = url + href;
                if (!href.startsWith("https:/") && !href.startsWith("/video")) {
                    Document newsDetailDoc = Jsoup.connect(newsDetailUrl).get();
                    //Getting element contents
                    Element publishDateElements = newsDetailDoc.select("div[data-role=publishdate]").first();
                    String publishDate = publishDateElements.text().substring(0, 10);

                    Elements detailSapoElements = newsDetailDoc.select("h2.detail-sapo");
                    String detailSapo = detailSapoElements.html();

                    Elements titleElements = newsDetailDoc.select("h1.detail-title.article-title");
                    String title = titleElements.html();

                    Element ImagesElement = newsDetailDoc.select("div.detail-content.afcbc-body img").first();
                    String img = ImagesElement.attr("src");

                    Elements newsContentElement = newsDetailDoc.select("div.detail-content.afcbc-body");
                    String newsContent = newsContentElement.toString();

                    Elements categoryElements = newsDetailDoc.select("div.detail-cate a");
                    for (Element categoryElement : categoryElements) {
                        String categoryName = categoryElement.html();
                        Category category = categoryRepository.findCategoryByCategoryName(categoryName);

                        NewsDetail newsDetail = new NewsDetail();
                        newsDetail.setPublishDate(publishDate);
                        newsDetail.setDetailSapo(detailSapo);
                        newsDetail.setTitle(title);
                        newsDetail.setImg(img);
                        newsDetail.setNewsContent(newsContent);
                        newsDetail.setCategory(category);
                        newsDetailRepository.save(newsDetail);
                        newsDetailsList.add(newsDetailConverter.newsEntityToNewsDetailResponese(newsDetail));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public NewsDetailResponseDTO findNewsDetailById(String id) {
        return newsDetailConverter.newsEntityToNewsDetailResponese(newsDetailRepository.findNewsDetailById(id));
    }

    @Override
    public List<HomePageNewsResponseDTO> findNewsDetailsByCategory_Id(String id) {
        List<NewsDetail> newsDetailList = newsDetailRepository.findNewsDetailsByCategory_Id(id);
        return newsDetailList.stream().map(newsDetail -> newsDetailConverter.newsEntityToHomePageResponse(newsDetail)).collect(Collectors.toList());    }
}
