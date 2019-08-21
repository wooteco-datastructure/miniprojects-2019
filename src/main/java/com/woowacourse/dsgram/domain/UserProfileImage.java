package com.woowacourse.dsgram.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserProfileImage {
    @Id
    private long id;

    @Column(nullable = false, length = 240)
    private String fileName;

    @Column(nullable = false)
    private String filePath;
    
    public UserProfileImage(long id, String fileName, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public void update(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
