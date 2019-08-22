package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private ArticleApiService articleApiService;

    public HomeController(ArticleApiService articleApiService) {
        this.articleApiService = articleApiService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        // TODO: 2019-08-22 무한 스크롤 -> ApiController 이동 및 받아오기, Pagination
        List<Article> articles = articleApiService.findAll();
        model.addAttribute("articles", articles);
        return "index";
    }
}
