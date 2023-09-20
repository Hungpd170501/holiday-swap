package com.example.holidayswap.service.lock;

import com.example.holidayswap.service.lock.DistributeLocker;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;


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
    public RFuture<Boolean> tryLockAsync(String lockKey, int waitTime, int leaseTime, TimeUnit timeUnit) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        Thread.sleep(1000);
        return lock.tryLockAsync(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void unLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unLock(RLock lock) {
        lock.unlock();
    }

    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit timeUnit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, timeUnit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit timeUnit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
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