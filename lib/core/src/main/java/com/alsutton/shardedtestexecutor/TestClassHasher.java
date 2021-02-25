package com.alsutton.shardedtestexecutor;

/**
 * Creates an integer hash value from a given test class name
 */
public interface TestClassHasher {
    int hash(String testClassName);
}
