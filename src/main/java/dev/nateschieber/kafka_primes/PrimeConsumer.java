package consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class PrimeConsumer extends Thread implements Runnable{
    public
    void
    run() {
        String topic = "primes";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "testGroup");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");

        Consumer<Integer, Integer> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        while (true)
        {
          // System.out.printf("consume primes");
          ConsumerRecords<Integer, Integer> records = consumer.poll(Duration.ofMillis(100));
          records.forEach(record -> {
            System.out.printf("offset = %d, key = %d, value = %d%n", record.offset(), record.key(), record.value());
          });
        }
    }
}
