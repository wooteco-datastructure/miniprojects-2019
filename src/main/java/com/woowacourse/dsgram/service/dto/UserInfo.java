package com.woowacourse.dsgram.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String nickName;
    private String userName;
    private String fileName;
    private String filePath;

    @Builder
    public UserInfo(String nickName, String userName, String fileName, String filePath) {
        this.nickName = nickName;
        this.userName = userName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
