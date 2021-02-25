package com.alsutton.shardedtestexecutor;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class DefaultEnvironmentTests {
    private static final String TEST_SHARD_COUNT_STRING = "10";
    private static final String TEST_SHARD_ID_STRING = "5";

    @Test
    public void testDetectsUnshardedEnvironment() {
        DefaultEnvironment testEnvironment = new DefaultEnvironment(x -> null);

        assertThat(testEnvironment.getThisShardNumber()).isEqualTo(0);
        assertThat(testEnvironment.getTotalShardCount()).isEqualTo(1);
    }

    @Test
    public void testDetectsShardednvironment() {
        DefaultEnvironment testEnvironment =
                new DefaultEnvironment(new TestEnvironmentSupplier());

        assertThat(testEnvironment.getThisShardNumber()).isEqualTo(5);
        assertThat(testEnvironment.getTotalShardCount()).isEqualTo(10);
    }

    private static class TestEnvironmentSupplier implements Function<String,String> {
        @Override
        public String apply(String value) {
            switch (value) {
                case DefaultEnvironment.SHARD_COUNT_VARIABLE:
                    return TEST_SHARD_COUNT_STRING;
                case DefaultEnvironment.THIS_SHARD_VARIABLE:
                    return TEST_SHARD_ID_STRING;
            }
            fail("Request for unexpected value "+value);
            return null;
        }
    }
}
