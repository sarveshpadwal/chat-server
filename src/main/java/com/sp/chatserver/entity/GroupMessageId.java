package com.sp.chatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageId {
    private String channel;
    private Long msgId;

    public GroupMessageId(String channel) {
        this.channel = channel;
    }
}
