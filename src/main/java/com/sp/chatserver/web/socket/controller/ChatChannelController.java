package com.sp.chatserver.web.socket.controller;

import com.sp.chatserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatChannelController implements ChatChannelApi {

    private final ChatService chatService;

    @Override
    public ChatService getDelegate() {
        return chatService;
    }
}
