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
    String topic;
    List<Integer> primes;

    public
    PrimeProducer(ListType listType, Properties props)
    {
      super(props);

      switch (listType) {
          case ARRAY_LIST     -> { this.primes = new ArrayList<Integer>();     this.topic = "primes_array";  }
          case LINKED_LIST    -> { this.primes = new LinkedList<Integer>();    this.topic = "primes_linked"; }
          case VECTOR         -> { this.primes = new Vector<Integer>();        this.topic = "primes_vector"; }
          default             -> throw new IllegalArgumentException("Unrecognized List Type");
      }
    }

    public
    void
    run()
    {
      primes.add(2);
      this.send(new ProducerRecord<>(this.topic, 2, 2));

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
