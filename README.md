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

```java
int shardCount = 10; // Number of machines tests are running over
int thisShard = 2; // The shard for this machine;  0 <= thisShard < shardCount;

int targetShard = hash(testClassName) % shardCount;
if (targetShard == thisShard) {
    runTest();
}
```