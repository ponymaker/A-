package com.alm.test;

import com.alm.impl.EntityLocker;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;

import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;
import static org.openjdk.jcstress.annotations.Expect.FORBIDDEN;

public class EntityGlobalLockerTest {

//    @JCStressTest
//    @Description("Simple test for two locals and one global lock.")
//    @Outcome(id = "1, 2, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "1, 3, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 1, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 3, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
//    @State
//    public static class SimpleOneGlobalTwoLocalEntityLockTest {
//        private SimpleTestObject simpleTestObject = new SimpleTestObject();
//        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
//
//        @Actor
//        public void actor1(III_Result result) {
//            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
//            try {
//                simpleTestObject.increment();
//                result.r1 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
//            }
//        }
//
//        @Actor
//        public void actor2(III_Result result) {
//            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
//            try {
//                simpleTestObject.increment();
//                result.r2 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
//            }
//        }
//
//        @Actor
//        public void actor3(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r3 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//    }
//
//    @JCStressTest
//    @Description("Simple test for two globals and one local lock.")
//    @Outcome(id = "1, 2, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "1, 3, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 1, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 3, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
//    @State
//    public static class SimpleTwoGlobalOneLocalEntityLockTest {
//        private SimpleTestObject simpleTestObject = new SimpleTestObject();
//        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
//        @Actor
//        public void actor1(III_Result result) {
//            simpleTestObjectEntityLocker.lockEntity(simpleTestObject.getId());
//            try {
//                simpleTestObject.increment();
//                result.r1 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.unlockEntity(simpleTestObject.getId());
//            }
//        }
//        @Actor
//        public void actor2(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r2 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//        @Actor
//        public void actor3(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r3 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//    }
//
//    @JCStressTest
//    @Description("Simple test for two globals and one local lock.")
//    @Outcome(id = "1, 2, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "1, 3, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 1, 3", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "2, 3, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(id = "3, 2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
//    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
//    @State
//    public static class SimpleThreeGlobalEntityLockTest {
//        private SimpleTestObject simpleTestObject = new SimpleTestObject();
//        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
//        @Actor
//        public void actor1(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r1 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//        @Actor
//        public void actor2(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r2 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//        @Actor
//        public void actor3(III_Result result) {
//            simpleTestObjectEntityLocker.globalLockEntity();
//            try {
//                simpleTestObject.increment();
//                result.r3 = simpleTestObject.getValue();
//            } finally {
//                simpleTestObjectEntityLocker.globalUnlockEntity();
//            }
//        }
//    }

    @JCStressTest
    @Description("Simple test for two globals and one local lock.")
    @Outcome(id = "1, 2, 3", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "1, 3, 2", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "2, 1, 3", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "2, 3, 1", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "3, 1, 2", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(id = "3, 2, 1", expect = ACCEPTABLE, desc = "Correct increment.")
    @Outcome(expect = FORBIDDEN, desc = "Race occured.")
    @State
    public static class SimpleThreeGlobalEntityLockTest {
        private SimpleTestObject simpleTestObject = new SimpleTestObject();
        private final EntityLocker<Integer> simpleTestObjectEntityLocker = new EntityLocker<>();
        @Actor
        public void actor1(III_Result result) {
            simpleTestObjectEntityLocker.globalLockEntity();
            try {
                simpleTestObject.increment();
                result.r1 = simpleTestObject.getValue();
                simpleTestObjectEntityLocker.globalLockEntity();
                try {
                    simpleTestObject.increment();
                    result.r1 = simpleTestObject.getValue();
                } finally {
                    simpleTestObjectEntityLocker.globalUnlockEntity();
                }
            } finally {
                simpleTestObjectEntityLocker.globalUnlockEntity();
            }
        }
        @Actor
        public void actor2(III_Result result) {
            simpleTestObjectEntityLocker.globalLockEntity();
            try {
                simpleTestObject.increment();
                result.r2 = simpleTestObject.getValue();
            } finally {
                simpleTestObjectEntityLocker.globalUnlockEntity();
            }
        }
        @Actor
        public void actor3(III_Result result) {
            simpleTestObjectEntityLocker.globalLockEntity();
            try {
                simpleTestObject.increment();
                result.r3 = simpleTestObject.getValue();
            } finally {
                simpleTestObjectEntityLocker.globalUnlockEntity();
            }
        }
    }

}
