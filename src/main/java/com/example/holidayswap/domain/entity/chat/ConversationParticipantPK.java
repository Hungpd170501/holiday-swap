package com.example.holidayswap.domain.entity.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ConversationParticipantPK  implements Serializable {
    @Column(name = "conversation_id")
    private Long conversationId;
    @Column(name = "user_id")
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConversationParticipantPK)) return false;
        ConversationParticipantPK that = (ConversationParticipantPK) o;
        return Objects.equals(conversationId, that.conversationId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, userId);
    }
}
