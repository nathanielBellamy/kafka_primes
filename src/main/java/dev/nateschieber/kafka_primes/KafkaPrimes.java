package dev.nateschieber.kafka_primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import dev.nateschieber.consumers.PrimeConsumer;
import dev.nateschieber.producers.PrimeProducer;
import dev.nateschieber.producers.ListType;

@SpringBootApplication
public class KafkaPrimes {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPrimes.class, args);

    KafkaPrimes.init();
	}

  private
  static
  void
  init() {
    KafkaPrimes.createTopics();

    Properties producerProps = KafkaPrimes.producerProps();
    PrimeProducer producerArray = new PrimeProducer(ListType.ARRAY_LIST, producerProps);
    PrimeProducer producerLinked = new PrimeProducer(ListType.LINKED_LIST, producerProps);
    PrimeProducer producerVector = new PrimeProducer(ListType.VECTOR, producerProps);

    Properties consumerProps = KafkaPrimes.consumerProps();
    PrimeConsumer consumer = new PrimeConsumer(consumerProps);

    Thread consumerThread = new Thread(consumer);
    consumerThread.start();

    Thread producerArrayThread = new Thread(producerArray);
    producerArrayThread.start();

    Thread producerLinkedThread = new Thread(producerLinked);
    producerLinkedThread.start();

    Thread producerVectorThread = new Thread(producerVector);
    producerVectorThread.start();
  }

  private
  static
  void
  createTopics() {
    AdminClient client = AdminClient.create(KafkaPrimes.consumerProps());

    NewTopic arrayTopic = new NewTopic("primes-array", 1, (short)1);
    NewTopic linkedTopic = new NewTopic("primes-linked", 1, (short)1);
    NewTopic vectorTopic = new NewTopic("primes-vector", 1, (short)1);

    List<NewTopic> newTopics = new ArrayList<NewTopic>();
    newTopics.add(arrayTopic);
    newTopics.add(linkedTopic);
    newTopics.add(vectorTopic);

    CreateTopicsResult res = client.createTopics(newTopics);
    try {
      res.all().get(5000, TimeUnit.MILLISECONDS);
    } catch (ExecutionException|InterruptedException|TimeoutException ex) {
      System.out.println("Kafka Topics Create: ERROR");
    }
    client.close();
    System.out.println("Kafka Topics Create: SUCCESS");
  }

  public
  static
  Properties
  producerProps() {
    Properties props = new Properties();

    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "kafka-primes");
    props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

    return props;
  };

  public
  static
  Properties
  consumerProps() {
    Properties props = new Properties();

    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "kafka-primes");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");

    return props;
  };
}
