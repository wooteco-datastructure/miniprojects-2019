package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.service.CommentService;
import com.woowacourse.dsgram.service.dto.CommentRequest;
import com.woowacourse.dsgram.service.dto.CommentResponse;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private CommentService commentService;
    private String name;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CommentRequest commentRequest, @UserSession LoggedInUser loggedInUser) {
        Comment savedComment = commentService.create(commentRequest, loggedInUser.getId());
        return new ResponseEntity<>(savedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public HttpStatus delete(@PathVariable("commentId") Long commentId, @UserSession LoggedInUser loggedInUser) {
        commentService.delete(commentId, loggedInUser.getId());
        return HttpStatus.OK;
    }

    @PutMapping("/{commentId}")
    public ResponseEntity update(@PathVariable("commentId") Long commentId, @UserSession LoggedInUser loggedInUser,
                                 @RequestBody CommentRequest commentRequest) {
        CommentResponse updatedComment = commentService.update(commentId, commentRequest, loggedInUser);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity get(@PathVariable("articleId") Long articleId) {
        List<Comment> comments = commentService.get(articleId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}