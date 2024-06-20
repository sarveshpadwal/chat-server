package com.sp.chatserver.web.socket.controller;

import com.sp.chatserver.dto.GroupChatMessage;
import com.sp.chatserver.dto.GroupChatMessagePayload;
import com.sp.chatserver.dto.GroupChatParticipants;
import com.sp.chatserver.service.GroupChatService;
import com.sp.chatserver.service.GroupChatServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface GroupChatChannelApi {

    default GroupChatService getDelegate() {
        return new GroupChatServiceImpl(null);
    }

    @MessageMapping("/group.chat.{channelId}")
    @SendTo("/topic/group.chat.{channelId}")
    default GroupChatMessage chatMessage(@DestinationVariable String channelId, GroupChatMessagePayload message) {
        return getDelegate().save(channelId, message);
    }

    @PostMapping(value = "/api/groupchat/channel", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<String> createGroup(@RequestBody GroupChatParticipants groupChatParticipants) {
        return new ResponseEntity<>(getDelegate().save(groupChatParticipants), HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/groupchat/channel/{channelId}/user/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<String> joinGroup(@PathVariable("channelId") String channelId,
                                             @PathVariable("user") Long user) {
        return new ResponseEntity<>(getDelegate().addUserToGroup(channelId, user), HttpStatus.OK);
    }

    @GetMapping(value = "/api/groupchat/channel/{channelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<List<GroupChatMessage>> getMessages(@PathVariable("channelId") String channelId) {
        return new ResponseEntity<>(getDelegate().getMessages(channelId), HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/{user}/groupchat/channel", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Set<String>> getGroupChatChannels(@PathVariable("user") Long user) {
        return new ResponseEntity<>(getDelegate().getGroupChatChannels(user), HttpStatus.OK);
    }
}
