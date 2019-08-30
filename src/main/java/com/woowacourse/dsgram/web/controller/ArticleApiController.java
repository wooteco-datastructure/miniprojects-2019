package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.dto.article.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.article.ArticleInfo;
import com.woowacourse.dsgram.service.dto.article.ArticleRequest;
import com.woowacourse.dsgram.service.dto.follow.FollowInfo;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {
    private ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity create(ArticleRequest articleRequest, @UserSession LoggedInUser loggedInUser) {
        long articleId = articleService.createAndFindId(articleRequest, loggedInUser);
        return ResponseEntity.ok(articleId);
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        return ResponseEntity.ok(articleService.findFileById(articleId));
    }

    @GetMapping("{articleId}")
    public ResponseEntity showArticleInfo(@PathVariable long articleId) {
        return ResponseEntity.ok(articleService.findArticleInfo(articleId));
    }

    @PutMapping("{articleId}")
    public ResponseEntity update(@PathVariable long articleId, @RequestBody ArticleEditRequest articleEditRequest, @UserSession LoggedInUser loggedInUser) {
        articleService.update(articleId, articleEditRequest, loggedInUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity delete(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        articleService.delete(articleId, loggedInUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity showArticles(@UserSession LoggedInUser loggedInUser) {
        List<Article> articles = articleService.getArticlesByFollowings(loggedInUser.getNickName());
        List<ArticleInfo> articleInfos = articles.stream().map(article -> ArticleAssembler.toArticleInfo(article)).collect(Collectors.toList());
        return ResponseEntity.ok(articleInfos);
    }

    @GetMapping("/users/{userNickname}")
    public ResponseEntity showUserArticles(@PathVariable String userNickname, int page) {
        return ResponseEntity.ok(articleService.findArticlesByAuthorNickName(page, userNickname));
    }

    @PostMapping("/like/{articleId}")
    public ResponseEntity like(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        return ResponseEntity.ok(articleService.like(articleId, loggedInUser.getId()));
    }


    @GetMapping("/{articleId}/liker")
    public ResponseEntity liker(@PathVariable long articleId) {
        List<FollowInfo> likerList = articleService.findLikerListById(articleId);
        return ResponseEntity.ok(likerList);
    }


}
