package dev.nateschieber.kafka_primes.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import dev.nateschieber.kafka_primes.algorithms.AlgorithmType;
import dev.nateschieber.kafka_primes.algorithms.CollectionType;
import dev.nateschieber.kafka_primes.algorithms.PrimeAlgorithm;

public class PrimeProducer extends KafkaProducer<Integer, Integer> implements Runnable {
    public String topic;
    private PrimeAlgorithm primeAlgorithm;

    public
    PrimeProducer(AlgorithmType algorithmType, CollectionType collectionType, Properties props)
    {
      super(props);
      this.topic = "PRIMES_".concat(collectionType.name());

      this.primeAlgorithm = PrimeAlgorithm.create(algorithmType, collectionType);
    }

    public
    void
    run()
    {
      this.createShutdownHook();

      while (true)
      {
        Integer p = primeAlgorithm.nextPrime();
        // System.out.printf("topic: %s ==== newPrime: %d", this.topic, p);
        this.send(new ProducerRecord<>(this.topic, p, p));
      }
    }

    private
    void
    createShutdownHook()
    {
      PrimeProducer self = this;
      Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          self.close();
        }
      });
    }
}
