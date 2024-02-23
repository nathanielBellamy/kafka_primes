package dev.nateschieber.kafka_primes.producers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import dev.nateschieber.kafka_primes.KafkaPrimes;
import dev.nateschieber.kafka_primes.enums.AlgorithmType;
import dev.nateschieber.kafka_primes.enums.CollectionType;

@SpringBootTest(classes = KafkaPrimes.class)
class PrimeProducerTests {

  @Test
  void
  storesTopicString() {
    PrimeProducer producerArray = new PrimeProducer(
      AlgorithmType.NAIVE,
      CollectionType.ARRAY_LIST,
      KafkaPrimes.producerProps()
    );
    PrimeProducer producerLinked = new PrimeProducer(
      AlgorithmType.NAIVE,
      CollectionType.LINKED_LIST,
      KafkaPrimes.producerProps()
    );
    PrimeProducer producerVector = new PrimeProducer(
      AlgorithmType.NAIVE,
      CollectionType.VECTOR,
      KafkaPrimes.producerProps()
    );

    assertThat(producerArray.topic).isEqualTo("PRIMES_ARRAY_LIST");
    assertThat(producerLinked.topic).isEqualTo("PRIMES_LINKED_LIST");
    assertThat(producerVector.topic).isEqualTo("PRIMES_VECTOR");

    producerArray.close();
    producerLinked.close();
    producerVector.close();
  }
}
