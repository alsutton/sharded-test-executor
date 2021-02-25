package com.alsutton.shardedtestexecutor.example;

import com.alsutton.shardedtestexecutor.DefaultEnvironment;
import com.alsutton.shardedtestexecutor.junit4.ShardedTestExecutorRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(ShardedTestExecutorRunner.class)
public class DummyTestClass4 {
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
