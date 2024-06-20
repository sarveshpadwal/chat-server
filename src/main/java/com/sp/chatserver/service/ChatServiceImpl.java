package com.sp.chatserver.service;

import com.sp.chatserver.dto.ChatMessage;
import com.sp.chatserver.dto.ChatMessagePayload;
import com.sp.chatserver.dto.PrivateChatParticipants;
import com.sp.chatserver.entity.PrivateMessage;
import com.sp.chatserver.modelmapper.ChatMessageMapper;
import com.sp.chatserver.repository.PrivateMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final PrivateMessageRepository repo;

    @Override
    public String save(String channel, ChatMessagePayload message) {
        PrivateMessage privateMessage = ChatMessageMapper.map(message);
        privateMessage.setChannel(channel);
        repo.save(privateMessage);
        return privateMessage.getId();
    }

    @Override
    public String establishPrivateChatSession(PrivateChatParticipants privateChatParticipants) {
        return getExistingPrivateChatChannel(privateChatParticipants)
                .orElseGet(() -> newPrivateChatChannel(privateChatParticipants));
    }

    private Optional<String> getExistingPrivateChatChannel(PrivateChatParticipants privateChatParticipants) {
        return repo.findExistingChannel(privateChatParticipants.getUser1(), privateChatParticipants.getUser2())
                .map(PrivateMessage::getId);
    }

    private String newPrivateChatChannel(PrivateChatParticipants privateChatParticipants) {
        PrivateMessage privateMessage = new PrivateMessage(privateChatParticipants.getUser1(),
                privateChatParticipants.getUser2());
        PrivateMessage persistedMsg = repo.save(privateMessage);
        return persistedMsg.getId();
    }

    @Override
    public List<ChatMessagePayload> getMessages(String channel) {
        List<PrivateMessage> privateMessages = repo.findAllByChannel(channel);
        return ChatMessageMapper.map(privateMessages);
    }

    @Override
    public List<String> getChatChannels(Long user) {
        List<String> channels = repo.findAllChannelByUser(user);
        channels.remove(null);
        return channels;
    }
}
