package com.example.holidayswap.service.lock;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;

public interface DistributeLocker {
    /**
     * Acquire a lock
     *
     * @param lockKey Key for the lock
     * @return RLock
     */
    RLock getLock(String lockKey);

    /**
     * Acquire a fair lock
     *
     * @param lockKey Key for the lock
     * @return RLock
     */
    RLock getFairLock(String lockKey);

    /**
     * Acquire a read-write lock
     *
     * @param lockKey Key for the lock
     * @return RReadWriteLock
     */
    RReadWriteLock getReadWriteLock(String lockKey);

    /**
     * Acquire a semaphore
     *
     * @param lockKey Key for the lock
     * @return RSemaphore
     */
    RSemaphore getSemaphore(String lockKey);

    /**
     * Acquire a countdown latch
     *
     * @param lockKey Key for the lock
     * @return RCountDownLatch
     */
    RCountDownLatch getCountDownLatch(String lockKey);

    /**
     * Attempt to acquire a lock asynchronously
     *
     * @param lockKey   Key for the lock
     * @param waitTime  Maximum time to wait for the lock
     * @param leaseTime Lock expiration time
     * @param timeUnit  Time unit
     * @return true if acquired successfully, false if not
     * @throws InterruptedException Interruption exception
     */
    RFuture<Boolean> tryLockAsync(String lockKey, int waitTime, int leaseTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * Release a lock
     *
     * @param lockKey Key for the lock
     */
    void unLock(String lockKey);

    /**
     * Release a lock
     *
     * @param lock Lock
     */
    void unLock(RLock lock);

    /**
     * Lock with a timeout
     *
     * @param lockKey Key for the lock
     * @param timeout Lock expiration time
     * @return RLock
     */
    RLock lock(String lockKey, int timeout);

    /**
     * Lock with a timeout
     *
     * @param lockKey  Key for the lock
     * @param timeUnit Time unit
     * @param timeout  Lock expiration time
     * @return RLock
     */
    RLock lock(String lockKey, TimeUnit timeUnit, int timeout);

    /**
     * Try to acquire a lock
     *
     * @param lockKey   Key for the lock
     * @param waitTime  Maximum time to wait for the lock
     * @param leaseTime Lock expiration time
     * @return true if acquired successfully, false if not
     */
    boolean tryLock(String lockKey, int waitTime, int leaseTime);

    /**
     * Try to acquire a lock
     *
     * @param lockKey   Key for the lock
     * @param timeUnit  Time unit
     * @param waitTime  Maximum time to wait for the lock
     * @param leaseTime Lock expiration time
     * @return true if acquired successfully, false if not
     */
    boolean tryLock(String lockKey, TimeUnit timeUnit, int waitTime, int leaseTime);

    /**
     * Check if a lock is held by any thread
     *
     * @param lockKey Key for the lock
     * @return true if locked, false if not
     */
    boolean isLocked(String lockKey);

    /**
     * Check if the current thread holds this lock
     *
     * @param lockKey Key for the lock
     * @return true if locked, false if not
     */
    boolean isHeldByCurrentThread(String lockKey);
}
