package com.alm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public class EntityLocker<T> implements IEntityLocker<T> {
    private final Map<T, ReentrantLock> internalLockMap = new ConcurrentHashMap<>();

    @Override
    public void lockEntity(T entityID) {
        getLockByEntityID(entityID).lock();
    }

    @Override
    public void lockEntity(T entityID, long timeout) throws InterruptedException, TimeoutException {
        if (!getLockByEntityID(entityID).tryLock(timeout, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }

    @Override
    public void unlockEntity() {

    }

    private ReentrantLock getLockByEntityID(T entityID) {
        if (entityID == null)
            throw new IllegalArgumentException("EntityID can't be null.");
        internalLockMap.putIfAbsent(entityID, new ReentrantLock(true));
        return internalLockMap.get(entityID);
    }


}
