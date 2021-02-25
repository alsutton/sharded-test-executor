package com.alsutton.shardedtestexecutor.example;

import com.alsutton.shardedtestexecutor.junit5.ShardedTestExecutorExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ShardedTestExecutorExtension.class})
public class DummyTestClass5 {
    @Test
    public void dummyTestMethod() {
        assertEquals(1 , 1);
    }
}
