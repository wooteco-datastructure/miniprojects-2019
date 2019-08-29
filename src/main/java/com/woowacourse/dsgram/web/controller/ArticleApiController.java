package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.ArticleService;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.dto.UserInfo;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleApiController {

    private ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity create(ArticleRequest articleRequest, @UserSession LoggedInUser loggedInUser) {
        Long articleId = articleService.createAndFindId(articleRequest, loggedInUser);
        return ResponseEntity.ok(articleId);
    }

    @GetMapping("{articleId}/file")
    public ResponseEntity<byte[]> showArticleFile(@PathVariable long articleId) {
        return new ResponseEntity<>(articleService.findFileById(articleId), HttpStatus.OK);
    }

    @GetMapping("{articleId}")
    public ResponseEntity showArticleInfo(@PathVariable long articleId) {
        return ResponseEntity.ok(articleService.findArticleInfo(articleId));
    }

    @PutMapping("{articleId}")
    public ResponseEntity update(@PathVariable long articleId, @RequestBody ArticleEditRequest articleEditRequest, @UserSession LoggedInUser loggedInUser) {
        articleService.update(articleId, articleEditRequest, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity delete(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        articleService.delete(articleId, loggedInUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{articleId}/like")
    public ResponseEntity like(@PathVariable long articleId, @UserSession LoggedInUser loggedInUser) {
        articleService.like(articleId, loggedInUser.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{articleId}/liker")
    public ResponseEntity liker(@PathVariable long articleId) {
        List<UserInfo> likerList = articleService.findLikerListById(articleId);
        return ResponseEntity.ok(likerList);
    }
}
