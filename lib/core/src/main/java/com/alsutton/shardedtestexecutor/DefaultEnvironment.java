package com.alsutton.shardedtestexecutor;

/**
 * The default implementation uses environment variables which
 * can be set by the system.
 */
public class DefaultEnvironment implements Environment {
    private static final String SHARD_COUNT_VARIABLE = "SHARD_COUNT";
    private static final String THIS_SHARD_VARIABLE = "SHARD_ID";

    private final int totalShardCount;
    private final int thisShardNumber;

    public DefaultEnvironment() {
        totalShardCount = getValueFromEnvironment(SHARD_COUNT_VARIABLE);
        thisShardNumber = getValueFromEnvironment(THIS_SHARD_VARIABLE);
    }

    private int getValueFromEnvironment(String variableName) {
        String value = System.getenv(variableName);
        return Integer.parseInt(value);
    }

    @Override
    public int getTotalShardCount() {
        return totalShardCount;
    }

    @Override
    public int getThisShardNumber() {
        return thisShardNumber;
    }
}
