package com.woowacourse.dsgram.service.assembler;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.service.dto.ArticleInfo;

public class ArticleAssembler {
    public static ArticleInfo toArticleInfo(Article article, long countOfLike) {
        return ArticleInfo.builder()
                .id(article.getId())
                .contents(article.getContents())
                .nickName(article.getAuthor().getNickName())
                .fileInfo(article.getFileInfo())
                .countOfLikes(countOfLike)
                .build();

    }
}
