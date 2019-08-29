package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.User;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class FeedInfo {
    private User user;
    private long followers;
    private long followings;
    private List<Article> articles;
    private FollowRelation followRelation;

    @Builder
    public FeedInfo(User user, long followers, long followings, List<Article> articles, FollowRelation followRelation) {
        this.user = user;
        this.followers = followers;
        this.followings = followings;
        this.articles = articles;
        this.followRelation = followRelation;
    }
}
