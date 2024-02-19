# Kafka by Doing
- produce primes on some threads
- consume them on other threads

## To Run:
- from within the directory containing the [Kafka runtime](https://kafka.apache.org/downloads)
  - start zookeeper: `$ bin/zookeeper-server-start.sh config/zookeeper.properties`
  - start kafka:     `$ bin/kafka-server-start.sh config/server.properties`
- run: `$ ./gradlew bootRun`
- test: `$ ./gradlew test`
