package com.sp.chatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("group_message")
public class GroupMessage {
    @Id
    private String id;
    private GroupMessageId groupMessageId;
    private Long user;
    private String payload;
    private String attachment;
    private Set<Long> participants;
    private Instant ts;
    public GroupMessage(Long user, String payload, String attachment) {
        this.user = user;
        this.payload = payload;
        this.attachment = attachment;
    }
}
