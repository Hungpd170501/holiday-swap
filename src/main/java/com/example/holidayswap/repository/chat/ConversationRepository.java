package com.example.holidayswap.repository.chat;

import com.example.holidayswap.domain.entity.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.conversationParticipantId.userId = :userId and p.leftChat = false")
    List<Conversation> findByUserId(@Param("userId") Long userId);
    @Query("SELECT c FROM Conversation c JOIN c.participants p WHERE p.conversationParticipantId.conversationId = :conversationId and p.leftChat = false")

    Optional<Conversation> findByConversationId(@Param("conversationId") Long conversationId);

    @Query("SELECT c.conversationId FROM Conversation c JOIN c.participants p WHERE p.conversationParticipantId.userId = :userId and p.leftChat = false and c.conversationId = :conversationId")
    Optional<Conversation> findByUserIdEqualsAndConversationIdEquals(@Param("userId") Long userId, @Param("conversationId") Long conversationId);

    @Query("""
        SELECT c FROM Conversation c
        INNER JOIN c.participants p
        ON c.conversationId = p.conversationParticipantId.conversationId
        WHERE (c.conversationName IS NULL OR c.conversationName = '')
        AND p.conversationParticipantId.userId IN (:currentUserId, :userId)
        AND p.leftChat = false
        GROUP BY c.conversationId
        HAVING COUNT(DISTINCT p.conversationParticipantId.userId) = 2
    """)
    Optional<Conversation> findConversationByUserIds(Long currentUserId, Long userId);
}
