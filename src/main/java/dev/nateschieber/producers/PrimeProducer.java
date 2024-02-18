package dev.nateschieber.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import kafka_primes.ListType;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

public class PrimeProducer extends KafkaProducer implements Runnable {
    static List<Integer> primes;

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
    void
    run()
    {
      String topic = "primes";

      primes.add(2);
      this.send(new ProducerRecord<>(topic, 2, 2));

      int i = 3;
      while (true)
      {
        // System.out.printf("produce prime");
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
          this.send(new ProducerRecord<>(topic, i, i));
        }

        i++;
      }

      // producer.close();
    }
}
