package dev.nateschieber.kafka_primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;
import consumer.PrimeConsumer;
import producer.PrimeProducer;
import kafka_primes.ListType;

@SpringBootApplication
public class KafkaPrimesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPrimesApplication.class, args);

    KafkaPrimesApplication.init();
	}

  private
  static
  void
  init() {
    Properties props = new Properties();

    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "kafka_primes");
    props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");

    PrimeConsumer consumer = new PrimeConsumer(props);
    PrimeProducer producer = new PrimeProducer(ListType.LINKED_LIST, props);

    Thread consumerThread = new Thread(consumer);
    consumerThread.start();

    Thread producerThread = new Thread(producer);
    producerThread.start();
  }
}
