package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.FileInfo;
import com.woowacourse.dsgram.domain.repository.ArticleRepository;
import com.woowacourse.dsgram.domain.repository.CommentRepository;
import com.woowacourse.dsgram.service.assembler.ArticleAssembler;
import com.woowacourse.dsgram.service.dto.ArticleEditRequest;
import com.woowacourse.dsgram.service.dto.ArticleInfo;
import com.woowacourse.dsgram.service.dto.ArticleRequest;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.service.strategy.ArticleFileNamingStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final HashTagService hashTagService;
    private final FileService fileService;
    private final UserService userService; // TODO: 빼고싶음

    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository, HashTagService hashTagService, FileService fileService, UserService userService) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.hashTagService = hashTagService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @Transactional
    public long createAndFindId(ArticleRequest articleRequest, LoggedInUser loggedInUser) {
        return create(articleRequest, loggedInUser).getId();
    }

    private Article create(ArticleRequest articleRequest, LoggedInUser loggedInUser) {
        FileInfo fileInfo = fileService.save(articleRequest.getFile(), new ArticleFileNamingStrategy());

        Article article = Article.builder()
                .contents(articleRequest.getContents())
                .fileInfo(fileInfo)
                .author(userService.findUserById(loggedInUser.getId()))
                .build();

        hashTagService.saveHashTags(article);

        return articleRepository.save(article);
    }

    @Transactional(readOnly = true)
    public Article findById(long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException(articleId + "번 게시글을 조회하지 못했습니다."));
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Transactional
    public void update(long articleId, ArticleEditRequest articleEditRequest, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        Article updatedArticle = article.update(articleEditRequest.getContents(), loggedInUser.getId());

        hashTagService.update(updatedArticle);
    }

    @Transactional
    public void delete(long articleId, LoggedInUser loggedInUser) {
        Article article = findById(articleId);
        article.checkAccessibleAuthor(loggedInUser.getId());
        articleRepository.delete(article);
    }

    public byte[] findFileById(long articleId) {
        return fileService.readFileByFileInfo(findById(articleId).getFileInfo());
    }

    public List<Article> findArticlesByAuthorNickName(String nickName) {
        userService.findByNickName(nickName);
        return articleRepository.findAllByAuthorNickName(nickName);
    }

    public ArticleInfo findArticleInfo(long articleId) {
        long countOfComments = commentRepository.countByArticleId(articleId);
        return ArticleAssembler.toArticleInfo(findById(articleId), countOfComments);
    }
}
