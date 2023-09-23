package com.example.holidayswap.service.lock;

import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;

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
