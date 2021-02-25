package com.alsutton.shardedtestexecutor;

import java.util.function.Function;

/**
 * The default implementation uses environment variables which
 * can be set by the system.
 */
public class DefaultEnvironment implements Environment {
    // Visible For Tests
    static final String SHARD_COUNT_VARIABLE = "SHARD_COUNT";
    static final String THIS_SHARD_VARIABLE = "SHARD_ID";

    private final int totalShardCount;
    private final int thisShardNumber;

    public DefaultEnvironment() {
        this(System::getenv);
    }

    DefaultEnvironment(Function<String,String> valueSupplier) {
        String shards = valueSupplier.apply(SHARD_COUNT_VARIABLE);
        String currentShard = valueSupplier.apply(THIS_SHARD_VARIABLE);

        // If both are null we're operating in an unsharded environment
        if (shards == null && currentShard == null) {
            totalShardCount = 1;
            thisShardNumber = 0;
        } else {
            if (shards == null || currentShard == null) {
                throw new IllegalArgumentException("Only one sharding variable was present");
            }

            totalShardCount = Integer.parseInt(shards);
            thisShardNumber = Integer.parseInt(currentShard);
        }
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
