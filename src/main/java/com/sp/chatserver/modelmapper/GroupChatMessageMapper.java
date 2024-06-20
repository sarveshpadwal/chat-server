package com.sp.chatserver.modelmapper;

import com.sp.chatserver.dto.GroupChatMessage;
import com.sp.chatserver.dto.GroupChatMessagePayload;
import com.sp.chatserver.entity.GroupMessage;

import java.util.ArrayList;
import java.util.List;

public class GroupChatMessageMapper {
    public static List<GroupChatMessage> map(List<GroupMessage> groupMessages) {
        List<GroupChatMessage> messages = new ArrayList<>();
        for (GroupMessage groupMessage : groupMessages) {
            messages.add(map(groupMessage));
        }
        return messages;
    }

    public static GroupMessage map(GroupChatMessagePayload dto) {
        return new GroupMessage(
                dto.getUser(),
                dto.getPayload(),
                dto.getFile()
        );
    }

    public static GroupChatMessage map(GroupMessage groupMessage) {
        return new GroupChatMessage(
                groupMessage.getGroupMessageId().getMsgId(),
                groupMessage.getUser(),
                groupMessage.getPayload(),
                groupMessage.getAttachment()
        );
    }
}
