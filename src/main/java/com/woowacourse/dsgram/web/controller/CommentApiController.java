package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.service.ArticleApiService;
import com.woowacourse.dsgram.service.CommentApiService;
import com.woowacourse.dsgram.service.UserService;
import com.woowacourse.dsgram.service.dto.user.CommentRequest;
import com.woowacourse.dsgram.service.dto.user.LoginUserRequest;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private ArticleApiService articleApiService;
    private UserService userService;
    private CommentApiService commentApiService;

    public CommentApiController(CommentApiService commentApiService,
                                ArticleApiService articleApiService,
                                UserService userService) {
        this.articleApiService = articleApiService;
        this.userService = userService;
        this.commentApiService = commentApiService;
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody CommentRequest commentRequest,
                                          @UserSession LoginUserRequest loginUserRequest) {
        Comment comment = converter(commentRequest, loginUserRequest);
        Comment savedComment = commentApiService.create(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    private Comment converter(CommentRequest commentRequest, LoginUserRequest loginRequest) {
        Article article = articleApiService.findById(commentRequest.getArticleId());
        User user = userService.findUserById(loginRequest.getId());
        return new Comment(article, user, commentRequest.getContents());
    }
}
