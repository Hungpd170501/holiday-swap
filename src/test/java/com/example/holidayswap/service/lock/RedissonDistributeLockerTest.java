package com.example.holidayswap.service.lock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedissonDistributeLockerTest {

    @Mock
    private RedissonClient mockRedissonClient;

    private RedissonDistributeLocker redissonDistributeLockerUnderTest;

    @BeforeEach
    void setUp() {
        redissonDistributeLockerUnderTest = new RedissonDistributeLocker(mockRedissonClient);
    }

    @Test
    void testIsLocked() {
        // Setup
        RLock mockLock = mock(RLock.class);
        when(mockRedissonClient.getLock("lockKey")).thenReturn(mockLock);
        when(mockLock.isLocked()).thenReturn(false);
        // Run the test
        final boolean result = redissonDistributeLockerUnderTest.isLocked("lockKey");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testIsHeldByCurrentThread() {
        // Setup
        RLock mockLock = mock(RLock.class);
        when(mockRedissonClient.getLock("lockKey")).thenReturn(mockLock);
        when(mockLock.isHeldByCurrentThread()).thenReturn(true);

        // Run the test
        final boolean result = redissonDistributeLockerUnderTest.isHeldByCurrentThread("lockKey");

        // Verify the results
        assertThat(result).isTrue();
    }
}
