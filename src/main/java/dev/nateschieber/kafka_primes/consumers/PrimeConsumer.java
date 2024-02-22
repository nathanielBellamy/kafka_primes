package dev.nateschieber.kafka_primes.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import dev.nateschieber.kafka_primes.algorithms.CollectionType;

public class PrimeConsumer extends KafkaConsumer<Integer, Integer> implements Runnable{
  public HashMap<String, Integer> newest;

  public String leader;

  public
  PrimeConsumer(Properties props)
  {
    super(props);
  }

  public
  void
  run()
  {
    this.createShutdownHook();
    this.subscribeToTopics();

    while (true)
    {
      // System.out.printf("consume primes");
      ConsumerRecords<Integer, Integer> records = this.poll(Duration.ofMillis(100));
      records.forEach(record -> {
        // System.out.printf("topic - %s,offset = %d, key = %d, value = %d%n", record.topic(), record.offset(), record.key(), record.value());
        this.newest.put(record.topic(), record.value());

        System.out.printf(
          "\n array : %d \n linked: %d \n vector: %d \n===\n",
          this.newest.get("PRIMES_ARRAY_LIST"),
          this.newest.get("PRIMES_LINKED_LIST"),
          this.newest.get("PRIMES_VECTOR")
        );
      });
    }
  }

  private
  void
  subscribeToTopics() {
    List<String> topics = Arrays.asList(CollectionType.values())
                                .stream()
                                .map((v) -> "PRIMES_" + v)
                                .collect(Collectors.toList());
    this.subscribe(topics);
  }

  private
  void
  createShutdownHook()
  {
    PrimeConsumer self = this;
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        self.close();
      }
    });
  }
}
