package com.meu.news_crawler_be.controller;

import com.meu.news_crawler_be.payload.res.HomePageNewsResponseDTO;
import com.meu.news_crawler_be.payload.res.NewsDetailResponseDTO;
import com.meu.news_crawler_be.service.NewDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/news")
@Tag(
        name = "News",
        description = "API endpoint for managing news in the database"
)
public class NewsDetailController {
    @Autowired
    private NewDetailService newDetailService;

    @GetMapping
    @Operation(
            summary = "Get News for Homepage",
            description = "Retrieve a list of news for the homepage",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = HomePageNewsResponseDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No news found for homepage",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> findNewsForHomePage(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HomePageNewsResponseDTO> homePageNews = newDetailService.findHomePageNews(pageable);
        if (homePageNews != null && !homePageNews.isEmpty()) {
            return new ResponseEntity<>(homePageNews, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("newsdetail/{id}")
    @Operation(
            summary = "Get News Detail by ID",
            description = "Retrieve the details of a news item by its ID",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the news",
                            in = ParameterIn.PATH,
                            required = true
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News detail retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = NewsDetailResponseDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "News detail not found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> findNewsDetail(@PathVariable("id") String id) {
        NewsDetailResponseDTO newsDetailResponseDTO = newDetailService.findNewsDetailById(id);
        if (newsDetailResponseDTO != null) {
            return new ResponseEntity<>(newsDetailResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get News by Category ID",
            description = "Retrieve a list of news items by their category ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "News retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = HomePageNewsResponseDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No news found for the category",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> findNewsByCategoryID(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable("id") String id
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishDate"));
        Page<HomePageNewsResponseDTO> homePageNews = newDetailService.findNewsByCategory_Id(id, pageable);
        if (homePageNews != null && !homePageNews.isEmpty()) {
            return new ResponseEntity<>(homePageNews, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
