package dev.nateschieber.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;

public class PrimeProducer extends KafkaProducer<Integer, Integer> implements Runnable {
    public String topic;
    private Integer currentI = Integer.valueOf(2);
    public List<Integer> primes;

    public
    PrimeProducer(ListType listType, Properties props)
    {
      super(props);

      switch (listType) {
          case ARRAY_LIST     -> { this.primes = new ArrayList<Integer>();     this.topic = "primes-array";  }
          case LINKED_LIST    -> { this.primes = new LinkedList<Integer>();    this.topic = "primes-linked"; }
          case VECTOR         -> { this.primes = new Vector<Integer>();        this.topic = "primes-vector"; }
          default             -> throw new IllegalArgumentException("Unrecognized List Type");
      }
    }

    public
    void
    run()
    {
      PrimeProducer self = this;
      Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          self.close();
        }
      });

      while (true)
      {
        Integer p = this.nextPrime();
        // System.out.printf("topic: %s ==== newPrime: %d", this.topic, p);
        this.send(new ProducerRecord<>(this.topic, p, p));
      }
    }

    public
    Integer
    nextPrime()
    {
      boolean isPrime = true;
      Iterator<Integer> j = this.primes.iterator();
      while (j.hasNext())
      {
        if (this.currentI % j.next() == 0)
        {
          isPrime = false;
          break;
        }
      }

      if (isPrime)
      {
        int newPrime = this.currentI;
        this.primes.add(newPrime);
        this.currentI++;
        return newPrime;
      }
      else
      {
        this.currentI++;
        return this.nextPrime(); // recursion
      }
    }
}
