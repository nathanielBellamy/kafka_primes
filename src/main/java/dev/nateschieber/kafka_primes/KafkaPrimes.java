package dev.nateschieber.kafka_primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;
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
    Properties producerProps = KafkaPrimes.producerProps();
    PrimeProducer producerArray = new PrimeProducer(ListType.ARRAY_LIST, producerProps); //KafkaPrimes.producerProps());
    PrimeProducer producerLinked = new PrimeProducer(ListType.LINKED_LIST, producerProps); //KafkaPrimes.producerProps());
    PrimeProducer producerVector = new PrimeProducer(ListType.VECTOR, producerProps); //KafkaPrimes.producerProps());

    Properties consumerProps = KafkaPrimes.consumerProps();
    PrimeConsumer consumer = new PrimeConsumer(consumerProps); //KafkaPrimes.consumerProps());

    Thread consumerThread = new Thread(consumer);
    consumerThread.start();

    Thread producerArrayThread = new Thread(producerArray);
    producerArrayThread.start();

    Thread producerLinkedThread = new Thread(producerLinked);
    producerLinkedThread.start();

    Thread producerVectorThread = new Thread(producerVector);
    producerVectorThread.start();

    // Thread[] threads = { consumerThread, producerThread };
  }

  public
  static
  Properties
  producerProps() {
    Properties props = new Properties();

    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "kafka_primes");
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
    props.put("group.id", "kafka_primes");
    props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");

    return props;
  };
}
