package dev.nateschieber.consumers;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class PrimeConsumer extends KafkaConsumer<Integer, Integer> implements Runnable{
    public int newestArrayPrime  = -1;
    public int newestLinkedPrime = -1;
    public int newestVectorPrime = -1;

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
        this.subscribe(Arrays.asList("primes_array", "primes_linked", "primes_vector"));

        while (true)
        {
          // System.out.printf("consume primes");
          ConsumerRecords<Integer, Integer> records = this.poll(Duration.ofMillis(100));
          records.forEach(record -> {
            // System.out.printf("topic - %s,offset = %d, key = %d, value = %d%n", record.topic(), record.offset(), record.key(), record.value());
            switch (record.topic()) {
              case "primes_array"   -> this.newestArrayPrime = record.value();
              case "primes_linked"  -> this.newestLinkedPrime = record.value();
              case "primes_vector"  -> this.newestVectorPrime = record.value();
            }

            System.out.printf(
              "array: %d \nlinked: %d \nvector: %d \n===\n",
              this.newestArrayPrime,
              this.newestLinkedPrime,
              this.newestVectorPrime
            );
          });
        }
    }
}
