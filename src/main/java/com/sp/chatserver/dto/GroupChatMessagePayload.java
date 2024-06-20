package com.sp.chatserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatMessagePayload {
    private Long user;
    private String payload;
    private String file;
}
