package com.woowacourse.dsgram.service.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentRequest {

    @NotNull
    @NotBlank
    private String contents;

    @NotNull
    @NotBlank
    private Long articleId;

    @Builder
    public CommentRequest(@NotNull @NotBlank String contents, @NotNull @NotBlank Long articleId) {
        this.contents = contents;
        this.articleId = articleId;
    }
}
