package com.example.holidayswap.repository.chat;

import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
    @Query("SELECT cp FROM ConversationParticipant cp WHERE cp.conversationParticipantId.conversationId = :conversationId")
    List<ConversationParticipant> findByConversationId(Long conversationId);

    @Query("SELECT cp FROM ConversationParticipant cp WHERE cp.conversationParticipantId.userId = :userId AND cp.conversationParticipantId.conversationId = :conversationId")
    Optional<ConversationParticipant> findByUserIdAndConversationId(Long userId, Long conversationId);
}
