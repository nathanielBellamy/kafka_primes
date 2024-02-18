package consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class PrimeConsumer extends KafkaConsumer<Integer, Integer> implements Runnable{
    public
    PrimeConsumer(Properties props)
    {
      super(props);
      props.put("enable.auto.commit", "true");
      props.put("auto.commit.interval.ms", "1000");
    }

    public
    void
    run()
    {
        String topic = "primes";

        this.subscribe(Arrays.asList(topic));

        while (true)
        {
          // System.out.printf("consume primes");
          ConsumerRecords<Integer, Integer> records = this.poll(Duration.ofMillis(100));
          records.forEach(record -> {
            System.out.printf("offset = %d, key = %d, value = %d%n", record.offset(), record.key(), record.value());
          });
        }
    }
}
