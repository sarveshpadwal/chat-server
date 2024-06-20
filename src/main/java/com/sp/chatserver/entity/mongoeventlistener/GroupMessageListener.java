package com.sp.chatserver.entity.mongoeventlistener;

import com.sp.chatserver.entity.GroupMessage;
import com.sp.chatserver.entity.GroupMessageId;
import com.sp.chatserver.entity.PrivateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class GroupMessageListener extends AbstractMongoEventListener<GroupMessage> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<GroupMessage> event) {
        GroupMessage groupMessage = event.getSource();
        GroupMessageId groupMessageId = groupMessage.getGroupMessageId();
        if (Objects.nonNull(groupMessageId)) {
            Long msgId = redisTemplate.opsForValue().increment(groupMessageId.getChannel());
            groupMessageId.setMsgId(msgId);
        }
        String payload = groupMessage.getPayload();
        if (Objects.nonNull(payload)) {
            groupMessage.setPayload(Base64.getEncoder().encodeToString(payload.getBytes()));
        }
        groupMessage.setTs(Instant.now());
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<GroupMessage> event) {
        String payload = event.getSource().getPayload();
        if (Objects.nonNull(payload)) {
            event.getSource().setPayload(new String(Base64.getDecoder().decode(payload)));
        }
    }
}
