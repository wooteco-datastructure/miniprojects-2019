package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.LikeRelation;
import com.woowacourse.dsgram.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeRelation, Long> {
    long countByArticle(Article article);
    boolean existsByArticleAndUser(Article article, User user);
    List<LikeRelation> findAllByArticle(Article article);
}
