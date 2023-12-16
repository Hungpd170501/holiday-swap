package com.example.holidayswap.service.chat;

import com.example.holidayswap.domain.dto.request.chat.MessageRequest;
import com.example.holidayswap.domain.dto.response.chat.MessageResponse;
import com.example.holidayswap.domain.entity.chat.Conversation;
import com.example.holidayswap.domain.entity.chat.Message;
import com.example.holidayswap.domain.entity.chat.MessageType;
import com.example.holidayswap.domain.mapper.chat.MessageMapper;
import com.example.holidayswap.repository.chat.ConversationParticipantRepository;
import com.example.holidayswap.repository.chat.ConversationRepository;
import com.example.holidayswap.repository.chat.MessageRepository;
import com.example.holidayswap.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository mockMessageRepository;
    @Mock
    private ConversationRepository mockConversationRepository;
    @Mock
    private MessageMapper mockMessageMapper;
    @Mock
    private FileService mockFileService;

    @Mock
    private ConversationParticipantRepository mockConversationParticipantRepository;

    private MessageServiceImpl messageServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        messageServiceImplUnderTest = new MessageServiceImpl(mockMessageRepository, mockConversationRepository,
                mockMessageMapper, mockFileService, mockConversationParticipantRepository);
    }

    @Test
    void testGetConversationMessages() {
        // Setup
        final List<MessageResponse> expectedResult = List.of(MessageResponse.builder().build());

        // Configure MessageRepository.findAllByConversationIdEquals(...).
        final List<Message> messages = List.of(Message.builder()
                .image("image")
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build());
        when(mockMessageRepository.findAllByConversationIdEquals(0L)).thenReturn(messages);

        when(mockMessageMapper.toMessageResponse(Message.builder()
                .image("image")
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build())).thenReturn(MessageResponse.builder().build());

        // Run the test
        final List<MessageResponse> result = messageServiceImplUnderTest.getConversationMessages(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetConversationMessages_MessageRepositoryReturnsNoItems() {
        // Setup
        when(mockMessageRepository.findAllByConversationIdEquals(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<MessageResponse> result = messageServiceImplUnderTest.getConversationMessages(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCreateMessage() throws Exception {
        // Setup
        final MessageRequest messageRequest = new MessageRequest();
        messageRequest.setText("text");
        messageRequest.setAuthorId(0L);

        final MessageResponse expectedResult = MessageResponse.builder().build();

        // Configure MessageMapper.toMessage(...).
        final Message message = Message.builder()
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build();
        final MessageRequest messageRequest1 = new MessageRequest();
        messageRequest1.setText("text");
        messageRequest1.setAuthorId(0L);
        when(mockMessageMapper.toMessage(messageRequest1)).thenReturn(message);

        // Configure ConversationRepository.findById(...).
        final Optional<Conversation> conversation = Optional.of(Conversation.builder().build());
        when(mockConversationRepository.findById(0L)).thenReturn(conversation);

        when(mockMessageMapper.toMessageResponse(Message.builder()
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build())).thenReturn(MessageResponse.builder().build());

        // Run the test
        final MessageResponse result = messageServiceImplUnderTest.createMessage(messageRequest, "0");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockMessageRepository).save(Message.builder()
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build());
    }

    @Test
    void testCreateMessage_ConversationRepositoryReturnsAbsent() {
        // Setup
        final MessageRequest messageRequest = new MessageRequest();
        messageRequest.setText("text");
        messageRequest.setAuthorId(0L);

        // Configure MessageMapper.toMessage(...).
        final Message message = Message.builder()
                .messageType(MessageType.TEXT)
                .conversation(Conversation.builder().build())
                .build();
        final MessageRequest messageRequest1 = new MessageRequest();
        messageRequest1.setText("text");
        messageRequest1.setAuthorId(0L);
        when(mockMessageMapper.toMessage(messageRequest1)).thenReturn(message);
        when(mockConversationRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> messageServiceImplUnderTest.createMessage(messageRequest, "0"))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testCreateMessage_FileServiceThrowsIOException() throws Exception {
        // Setup
        final MessageRequest messageRequest = new MessageRequest();
        messageRequest.setText("text");
        messageRequest.setImage(new MockMultipartFile("name", "content".getBytes()));
        messageRequest.setAuthorId(0L);

        // Configure MessageMapper.toMessage(...).
        final Message message = Message.builder()
                .image("image")
                .messageType(MessageType.TEXT_AND_IMAGE)
                .conversation(Conversation.builder().build())
                .build();
        when(mockMessageMapper.toMessage(messageRequest)).thenReturn(message);

        // Configure ConversationRepository.findById(...).
        final Optional<Conversation> conversation = Optional.of(Conversation.builder().build());
        when(mockConversationRepository.findById(0L)).thenReturn(conversation);

        when(mockFileService.uploadFile(any(MultipartFile.class))).thenThrow(IOException.class);

        // Run the test
        assertThatThrownBy(
                () -> messageServiceImplUnderTest.createMessage(messageRequest, "0"))
                .isInstanceOf(IOException.class);
    }
}
