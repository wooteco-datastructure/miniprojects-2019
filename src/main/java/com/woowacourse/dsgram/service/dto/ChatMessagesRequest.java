package com.woowacourse.dsgram.service.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatMessagesRequest {
    private List<ChatMessageResponse> prevMessages;

    public ChatMessagesRequest(List<ChatMessageResponse> prevMessages) {
        this.prevMessages = prevMessages;
    }
}
