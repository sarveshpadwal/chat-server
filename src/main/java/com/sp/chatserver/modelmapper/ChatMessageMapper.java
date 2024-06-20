package com.sp.chatserver.modelmapper;

import com.sp.chatserver.dto.ChatMessagePayload;
import com.sp.chatserver.entity.PrivateMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageMapper {
    public static List<ChatMessagePayload> map(List<PrivateMessage> chatMessages) {
        List<ChatMessagePayload> messages = new ArrayList<>();
        for (PrivateMessage privateMessage : chatMessages) {
            messages.add(
                    new ChatMessagePayload(
                            privateMessage.getFrom(),
                            privateMessage.getTo(),
                            privateMessage.getPayload(),
                            privateMessage.getAttachment()
                    )
            );
        }
        return messages;
    }

    public static PrivateMessage map(ChatMessagePayload dto) {
        return new PrivateMessage(
                dto.getFrom(),
                dto.getTo(),
                dto.getPayload(),
                dto.getFile()
        );
    }
}
