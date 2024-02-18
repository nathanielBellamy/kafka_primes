package dev.nateschieber.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;

public class PrimeProducer extends KafkaProducer<Integer, Integer> implements Runnable {
    List<Integer> primes;

    public
    PrimeProducer(ListType listType, Properties props)
    {
      super(props);

      switch (listType) {
          case ARRAY_LIST     -> this.primes = new ArrayList<Integer>();
          case LINKED_LIST    -> this.primes = new LinkedList<Integer>();
          case VECTOR         -> this.primes = new Vector<Integer>();
          default             -> throw new IllegalArgumentException("Unrecognized List Type");
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
