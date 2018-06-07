package com.alm;

import java.util.concurrent.TimeoutException;

/**
 * Requirements:
 * <p>
 * 1. EntityLocker should support different types of entity IDs.
 * <p>
 * 2. EntityLocker’s interface should allow the caller to specify which entity does it want to work with (using entity ID), and designate the boundaries of the code that should have exclusive access to the entity (called “protected code”).
 * <p>
 * 3. For any given entity, EntityLocker should guarantee that at most one thread executes protected code on that entity. If there’s a concurrent request to lock the same entity, the other thread should wait until the entity becomes available.
 * <p>
 * 4. EntityLocker should allow concurrent execution of protected code on different entities.
 *
 * @param <T> type of entityID
 */
public interface IEntityLocker<T> {

    /**
     * Entry point of the protected code.
     *
     * @param entityID with which caller want to work with.
     */
    void lockEntity(T entityID);

    /**
     * Entry point of the protected code with timeout.
     *
     * @param entityID with which caller want to work with.
     * @param timeout in milliseconds for lock wait.
     */
    void lockEntity(T entityID, long timeout) throws InterruptedException, TimeoutException;

    /**
     * Exit point of the protected code.
     */
    void unlockEntity();

}
