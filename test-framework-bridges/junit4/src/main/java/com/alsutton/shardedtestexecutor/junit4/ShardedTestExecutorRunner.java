package com.alsutton.shardedtestexecutor.junit4;

import com.alsutton.shardedtestexecutor.TestExecutionPredicate;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class ShardedTestExecutorRunner extends BlockJUnit4ClassRunner {
    private final TestExecutionPredicate testExecutionPredicate = new TestExecutionPredicate();
    private final String className;

    public ShardedTestExecutorRunner(Class<?> klass) throws InitializationError {
        super(klass);
        className = klass.getCanonicalName();
    }

    @Override
    public void run(RunNotifier notifier) {
        if (testExecutionPredicate.test(className)) {
            super.run(notifier);
        } else {
            notifier.fireTestIgnored(getDescription());
        }
    }
}
