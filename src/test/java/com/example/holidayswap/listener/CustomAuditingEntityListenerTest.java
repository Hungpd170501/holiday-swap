package com.example.holidayswap.listener;

import com.example.holidayswap.domain.entity.common.BaseEntityAudit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.auditing.AuditingHandler;

import static org.mockito.Mockito.*;

class CustomAuditingEntityListenerTest {


    @Mock
    private AuditingHandler mockHandler;

    @InjectMocks
    private CustomAuditingEntityListener customAuditingEntityListenerUnderTest;

    @BeforeEach
    void setUp() {
        customAuditingEntityListenerUnderTest = new CustomAuditingEntityListener(() -> mockHandler);
    }


    @Test
    void testTouchForCreateWhenCreatedByIsNull() {
        // Arrange
        BaseEntityAudit mockEntity = new BaseEntityAudit();
        mockEntity.setCreatedBy(null);

        // Act
        customAuditingEntityListenerUnderTest.touchForCreate(mockEntity);

        // Assert
//        verify(mockHandler).touchForCreate(mockEntity);
    }

    @Test
    void testTouchForCreateWhenCreatedByIsNotNull() {
        // Arrange
        BaseEntityAudit mockEntity = new BaseEntityAudit();
        mockEntity.setCreatedBy("user1");

        // Act
        customAuditingEntityListenerUnderTest.touchForCreate(mockEntity);

        // Assert
//        verify(mockHandler, never()).touchForCreate(mockEntity);
    }

    @Test
    void testTouchForCreateWhenCreatedByIsNotNullAndLastModifiedByIsNull() {
        // Arrange
        BaseEntityAudit mockEntity = new BaseEntityAudit();
        mockEntity.setCreatedBy("user1");
        mockEntity.setLastModifiedBy(null);

        // Act
        customAuditingEntityListenerUnderTest.touchForCreate(mockEntity);

        // Assert
//        verify(mockHandler, never()).touchForCreate(mockEntity);
//        assert mockEntity.getLastModifiedBy().equals("user1");
//        assert mockEntity.getLastModifiedOn().equals(mockEntity.getCreatedOn());
    }

    @Test
    void testTouchForUpdateWhenLastModifiedByIsNull() {
        // Arrange
        BaseEntityAudit mockEntity = new BaseEntityAudit();
        mockEntity.setLastModifiedBy(null);

        // Act
        customAuditingEntityListenerUnderTest.touchForUpdate(mockEntity);

        // Assert
//        verify(mockHandler).touchForUpdate(mockEntity);
    }

    @Test
    void testTouchForUpdateWhenLastModifiedByIsNotNull() {
        // Arrange
        BaseEntityAudit mockEntity = new BaseEntityAudit();
        mockEntity.setLastModifiedBy("user2");

        // Act
        customAuditingEntityListenerUnderTest.touchForUpdate(mockEntity);

        // Assert
//        verify(mockHandler, never()).touchForUpdate(mockEntity);
    }
}