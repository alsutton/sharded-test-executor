# sharded-test-executor

## Overview
As a codebase becomes large there should also be a large number of 
unit tests to go with it.  Trying to run all these tests in a single CI 
run can create a bottle-neck for developer performance, which is where this
sharded test executor is useful.

The sharded test executor provides a very simplistic way of splitting out
large test suites into smaller sets which can be run in parallel on multiple
machines (each known as a shard).

## How it works

The sharded test executor works by creating a hash of the name of the 
test class which is being executed. It then takes that hash, divides it
by the number of shards the tests are being across, and, if the remainder
equals this shards number, it will run the test. If it doesn't then it will
ignore the tests in that class.

The Pseudo-code for this is;

```
int shardCount = 10; // Number of machines tests are running over
int thisShard = 2; // The shard for this machine;  0 <= thisShard < shardCount;

int targetShard = hash(testClassName) % shardCount;
if (targetShard == thisShard) {
    runTest();
}
```

## Bridges to test frameworks

### JUnit 5

You can use JUnit 5's [Automatic Extension Registration](https://junit.org/junit5/docs/current/user-guide/#extensions-registration-automatic) to enable
the sharding system for all tests. This is the mechanism used in the [JUnit 5 example](https://github.com/alsutton/sharded-test-executor/tree/main/examples/junit5)
provided.

### JUnit 4/Robolectric

JUnit4 is less extensible, so you will need to update your classes to say you want to use
the sharded test runner as I've done in the [JUnit 4 example](https://github.com/alsutton/sharded-test-executor/tree/main/examples/junit4).

The [Robolectric Runner](https://github.com/alsutton/sharded-test-executor/tree/main/test-framework-bridges/robolectric) 
has the same requirement because Robolectric uses the JUnit 4 framework for test execution.

## Next Steps

### Bin packing instead of hashing

Hashing can create an uneven test load for two reasons;

* The hashing algorithm doesn't take into account the time and resources disparity of tests.
* The current hashing algorithm isn't guaranteed to provide an even distribution across all test names.

To address both of these I'm going to introduce a bin packing based distribution algorithm.

The [bin packing](https://en.wikipedia.org/wiki/Bin_packing_problem) algorithm will use an in-repo file which 
holds the average test runtimes to determine which shards tests should run on. The average runtimes will be 
used as the *volumes* for a bin packing heuristic to reduce the difference between the complete execution
time of the fastest and slowest shards.

The reason I'm currently planning to use an in-repo file is for reproducibility; having the source of data
for test times in the repo means that for any given commit the data will stay the same. It is possible to 
use an external source which maps commits onto test runtime data, and the system will be developed to 
accommodate this, but it will add significant complexity and so is beyond most folks needs.

The test runtime data can be updated from a regular complete test run against the primary branch (e.g. `main`). 
That way the data doesn't get noise from the branches of folk currently working on things, it doesn't
add to the pull request verification time, and it reduces the churn on the test runtime data.

[This idea is open for discussion](https://github.com/alsutton/sharded-test-executor/issues/3)