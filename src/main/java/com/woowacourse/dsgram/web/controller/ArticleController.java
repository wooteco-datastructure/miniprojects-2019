package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/writing")
    public String moveToWritePage() {
        return "article-edit";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, Model model) {
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        return "article";
    }
    // TODO: 2019-08-20 my feed뿐만 아니라, 다른 user feed도 사용할 수 있도록 이름 바꾸기
    // TODO: 2019-08-20 real instagram은 뒤에 email을 붙임.... 우린 users/1/edit, myfeed/1 이렇게 될거같은데 바꿔야될듯ㅎㅎ^^;;;;;;;;
    // TODO: 2019-08-25 real instagram 은 자기 닉네임을 뒤에 붙임
    @GetMapping("/user/{nickName}")
    public String showMyFeed(@PathVariable String nickName, Model model) {
        List<Article> articles = articleService.findArticlesByAuthor(nickName);
        model.addAttribute("articles", articles);
        return "my-feed";
    }
}
