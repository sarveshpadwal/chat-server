package com.sp.chatserver.service;

import com.sp.chatserver.dto.ChatMessage;
import com.sp.chatserver.dto.ChatMessagePayload;
import com.sp.chatserver.dto.PrivateChatParticipants;

import java.util.List;

public interface ChatService {
    String save(String channelId, ChatMessagePayload message);

    String establishPrivateChatSession(PrivateChatParticipants privateChatParticipants);

    List<ChatMessagePayload> getMessages(String channelId);

    List<String> getChatChannels(Long user);
}
