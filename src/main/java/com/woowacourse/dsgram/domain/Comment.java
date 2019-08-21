package com.woowacourse.dsgram.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(name = "fk_comment_to_article"))
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_comment_to_user"))
    private User user;

    @Column(nullable = false)
    @Lob
    private String contents;

    @Builder
    public Comment(Article article, User user, String contents) {
        this.article = article;
        this.user = user;
        this.contents = contents;
    }
}
