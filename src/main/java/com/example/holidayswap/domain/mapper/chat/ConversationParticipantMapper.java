package com.example.holidayswap.domain.mapper.chat;

import com.example.holidayswap.domain.dto.response.chat.ConversationParticipantResponse;
import com.example.holidayswap.domain.entity.chat.ConversationParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConversationParticipantMapper {
    ConversationParticipantMapper INSTANCE = Mappers.getMapper(ConversationParticipantMapper.class);
    @Mapping(target = "user.emailVerified", source = "user.emailVerified", ignore = true)
    @Mapping(target = "user.phoneVerified", source = "user.phoneVerified", ignore = true)
    @Mapping(target = "user.phone", source = "user.phone", ignore = true)
    ConversationParticipantResponse toConversationParticipantResponse(ConversationParticipant conversationParticipant);

}
