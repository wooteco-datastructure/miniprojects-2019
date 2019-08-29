package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.LikeRelation;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.LikeRelationRepository;
import com.woowacourse.dsgram.service.assembler.UserAssembler;
import com.woowacourse.dsgram.service.dto.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRelationRepository likeRepository;

    public LikeService(LikeRelationRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Transactional
    public void like(Article article, User user) {
        LikeRelation likeRelation = new LikeRelation(article, user);
        if (!likeRepository.existsByArticleAndUser(article, user)) {
            likeRepository.save(likeRelation);
            return;
        }
        likeRepository.delete(likeRelation);
    }

    public List<UserInfo> findLikerList(Article article) {
        return likeRepository.findAllByArticle(article)
                .stream().map(LikeRelation::getUser).map(UserAssembler::toUserInfo)
                .collect(Collectors.toList());
    }
}