package com.alm.test;

import com.alm.impl.EntityLocker;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

import java.util.concurrent.TimeUnit;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;


public class EntityLockerTest {
    @JCStressTest
    @Description("Simple test for concurrent protected code execution for one entity.")
    @Outcome(id = "1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleOneEntityLockTest {
        private SimpleTestObject simpleTestObject = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
            try {
                simpleTestObject.increment();
                result.r1 = simpleTestObject.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
            }
        }
        @Actor
        public void actor2(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
            try {
                simpleTestObject.increment();
                result.r2 = simpleTestObject.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
            }
        }
    }

    @JCStressTest
    @Description("Simple test for concurrent protected code execution for two entities.")
    @Outcome(id = "1, 1", expect = ACCEPTABLE, desc = "Correct incement.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleTwoEntityLockTest {
        private SimpleTestObject simpleTestObject1 = new SimpleTestObject();
        private SimpleTestObject simpleTestObject2 = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject1.getId());
            try {
                simpleTestObject1.increment();
                result.r1 = simpleTestObject1.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject1.getId());
            }
        }
        @Actor
        public void actor2(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject2.getId());
            try {
                simpleTestObject2.increment();
                result.r2 = simpleTestObject2.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject2.getId());
            }
        }
    }

    @JCStressTest
    @Description("Simple test for concurrent protected code execution for two entities.")
    @Outcome(id = "2, 3", expect = ACCEPTABLE, desc = "First reenter, then second.")
    @Outcome(id = "3, 1", expect = ACCEPTABLE, desc = "Second increment first, then reenter for first.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleOneEntityReenterTest {
        private SimpleTestObject simpleTestObject1 = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject1.getId());
            try {
                simpleTestObject1.increment();
                result.r1 = simpleTestObject1.getValue();
                simpleTestObjectEntityLocker.lockEntity(simpleTestObject1.getId());
                try {
                    simpleTestObject1.increment();
                    result.r1 = simpleTestObject1.getValue();
                } finally {
                    simpleTestObjectEntityLocker.unlockEntity(simpleTestObject1.getId());
                }
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject1.getId());
            }
        }
        @Actor
        public void actor2(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject1.getId());
            try {
                simpleTestObject1.increment();
                result.r2 = simpleTestObject1.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject1.getId());
            }
        }
    }

    @JCStressTest
    @Description("Simple test for concurrent protected code execution for one entity with timeout.")
    @Outcome(id = "1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleOneEntityTimeoutSuccessWaitTest {
        private SimpleTestObject simpleTestObject = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(II_Result result)  {
            try {
                simpleTestObjectEntityLocker.tryLockEntityWithTimeout(simpleTestObject.getId(), 10, TimeUnit.MILLISECONDS);
                try {
                    simpleTestObject.increment();
                    result.r1 = simpleTestObject.getValue();
                } finally {
                    simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
                }
            } catch (InterruptedException e) {
                result.r1 = -1;
            }
        }
        @Actor
        public void actor2(II_Result result) {
            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
            try {
                simpleTestObject.increment();
                result.r2 = simpleTestObject.getValue();
            } finally {
                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
            }
        }
    }

    @JCStressTest
    @Description("Simple test for concurrent protected code execution for one entity with timeout.")
    @Outcome(id = "0, 1", expect = ACCEPTABLE, desc = "First actor timeout.")
    @Outcome(id = "1, 0", expect = ACCEPTABLE, desc = "Second actor timeout.")
    @Outcome(id = "1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleOneEntityTimeoutUnSuccessWaitTest {
        private SimpleTestObject simpleTestObject = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(II_Result result)  {
            try {
                if (simpleTestObjectEntityLocker.tryLockEntityWithTimeout(simpleTestObject.getId(), 1, TimeUnit.NANOSECONDS)) {
                    try {
                        simpleTestObject.increment();
                        result.r1 = simpleTestObject.getValue();
                    } finally {
                        simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
                    }
                }
            } catch (InterruptedException e) {
                result.r1 = -42;
            }
        }
        @Actor
        public void actor2(II_Result result) {
            try {
                if (simpleTestObjectEntityLocker.tryLockEntityWithTimeout(simpleTestObject.getId(), 1, TimeUnit.NANOSECONDS)) {
                    try {
                        simpleTestObject.increment();
                        result.r2 = simpleTestObject.getValue();
                    } finally {
                        simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
                    }
                }
            } catch (InterruptedException e) {
                result.r2 = -42;
            }
        }
    }

}
