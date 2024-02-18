# Kafka by Doing

```
./gradlew bootRun
```

- produce primes on one thread
- consume them on another





---
## For Reference:
## From https://kafka.apache.org/quickstart:
### To Start a Basic Kafka Instance
> Kafka with ZooKeeper
Run the following commands in order to start all services in the correct order:
# Start the ZooKeeper service
$ bin/zookeeper-server-start.sh config/zookeeper.properties
Open another terminal session and run:
# Start the Kafka broker service
$ bin/kafka-server-start.sh config/server.properties
Once all services have successfully launched, you will have a basic Kafka environment running and ready to use.


### Create a Topic
```
$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
```

### Producer: Write Events to the Topic
```
$ bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
```
This is my first event
This is my second event

### Consumer: Read Events from the Topic

```
$ bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```

## Kafka Connect: Stream Data from Kafka to another Service

### Standalone Mode:
- single process, easy to manage
- not fault tolerant

```
$ bin/connect-standalone.sh config/connect-standalone.properties [connector1.properties connector2.properties ...]
```
