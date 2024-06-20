package com.sp.chatserver;

import com.sp.chatserver.dto.GroupChatMessage;
import com.sp.chatserver.entity.GroupMessage;
import com.sp.chatserver.entity.GroupMessageId;
import com.sp.chatserver.entity.PrivateMessage;
import com.sp.chatserver.repository.GroupMessageRepository;
import com.sp.chatserver.repository.PrivateMessageRepository;
import com.sp.chatserver.service.ChatService;
import com.sp.chatserver.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EnableMongoRepositories(basePackages = "com.sp.chatserver.repository")
//@EnableRedisHttpSession
@SpringBootApplication
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
    }

    @Autowired
    GroupMessageRepository repo;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    GroupChatService service;


    /*@Override
    public void run(String... args) throws Exception {
        *//*Set<Long> participants = Set.of(10L, 20L, 30L);
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setParticipants(participants);
        groupMessage = repo.save(groupMessage);
        System.out.println(groupMessage);

        String channel = groupMessage.getId();

        redisTemplate.opsForValue().set(channel, 0);

        String payload1 = "Hi Guys";
        Long user1 = 10L;
        GroupMessageId groupMessageId1 = new GroupMessageId(channel);
        GroupMessage groupMessage1 = new GroupMessage(user1, payload1, null);
        groupMessage1.setGroupMessageId(groupMessageId1);
        repo.save(groupMessage1);

        String payload2 = "Hi Guys";
        Long user2 = 20L;
        GroupMessageId groupMessageId2 = new GroupMessageId(channel);
        GroupMessage groupMessage2 = new GroupMessage(user2, payload2, null);
        groupMessage2.setGroupMessageId(groupMessageId2);
        repo.save(groupMessage2);

        String payload3 = "Hi Guys";
        Long user3 = 30L;
        GroupMessageId groupMessageId3 = new GroupMessageId(channel);
        GroupMessage groupMessage3 = new GroupMessage(user3, payload3, null);
        groupMessage3.setGroupMessageId(groupMessageId3);
        repo.save(groupMessage3);

        System.out.println("redis group msg id:"+redisTemplate.opsForValue().get(channel));*//*

        *//*String channel = "6672bf9988725b37cad853dc";
        List<GroupChatMessage> messages = service.getMessages(channel);
        System.out.println("messages:::"+messages);

        Set<String> groupChatChannels = service.getGroupChatChannels(10L);
        System.out.println("groupChatChannels:::"+groupChatChannels);*//*
    }*/
}
