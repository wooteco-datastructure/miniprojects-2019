package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.vo.HashTagSearchResult;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class HashTagResponse {
    private List<HashTagSearchResult> hashTags;

    public HashTagResponse(List<HashTagSearchResult> hashTags) {
        this.hashTags = hashTags;
    }
}
