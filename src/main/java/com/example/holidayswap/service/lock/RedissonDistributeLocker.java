package com.example.holidayswap.service.lock;

import org.redisson.api.*;


public class RedissonDistributeLocker implements DistributeLocker {

    private final RedissonClient redissonClient;

    public RedissonDistributeLocker(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    @Override
    public RLock getFairLock(String lockKey) {
        return redissonClient.getFairLock(lockKey);
    }

    @Override
    public RReadWriteLock getReadWriteLock(String lockKey) {
        return redissonClient.getReadWriteLock(lockKey);
    }

    @Override
    public RSemaphore getSemaphore(String lockKey) {
        return redissonClient.getSemaphore(lockKey);
    }

    @Override
    public RCountDownLatch getCountDownLatch(String lockKey) {
        return redissonClient.getCountDownLatch(lockKey);
    }

    @Override
    public boolean isLocked(String lockKey) {
        return redissonClient.getLock(lockKey).isLocked();
    }

    @Override
    public boolean isHeldByCurrentThread(String lockKey) {
        return redissonClient.getLock(lockKey).isHeldByCurrentThread();
    }
}