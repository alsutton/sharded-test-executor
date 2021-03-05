package com.alsutton.shardedtestexecutor.robolectric;

import com.alsutton.shardedtestexecutor.TestExecutionPredicate;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

public class ShardedTestExecutorRobolectricTestRunner extends RobolectricTestRunner {
    private final TestExecutionPredicate testExecutionPredicate = new TestExecutionPredicate();
    private final String className;

    public ShardedTestExecutorRobolectricTestRunner(Class<?> klass) throws InitializationError {
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
