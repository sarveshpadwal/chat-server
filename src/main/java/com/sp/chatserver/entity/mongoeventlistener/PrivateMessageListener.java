package com.sp.chatserver.entity.mongoeventlistener;

import com.sp.chatserver.entity.PrivateMessage;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

@Component
public class PrivateMessageListener extends AbstractMongoEventListener<PrivateMessage> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<PrivateMessage> event) {
        String payload = event.getSource().getPayload();
        if (Objects.nonNull(payload)) {
            event.getSource().setPayload(Base64.getEncoder().encodeToString(payload.getBytes()));
        }
        event.getSource().setTs(Instant.now());
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<PrivateMessage> event) {
        String payload = event.getSource().getPayload();
        if (Objects.nonNull(payload)) {
            event.getSource().setPayload(new String(Base64.getDecoder().decode(payload)));
        }
    }
}
