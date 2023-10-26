package com.meu.news_crawler_be.controller;

import com.meu.news_crawler_be.payload.res.CategoryResponseDTO;
import com.meu.news_crawler_be.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//http://localhost:8080/docs/swagger-ui/index.html
//http://localhost:8080/docs/api-docs
@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
@Tag(
        name = "Categories",
        description = "API endpoint to get categories"
)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(
            summary = "show categories",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get categories successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = CategoryResponseDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No categories found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<?> findAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll();
        if (categories != null && !categories.isEmpty()) {
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
