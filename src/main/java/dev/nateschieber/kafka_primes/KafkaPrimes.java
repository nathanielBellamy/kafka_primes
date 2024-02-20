package dev.nateschieber.kafka_primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.Iterator;
import java.lang.InterruptedException;

import dev.nateschieber.consumers.PrimeConsumer;
import dev.nateschieber.producers.PrimeProducer;
import dev.nateschieber.producers.ListType;

@SpringBootApplication
public class KafkaPrimes {

	public
  static
  void
  main(String[] args)
  {
		SpringApplication.run(KafkaPrimes.class, args);

    KafkaPrimes.init();
	}

  private
  static
  void
  init()
  {
    KafkaPrimes.createTopics();
    List<Thread> threads = KafkaPrimes.createThreads();
    KafkaPrimes.createShutdownHandler(threads);
  }

  private
  static
  void
  createTopics() {
    AdminClient client = AdminClient.create(KafkaPrimes.consumerProps());

    KafkaPrimes.createTopicIfDoesNotExist("primes-array", client);
    KafkaPrimes.createTopicIfDoesNotExist("primes-linked", client);
    KafkaPrimes.createTopicIfDoesNotExist("primes-vector", client);

    client.close();
    System.out.println("Kafka Topics Create: SUCCESS");
  }

  private
  static
  List<Thread>
  createThreads() {
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

    List<Thread> threads = Arrays.asList(
      consumerThread,
      producerArrayThread,
      producerLinkedThread,
      producerVectorThread
    );

    return threads;
  }

  private
  static
  void
  createShutdownHandler(List<Thread> threads)
  {
    Thread shutdownHandler = new Thread(() -> {
      System.out.println("\n Terminating KafkaPrimes...");
      Iterator<Thread> i = threads.iterator();
      while (i.hasNext())
      {
        try {
          i.next().join();
        } catch (InterruptedException ex) {
          System.out.println("\n An issue occured joining threads.");
        }
      }
      System.out.println("\n All threads joined.");
    });

    Runtime.getRuntime().addShutdownHook(shutdownHandler);
  }

  private
  static
  void
  createTopicIfDoesNotExist(String topic, AdminClient client)
  {
    ListTopicsResult listTopics = client.listTopics();
    try {
      Set<String> names = listTopics.names().get();
      boolean contains = names.contains(topic);
      if (!contains)
      {
        NewTopic newTopic = new NewTopic(topic, 1, (short) 1);
        CreateTopicsResult res = client.createTopics(Arrays.asList(newTopic));
        try {
          res.all().get(1000, TimeUnit.MILLISECONDS);
        } catch (ExecutionException|InterruptedException|TimeoutException ex) {
          System.out.printf("ERROR creating topic: %s", topic);
          throw new Error(ex);
        }
      }
    } catch (ExecutionException|InterruptedException|Error e) {
      e.printStackTrace();
      System.exit(1);
    }
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
