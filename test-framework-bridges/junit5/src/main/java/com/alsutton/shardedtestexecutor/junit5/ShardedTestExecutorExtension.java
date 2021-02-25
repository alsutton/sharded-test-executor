package com.alsutton.shardedtestexecutor.junit5;

import com.alsutton.shardedtestexecutor.TestExecutionPredicate;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ShardedTestExecutorExtension implements ExecutionCondition {
    private final TestExecutionPredicate testExecutionPredicate = new TestExecutionPredicate();
    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getCanonicalName();
        if (testExecutionPredicate.test(testClassName)) {
            return ConditionEvaluationResult.enabled(null);
        }

        return ConditionEvaluationResult.disabled("This test will run on another testing shard.");
    }
}
