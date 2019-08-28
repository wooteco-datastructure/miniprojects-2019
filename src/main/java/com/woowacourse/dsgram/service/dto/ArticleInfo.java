package com.woowacourse.dsgram.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleInfo {

    private Long userId;
    private String nickName;
    private String userProfileFileName;
    private Long articleId;
    private String contents;
    private String articleFileName;
    private Long countOfLikes;
    private Long countOfComments;

    @Builder
    public ArticleInfo(Long userId, String nickName, String userProfileFileName,
                       Long articleId, String contents, String articleFileName,
                       Long countOfLikes, Long countOfComments) {
        this.userId = userId;
        this.nickName = nickName;
        this.userProfileFileName = userProfileFileName;
        this.articleId = articleId;
        this.contents = contents;
        this.articleFileName = articleFileName;
        this.countOfLikes = countOfLikes;
        this.countOfComments = countOfComments;
    }
}