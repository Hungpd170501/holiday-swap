package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.MessageRequest;
import com.example.holidayswap.domain.dto.response.chat.MessageResponse;
import com.example.holidayswap.domain.entity.auth.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MessageService {
    List<MessageResponse> getConversationMessages(Long conversationId);

    MessageResponse createMessage(MessageRequest messageRequest, String conversationId) throws IOException;

    void markAllAsRead(Long userId, Long conversationId);

    void createConversationParticipantIfNotExist(User user, Long conversationId);

    void markReadByMessageId(long userId, long conversationId, long messageId);
}
