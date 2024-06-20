package com.sp.chatserver.web.socket.controller;

import com.sp.chatserver.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class GroupChatChannelController implements GroupChatChannelApi {

    private final GroupChatService groupChatService;

    @Override
    public GroupChatService getDelegate() {
        return groupChatService;
    }
}
