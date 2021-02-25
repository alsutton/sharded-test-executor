package com.alsutton.shardedtestexecutor;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class TestExecutionPredicateTests {
  private static final int TEST_CLASS_COUNT = 1000;

  private static final int TEST_SHARD_COUNT = 5;

  @Test
  public void testAllTestsAreRun() {
    String[] testNames = new String[TEST_CLASS_COUNT];
    for (int i = 0; i < testNames.length; i++) {
      testNames[i] = Integer.toString(i);
    }

    int count = 0;
    for (int i = 0; i < TEST_SHARD_COUNT; i++) {
      count += countTestsForShard(i, testNames);
    }

    assertThat(count).isEqualTo(testNames.length);
  }

  @Test
  public void testSimilarNamesHaveEvenDistribution() {
    String[] testNames = new String[TEST_CLASS_COUNT];
    for (int i = 0; i < testNames.length; i++) {
      testNames[i] = Integer.toString(i);
    }

    int ideal = TEST_CLASS_COUNT / TEST_SHARD_COUNT;
    int tolerance = ideal / 10; // 10% tolerance
    for (int i = 0; i < TEST_SHARD_COUNT; i++) {
      int shardTestCount = countTestsForShard(i, testNames);
      if (shardTestCount < ideal - tolerance || ideal + tolerance < shardTestCount) {
        fail("Test count for shard " + i + " outside of tolerance. Got " + shardTestCount
            + " wanted value in range " + (ideal-tolerance)+ " to " + (ideal+tolerance));
      }
    }
  }

  private int countTestsForShard(int shardNumber, String[] testNames) {
    TestEnvironment environment = new TestEnvironment(shardNumber);
    TestExecutionPredicate testExecutionPredicate =
        new TestExecutionPredicate(new DefaultTestClassHasher(), environment);
    int count = 0;
    for (String testName : testNames) {
      if (testExecutionPredicate.test(testName)) {
        count++;
      }
    }
    return count;
  }

  // Provides an environment to simulate being on a specific shard.
  static class TestEnvironment implements Environment {

    private final int shardNumber;

    TestEnvironment(int shardNumber) {
      this.shardNumber = shardNumber;
    }

    @Override
    public int getTotalShardCount() {
      return TEST_SHARD_COUNT;
    }

    @Override
    public int getThisShardNumber() {
      return shardNumber;
    }
  }
}
