package com.alsutton.shardedtestexecutor;

/**
 * Provides information about the sharding environment
 * we're running in.
 */
public interface Environment {

    int getTotalShardCount();

    int getThisShardNumber();
}
