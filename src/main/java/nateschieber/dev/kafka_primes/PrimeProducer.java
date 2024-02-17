package prime_consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import kafka_primes.ListType;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class PrimeProducer extends KafkaProducer {
    static List primes;

    public
    PrimeProducer(ListType listType, Properties props)
    {
      super(props);

      switch (listType) {
          case ListType.ARRAY_LIST     -> this.primes = new ArrayList<Integer>();
          case ListType.LINKED_LIST    -> this.primes = new LinkedList<Integer>();
          default                      -> throw new IllegalArgumentException("Unrecognized List Type");
      }
    }

    public
    static
    void
    main(String[] args)
    {
      String topic = "primes";
      Properties props = new Properties();

      props.put("bootstrap.servers", "localhost:9092");
      props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

      Producer<Integer, Integer> producer = new PrimeProducer(ListType.LINKED_LIST, props);

      primes.add(2);
      producer.send(new ProducerRecord<>(topic, 2, 2));

      int i = 3;
      while (true)
      {
        boolean isPrime = true;
        Iterator<Integer> j = primes.iterator();
        while (j.hasNext())
        {
          if (i % j.next() == 0) {
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

      // producer.close();
    }
}
