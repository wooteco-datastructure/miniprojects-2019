package com.woowacourse.dsgram.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "follower")
    User from;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "followed")
    User to;

    @Builder
    public Follow(User from, User to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
