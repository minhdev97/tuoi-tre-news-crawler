package com.meu.news_crawler_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsCrawlerBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsCrawlerBeApplication.class, args);
	}

}
