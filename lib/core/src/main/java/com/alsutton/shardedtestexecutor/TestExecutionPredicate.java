package com.alsutton.shardedtestexecutor;

import java.util.function.Predicate;

public class TestExecutionPredicate implements Predicate<String> {
    private TestClassHasher classHasher;
    private Environment environment;

    public TestExecutionPredicate() {
        classHasher = new DefaultTestClassHasher();
        environment = new DefaultEnvironment();
    }

    // For testing
    TestExecutionPredicate(TestClassHasher classHasher, Environment environment) {
        this.classHasher = classHasher;
        this.environment = environment;
    }

    @Override
    public boolean test(String testClassName) {
        int classHash = Math.abs(classHasher.hash(testClassName));
        int targetShard = classHash % environment.getTotalShardCount();
        return environment.getThisShardNumber() == targetShard;
    }
}
