package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.repository.HashTagRepository;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.dto.ArticleInfo;
import com.woowacourse.dsgram.service.dto.HashTagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public void saveHashTags(Article article) {
        hashTagRepository.saveAll(extractHashTags(article));
    }

    private List<HashTag> extractHashTags(Article article) {
        return article.getKeyword().stream()
                .map(keyword -> new HashTag(keyword, article))
                .collect(toList());
    }

    public HashTagResponse findAllWithCountByQuery(String query) {
        return new HashTagResponse(hashTagRepository.findResult(query));
    }

    private void deleteAllByArticleId(long articleId) {
        hashTagRepository.deleteAllByArticleId(articleId);
    }

    public void update(Article article) {
        deleteAllByArticleId(article.getId());
        saveHashTags(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleInfo> findAllByKeyword(String keyword, int page) {
        Page<HashTag> hashTags = hashTagRepository.findAllByKeywordContaining(
                PageRequest.of(page, 10), keyword);

        return hashTags.stream()
                .map(HashTag::getArticle).sorted()
                .map(ArticleAssembler::toArticleInfo)
                .collect(Collectors.toList());
    }
}
