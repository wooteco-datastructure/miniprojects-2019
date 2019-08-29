package com.woowacourse.dsgram.service.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class CommentRequest {
    private String contents;
    private Long articleId;

    public CommentRequest(String contents, Long articleId) {
        this.contents = contents;
        this.articleId = articleId;
    }
}