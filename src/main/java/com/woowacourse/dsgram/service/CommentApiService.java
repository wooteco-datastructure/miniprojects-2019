package com.woowacourse.dsgram.service;

import com.sun.deploy.util.StringUtils;
import com.woowacourse.dsgram.domain.Comment;
import com.woowacourse.dsgram.domain.CommentRepository;
import com.woowacourse.dsgram.service.exception.EmptyCommentRequestException;
import org.springframework.stereotype.Service;

@Service
public class CommentApiService {

    private CommentRepository commentRepository;

    public CommentApiService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment) {
        if (comment.getContents()) {
            throw new EmptyCommentRequestException("댓글의 내용을 입력해주세요.");
        }
        return commentRepository.save(comment);
    }
}
