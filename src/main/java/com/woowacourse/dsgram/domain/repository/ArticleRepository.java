package com.woowacourse.dsgram.domain.repository;

import com.woowacourse.dsgram.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByAuthorNickNameOrderByCreatedDateDesc(Pageable pageable, String nickName);

    List<Article> findAllByAuthorNickNameOrderByCreatedDateDesc(String nickName);

    List<Article> findAll(Sort sort);
}
