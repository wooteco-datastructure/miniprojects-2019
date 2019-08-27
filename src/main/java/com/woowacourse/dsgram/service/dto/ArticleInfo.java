package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.FileInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ArticleInfo {
    private long id;
    private String contents;
    private String nickName;
    private FileInfo fileInfo;
    private long countOfLikes;

    @Builder
    public ArticleInfo(long id, String contents, String nickName, FileInfo fileInfo, long countOfLikes) {
        this.id = id;
        this.contents = contents;
        this.nickName = nickName;
        this.fileInfo = fileInfo;
        this.countOfLikes = countOfLikes;
    }
}
