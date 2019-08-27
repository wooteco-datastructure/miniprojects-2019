package com.woowacourse.dsgram.service.dto;

import com.woowacourse.dsgram.domain.FileInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String nickName;
    private String userName;
    private FileInfo fileInfo;

    public UserInfo(String nickName, String userName, FileInfo fileInfo) {
        this.nickName = nickName;
        this.userName = userName;
        this.fileInfo = fileInfo;
    }

}
