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
      PrimeConsumer self = this;
      Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          self.close();
        }
      });

      this.subscribe(Arrays.asList("primes-array", "primes-linked", "primes-vector"));

      while (true)
      {
        // System.out.printf("consume primes");
        ConsumerRecords<Integer, Integer> records = this.poll(Duration.ofMillis(100));
        records.forEach(record -> {
          // System.out.printf("topic - %s,offset = %d, key = %d, value = %d%n", record.topic(), record.offset(), record.key(), record.value());
          switch (record.topic()) {
            case "primes-array"   -> this.newestArrayPrime = record.value();
            case "primes-linked"  -> this.newestLinkedPrime = record.value();
            case "primes-vector"  -> this.newestVectorPrime = record.value();
          }

          System.out.printf(
            "\n array : %d \n linked: %d \n vector: %d \n===\n",
            this.newestArrayPrime,
            this.newestLinkedPrime,
            this.newestVectorPrime
          );
        });
      }
    }
}
