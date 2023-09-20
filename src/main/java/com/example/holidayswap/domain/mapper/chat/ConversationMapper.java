package com.example.holidayswap.domain.mapper.chat;

import com.example.holidayswap.domain.dto.response.chat.ConversationResponse;
import com.example.holidayswap.domain.entity.chat.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);
    ConversationResponse toConversationResponse(Conversation conversation);
}
