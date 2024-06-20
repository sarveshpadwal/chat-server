package com.sp.chatserver.repository;

import com.sp.chatserver.entity.PrivateMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivateMessageRepository extends MongoRepository<PrivateMessage, String> {

    @Query(value = "{ $or: [ { 'from': ?0, 'to': ?1 }, { 'from': ?1, 'to': ?0 } ] }", fields = "{ 'id': 1 }")
    List<PrivateMessage> findExistingChannel(Long user1, Long user2, Sort sort);

    default Optional<PrivateMessage> findExistingChannel(Long user1, Long user2) {
        Sort sort = Sort.by(Sort.Direction.ASC, "ts");
        List<PrivateMessage> messageList = findExistingChannel(user1, user2, sort);
        if (!messageList.isEmpty()) {
            return Optional.of(messageList.get(0));
        }
        return Optional.empty();
    }

    @Query(value = "{ 'channel': ?0 }")
    List<PrivateMessage> findAllByChannel(String channel);

    @Query(value = "{ $or: [ { 'from': ?0 }, { 'to': ?0 } ] }", fields = "{ 'channel': 1 }")
    @Aggregation(pipeline = { "{ '$group': { '_id' : '$channel' } }" })
    List<String> findAllChannelByUser(Long user);
}
