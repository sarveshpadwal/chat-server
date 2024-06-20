package com.sp.chatserver.repository;

import com.sp.chatserver.entity.GroupMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMessageRepository extends MongoRepository<GroupMessage, String> {

    @Query(value = "{ 'groupMessageId.channel': ?0 }")
    List<GroupMessage> findAllByChannel(String channel);

    List<GroupMessage> findDistinctByParticipantsContains(Long user);
}
