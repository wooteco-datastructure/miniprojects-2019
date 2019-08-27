package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomeController {
    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String showMainPage(Model model) {
        Page<Article> articles2 = articleService.findAllByPage(0);

        List<Article> articles = articles2.getContent();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/tags/{query}")
    public String showSearchPage(@PathVariable String query, Model model) {
        model.addAttribute("query", query);
        return "search-result";
    }
}
