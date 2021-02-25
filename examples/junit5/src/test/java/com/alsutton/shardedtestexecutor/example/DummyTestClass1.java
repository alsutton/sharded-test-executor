package com.alsutton.shardedtestexecutor.example;

import com.alsutton.shardedtestexecutor.DefaultEnvironment;
import com.alsutton.shardedtestexecutor.junit5.ShardedTestExecutorExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({ShardedTestExecutorExtension.class})
public class DummyTestClass1 {
  @Test
  public void dummyTestMethod() {
    System.out.println(
        "Running "
            + this.getClass().getName()
            + " on shard #"
            + new DefaultEnvironment().getThisShardNumber());
    assertEquals(1, 1);
  }
}
