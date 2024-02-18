package dev.nateschieber.kafka_primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Properties;
import dev.nateschieber.consumers.PrimeConsumer;
import dev.nateschieber.producers.PrimeProducer;
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
    PrimeProducer producer = new PrimeProducer(ListType.LINKED_LIST, props);

    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    PrimeConsumer consumer = new PrimeConsumer(props);

    Thread consumerThread = new Thread(consumer);
    consumerThread.start();

    Thread producerThread = new Thread(producer);
    producerThread.start();

    // Thread[] threads = { consumerThread, producerThread };
  }
}
