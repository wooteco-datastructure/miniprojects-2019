package com.woowacourse.dsgram.service.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatMessageRequest {
    private long from;
    private String content;

    public ChatMessageRequest(long from, String content) {
        this.from = from;
        this.content = content;
    }
}
