package com.example.holidayswap.repository.chat;

import com.example.holidayswap.domain.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface     MessageRepository extends JpaRepository<Message, Long> {
    @Query("""
            SELECT m FROM Message m
            WHERE m.conversation.conversationId = :conversationId
            ORDER BY m.createdOn DESC LIMIT 1
            """)
    Optional<Message> findLatestMessageByConversation(@Param("conversationId") Long conversationId);

    @Query("""
            SELECT m FROM Message m
            WHERE m.conversation.conversationId = :conversationId
            ORDER BY m.createdOn DESC
            """)
    List<Message> findAllByConversationIdEquals(@Param("conversationId") Long conversationId);
}
