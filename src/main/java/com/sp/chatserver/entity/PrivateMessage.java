package com.sp.chatserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("private_message")
public class PrivateMessage {
    @Id
    private String id;
    private String channel;
    private Long from;
    private Long to;
    private String payload;
    private String attachment;
    private Instant ts;

    public PrivateMessage(Long from, Long to, String payload, String attachment) {
        this.from = from;
        this.to = to;
        this.payload = payload;
        this.attachment = attachment;
    }

    public PrivateMessage(Long from, Long to) {
        this.from = from;
        this.to = to;
    }
}
