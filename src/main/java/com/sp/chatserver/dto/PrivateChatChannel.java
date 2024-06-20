package com.sp.chatserver.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PrivateChatChannel {
    private String channelUuid;
    private Long from;
    private Long to;
}
