package com.sp.chatserver.service;

import com.sp.chatserver.dto.GroupChatMessage;
import com.sp.chatserver.dto.GroupChatMessagePayload;
import com.sp.chatserver.dto.GroupChatParticipants;

import java.util.List;
import java.util.Set;

public interface GroupChatService {
    String save(GroupChatParticipants groupChatParticipants);

    GroupChatMessage save(String channelId, GroupChatMessagePayload message);

    String addUserToGroup(String channelId, Long user);

    List<GroupChatMessage> getMessages(String channelId);

    Set<String> getGroupChatChannels(Long user);

}
