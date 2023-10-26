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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NewsDetailServiceImpl implements NewDetailService {
    @Autowired
    private NewsDetailRepository newsDetailRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsDetailConverter newsDetailConverter;

//    Constant variable
    private final static String TUOI_TRE_URL_HOME_PAGE = "https://tuoitre.vn";
    private final static String TUOI_TRE_URL_LATEST_NEWS = "https://tuoitre.vn/tin-moi-nhat.htm";
    private final static int THIRTY_MINUTES = 1800000;


    @Override
    public Page<HomePageNewsResponseDTO> findHomePageNews(Pageable pageable) {
        Page<NewsDetail> newsDetailPage = newsDetailRepository.findLatestNewsDetails(pageable);

        return newsDetailPage.map(newsDetail -> newsDetailConverter.newsEntityToHomePageResponse(newsDetail));
    }


    @Override
    public NewsDetailResponseDTO findNewsDetailById(String id) {
        return newsDetailConverter.newsEntityToNewsDetailResponese(newsDetailRepository.findNewsDetailById(id));
    }

    @Override
    public Page<HomePageNewsResponseDTO> findNewsByCategory_Id(String id, Pageable pageable) {
        Page<NewsDetail> newsDetailPage = newsDetailRepository.findNewsByCategory_Id(id, pageable);
        return newsDetailPage.map(newsDetail -> newsDetailConverter.newsEntityToHomePageResponse(newsDetail));
    }


    @Override
    @Scheduled(fixedRate = THIRTY_MINUTES)
    public void crawlNewsDataFromTuoiTreNews() {
        try {
            Document doc = Jsoup.connect(TUOI_TRE_URL_LATEST_NEWS).get();
            Elements elements = doc.getElementsByClass("box-category-item");
            for (Element element : elements) {
//                Get title
                Elements titleElement = element.getElementsByClass("box-category-link-title");
                String title = titleElement.attr("title");

//                Get image src
                Elements imgElement = element.getElementsByClass("box-category-avatar");
                String img = imgElement.attr("src");

//                Get the url for news detail
                String sub_URL = titleElement.attr("href");
                String news_detail_url = TUOI_TRE_URL_HOME_PAGE + sub_URL;
                Document newsDetailDoc = Jsoup.connect(news_detail_url).get();

//                Get the publishing date from news detail
                Elements publishDateElements = element.getElementsByClass("time-ago-last-news");
                Date publishDate = new SimpleDateFormat("yyyy-MM-dd").parse(publishDateElements.attr("title").substring(0,10));
                String publishDateString = new SimpleDateFormat("dd-MM-yyyy").format(publishDate);

//                Get the sapo from news detail
                Elements detailSapoElements = newsDetailDoc.select("h2.detail-sapo");
                String detailSapo = detailSapoElements.html();

//                Get category from news detail
                Elements categoryElements = newsDetailDoc.select("div.detail-cate a");
                String categoryName = categoryElements.html();
                Category category = categoryRepository.findCategoryByCategoryName(categoryName);

//                Get news content
                Elements newsContentElement = newsDetailDoc.select("div.detail-content.afcbc-body");
                String newsContent = newsContentElement.toString();

//                Check Duplicate news
                Optional<NewsDetail> existingNewsDetail = Optional.ofNullable(newsDetailRepository.findByTitle(title));
                if (category != null && !existingNewsDetail.isPresent()) {
                    NewsDetail newsDetail = NewsDetail.builder()
                            .id(UUID.randomUUID().toString())
                            .title(title)
                            .publishDate(publishDateString)
                            .detailSapo(detailSapo)
                            .img(img)
                            .newsContent(newsContent)
                            .category(category)
                            .build();

                    newsDetailRepository.save(newsDetail);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
