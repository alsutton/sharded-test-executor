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

If you wish to use this system with Robolectric you'll need to create a new runner which extends the existing 
Robolectric one and update your `@RunWith` annotations to point to the new runner.