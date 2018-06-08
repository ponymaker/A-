package com.alm.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class EntityLocker<T> implements IEntityLocker<T> {
    private final Map<T, ReentrantLock> internalLockMap = new ConcurrentHashMap<>();

    @Override
    public void lockEntity(T entityID) {
        boolean lockSucceed = false;
        while (!lockSucceed)
            lockSucceed = getLockByEntityID(entityID).tryLock();
    }

    @Override
    public boolean tryLockEntity(T entityID) {
        return getLockByEntityID(entityID).tryLock();
    }

    @Override
    public void unlockEntity(T entityID) {
        if (internalLockMap.get(entityID) != null) {
            internalLockMap.get(entityID).unlock();
        }
    }

    private ReentrantLock getLockByEntityID(T entityID) {
        if (entityID == null)
            throw new IllegalArgumentException("EntityID can't be null.");
        internalLockMap.putIfAbsent(entityID, new ReentrantLock(true));
        return internalLockMap.get(entityID);
    }


}
