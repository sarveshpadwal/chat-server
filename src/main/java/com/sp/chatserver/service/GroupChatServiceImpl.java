package com.sp.chatserver.service;

import com.sp.chatserver.dto.GroupChatMessage;
import com.sp.chatserver.dto.GroupChatMessagePayload;
import com.sp.chatserver.dto.GroupChatParticipants;
import com.sp.chatserver.entity.GroupMessage;
import com.sp.chatserver.entity.GroupMessageId;
import com.sp.chatserver.modelmapper.GroupChatMessageMapper;
import com.sp.chatserver.repository.GroupMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupChatServiceImpl implements GroupChatService {

    private final GroupMessageRepository repo;

    @Override
    public String save(GroupChatParticipants groupChatParticipants) {
        GroupMessage groupMessage = repo.save(new GroupMessage());
        groupMessage.setParticipants(groupChatParticipants.getUsers());
        return groupMessage.getId();
    }

    @Override
    public GroupChatMessage save(String channelId, GroupChatMessagePayload message) {
        GroupMessage groupMessage = GroupChatMessageMapper.map(message);
        groupMessage.setGroupMessageId(new GroupMessageId(channelId));
        groupMessage = repo.save(groupMessage);
        return new GroupChatMessage(
                groupMessage.getGroupMessageId().getMsgId(),
                message.getUser(),
                message.getPayload(),
                message.getFile()
        );
    }

    @Override
    public String addUserToGroup(String channelId, Long user) {
        GroupMessage groupMessage = repo.findById(channelId).orElseThrow(() -> new RuntimeException("group not found"));
        groupMessage.getParticipants().add(user);
        repo.save(groupMessage);
        return groupMessage.getId();
    }

    @Override
    public List<GroupChatMessage> getMessages(String channel) {
        List<GroupMessage> groupMessages = repo.findAllByChannel(channel);
        return GroupChatMessageMapper.map(groupMessages);
    }

    @Override
    public Set<String> getGroupChatChannels(Long user) {
        return repo.findDistinctByParticipantsContains(user).stream()
                .map(GroupMessage::getId)
                .collect(Collectors.toSet());
    }
}
