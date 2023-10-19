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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/news")
@Tag(
        name = "Categories",
        description = "API endpoint to get news from database"
)
public class NewsDetailController {
    @Autowired
    private NewDetailService newDetailService;


   @GetMapping("/homepage")
   @Operation(
           summary = "get news for homepage",
           responses = {
                   @ApiResponse(
                           responseCode = "200",
                           description = "Get news successfully",
                           content = @Content(
                                   array = @ArraySchema(
                                           schema = @Schema(implementation = HomePageNewsResponseDTO.class)
                                   )
                           )
                   )
           }
   )
   public ResponseEntity<?> findNewsForHomePage() {
       List<HomePageNewsResponseDTO> homePageNews = newDetailService.findHomePageNews();
       return new ResponseEntity<>(homePageNews, HttpStatus.OK);
   }

   @GetMapping("/{id}")
   @Operation(
           summary = "Get news detail",
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
                           description = "Get news detail successfully",
                           content = @Content(
                                   array = @ArraySchema(
                                           schema = @Schema(implementation = NewsDetailResponseDTO.class)
                                   )
                           )
                   )
           }
   )
   public ResponseEntity<?> findNewsDetail(@PathVariable("id") String id) {
       NewsDetailResponseDTO newsDetailResponseDTO = newDetailService.findNewsDetailById(id);
       return new ResponseEntity<>(newsDetailResponseDTO, HttpStatus.OK);
   }
}
