package com.alm.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.locks.ReentrantLock;

public class LockWrapper {
    private final ReentrantLock lock;
    /* Internal mark to help maintain size. Can also be used to build thread map for deadlock part.*/
    private AtomicMarkableReference<Long> visitorsCounter
            = new AtomicMarkableReference<>(0L, false);

    public LockWrapper(boolean fairness) {
        this.lock = new ReentrantLock(fairness);
    }

    /**
     * Internal trylock wrapper staff.
     * @return true - if locking was successful and reference counted.
     * If not - possible reenter with save us.
     */
    public boolean tryLock() {
        long counter = visitorsCounter.getReference();
        return lock.tryLock() && visitorsCounter.compareAndSet(counter, counter + 1, false, false);
    }

    /**
     * Like prevoius, but with standard timeout staff.
     */
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        long counter = visitorsCounter.getReference();
        return lock.tryLock(timeout, unit) && visitorsCounter.compareAndSet(
                counter,
                counter + 1,
                false,
                false
        );
    }

    /**
     * Internal unlock wrapper staff.
     * Unlocking internal reentrant lock and updating visitors count.
     */
    public void unlock() {
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            //TODO Undefined behavior. Don't think that this is error, maybe skip with holdcount?
            //throw new IllegalStateException("Seems that unlock called without locking.");
        }
        boolean refRemoveSuccessful = false;
        while (!refRemoveSuccessful) {
            long counter = visitorsCounter.getReference();
            refRemoveSuccessful = visitorsCounter.compareAndSet(
                    counter,
                    counter - 1,
                    false,
                    false
            );
        }

    }

    /**
     * This method can be used for automatic internal storage cleaning.
     * For ex. with some kind of CacheStateMonitor or just background job.
     *
     * @return true - if this lock can be removed from storage.
     */
    public boolean remove() {
        return visitorsCounter.compareAndSet(0L, 0L, false, true);
    }


}
