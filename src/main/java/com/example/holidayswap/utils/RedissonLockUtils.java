package com.example.holidayswap.utils;

import com.example.holidayswap.service.lock.DistributeLocker;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;

public class RedissonLockUtils {
    private static DistributeLocker locker;

    private RedissonLockUtils() {
    }

    public static void setLocker(DistributeLocker locker) {
        RedissonLockUtils.locker = locker;
    }

    public static RLock getLock(String lockKey) {
        return locker.getLock(lockKey);
    }

    public static RLock getFairLock(String lockKey) {
        return locker.getFairLock(lockKey);
    }

    public static RReadWriteLock getReadWriteLock(String lockKey) {
        return locker.getReadWriteLock(lockKey);
    }

    public static RSemaphore getSemaphore(String lockKey) {
        return locker.getSemaphore(lockKey);
    }


    public static RCountDownLatch getCountDownLatch(String lockKey) {
        return locker.getCountDownLatch(lockKey);
    }

    public static boolean isLocked(String lockKey) {
        RLock lock = locker.getLock(lockKey);
        return lock.isLocked();
    }

    public static boolean isHeldByCurrentThread(String lockKey) {
        RLock lock = locker.getLock(lockKey);
        return lock.isHeldByCurrentThread();
    }
}
