import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public enum ListType { ARRAY_LIST, LINKED_LIST }

public class PrimeProducer {
    static List primes;

    public PrimeProducer(ListType listType) {
        return switch (listType) {
            case ARRAY_LIST     -> primes = new ArrayList<Integer>();
            case LINKED_LIST    -> primes = new LinkedList<Integer>();
            default             -> throw new IllegalArgumentException("Unrecognized List Type");
        }
    }

    public void main(String[] args) {
        String topic = "primes";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        Producer<Integer, Integer> producer = new KafkaProducer<>(props);

        primes.add(2);
        producer.send(new ProducerRecord<>(topic, 2, 2));

        int i = 3;
        while (true)
        {
          boolean isPrime = true;
          for (int j = 0; j < primes.size(); j++)
          {
            if (i % primes.get(j) == 0) {
              isPrime = false;
              break;
            }
          }

          if (isPrime)
          {
            primes.add(i);
            i++;
            producer.send(new ProducerRecord<>(topic, i, i));
          }
        }

        producer.close();
    }
}
