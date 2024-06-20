package com.sp.chatserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagePayload {
    private Long from;
    private Long to;
    private String payload;
    private String file;
}
