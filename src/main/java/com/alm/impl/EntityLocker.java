package com.alm.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EntityLocker<T> implements IEntityLocker<T> {
    private final Map<T, ReentrantLock> internalLockMap = new ConcurrentHashMap<>();
    private ReentrantReadWriteLock globalLock = new ReentrantReadWriteLock();

    @Override
    public void lockEntity(T entityID) {
        boolean lockSucceed = false;
        globalLock.readLock().lock();
        while (!lockSucceed)
            lockSucceed = getLockByEntityID(entityID).tryLock();
    }

    @Override
    public boolean tryLockEntity(T entityID) {
        return globalLock.readLock().tryLock() && getLockByEntityID(entityID).tryLock();
    }

    @Override
    public boolean tryLockEntityWithTimeout(T entityID, long timeout, TimeUnit unit) throws InterruptedException {
        return getLockByEntityID(entityID).tryLock(timeout, unit);
    }


    @Override
    public void unlockEntity(T entityID) {
        if (internalLockMap.get(entityID) != null) {
            internalLockMap.get(entityID).unlock();
        }
        if (globalLock.getReadHoldCount() > 0)
            globalLock.readLock().unlock();
    }

    @Override
    public void globalLockEntity() {
        globalLock.writeLock().lock();
    }

    @Override
    public void globalUnlockEntity() {
        globalLock.writeLock().unlock();
    }


    private ReentrantLock getLockByEntityID(T entityID) {
        if (entityID == null)
            throw new IllegalArgumentException("EntityID can't be null.");
        internalLockMap.putIfAbsent(entityID, new ReentrantLock(true));
        return internalLockMap.get(entityID);
    }


}
