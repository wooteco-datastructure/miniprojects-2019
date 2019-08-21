package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.dto.user.AuthUserRequest;
import com.woowacourse.dsgram.service.dto.user.CommentRequest;
import com.woowacourse.dsgram.service.dto.user.SignUpUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

public class CommentApiControllerTests extends AbstractControllerTest {
    private SignUpUserRequest signUpUserRequest;
    private String sessionCookie;
    private Long articleId;

    @BeforeEach
    void setUp() {
        // 회원가입
        signUpUserRequest = signUpUserRequest.builder()
                .userName("김버디")
                .email(AUTO_INCREMENT + "buddy@buddy.com")
                .nickName(AUTO_INCREMENT + "buddy")
                .password("buddybuddy1!")
                .build();

        defaultSignUp(signUpUserRequest, true)
                .expectStatus().isOk();

        // 로그인
        AuthUserRequest authUserRequest = new AuthUserRequest(signUpUserRequest.getEmail(), signUpUserRequest.getPassword());
        sessionCookie = getCookie(authUserRequest);

        // 글쓰기
        articleId = saveArticle();
    }

    @Test
    void notLoginUserCreateCommentFail() {
        CommentRequest request = new CommentRequest("contents", articleId);
        webTestClient.post().uri("/api/comments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void noContentsCreateCommentFail() {
        CommentRequest request = new CommentRequest(" ", articleId);
        webTestClient.post().uri("/api/comments")
                .header("Cookie", sessionCookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void createCommentSuccess() {
        CommentRequest request = new CommentRequest("contents", articleId);
        webTestClient.post().uri("/api/comments")
                .header("Cookie", sessionCookie)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(request), CommentRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
    }


    // todo : 글쓰기 기능 중복 제거해야 함
    private Long saveArticle() {
        Long[] articleId = new Long[1];

        requestWithBodyBuilder(createMultipartBodyBuilder(), HttpMethod.POST, "/api/articles")
                .expectBody()
                .jsonPath("$.id")
                .value(o -> articleId[0] = Long.parseLong(o.toString()));

        return articleId[0];
    }

    private MultipartBodyBuilder createMultipartBodyBuilder() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", new ByteArrayResource(new byte[]{1, 2, 3, 4}) {
            @Override
            public String getFilename() {
                return "catImage.jpeg";
            }
        }, MediaType.IMAGE_JPEG);
        bodyBuilder.part("contents", "contents");
        bodyBuilder.part("hashtag", "hashtag");
        return bodyBuilder;
    }

    private WebTestClient.ResponseSpec requestWithBodyBuilder(MultipartBodyBuilder bodyBuilder, HttpMethod requestMethod, String requestUri) {
        return webTestClient.method(requestMethod)
                .uri(requestUri)
                .header("Cookie", sessionCookie)
                .body(BodyInserters.fromObject(bodyBuilder.build()))
                .exchange();
    }

}
