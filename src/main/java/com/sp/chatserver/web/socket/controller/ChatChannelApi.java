package com.sp.chatserver.web.socket.controller;

import com.sp.chatserver.dto.ChatMessagePayload;
import com.sp.chatserver.dto.PrivateChatParticipants;
import com.sp.chatserver.service.ChatService;
import com.sp.chatserver.service.ChatServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ChatChannelApi {

    default ChatService getDelegate() {
        return new ChatServiceImpl(null);
    }

    @MessageMapping("/chat.{channelId}")
    @SendTo("/topic/chat.{channelId}")
    default ChatMessagePayload chatMessage(@DestinationVariable String channelId, ChatMessagePayload message) {
        getDelegate().save(channelId, message);
        return message;
    }

    @PostMapping(value = "/api/chat/channel", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<String> establishPrivateChatSession(@RequestBody PrivateChatParticipants privateChatParticipants) {
        return new ResponseEntity<>(getDelegate().establishPrivateChatSession(privateChatParticipants), HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/chat/channel/{channelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<List<ChatMessagePayload>> getMessages(@PathVariable("channelId") String channelId) {
        return new ResponseEntity<>(getDelegate().getMessages(channelId), HttpStatus.OK);
    }

    @GetMapping(value = "/api/chat/channel/user/{user}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<List<String>> getChatChannels(@PathVariable("user") Long user) {
        return new ResponseEntity<>(getDelegate().getChatChannels(user), HttpStatus.OK);
    }
}
