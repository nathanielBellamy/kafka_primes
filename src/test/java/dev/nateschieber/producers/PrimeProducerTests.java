package dev.nateschieber.producers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

import dev.nateschieber.kafka_primes.KafkaPrimes;
import dev.nateschieber.producers.ListType;

@SpringBootTest(classes = KafkaPrimes.class)
class PrimeProducerTests {

	@Test
  void producesPrimes() {
    PrimeProducer producer = new PrimeProducer(ListType.ARRAY_LIST, KafkaPrimes.producerProps());

    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(2));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(3));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(5));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(7));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(11));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(13));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(17));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(19));
    assertThat(producer.nextPrime()).isEqualTo(Integer.valueOf(23));
  }

  @Test
  void
  storesPrimesInSpecifiedListType() {
    PrimeProducer producerArray = new PrimeProducer(ListType.ARRAY_LIST, KafkaPrimes.producerProps());
    PrimeProducer producerLinked = new PrimeProducer(ListType.LINKED_LIST, KafkaPrimes.producerProps());
    PrimeProducer producerVector = new PrimeProducer(ListType.VECTOR, KafkaPrimes.producerProps());

    assertThat(producerArray.primes).isInstanceOf(ArrayList.class);
    assertThat(producerLinked.primes).isInstanceOf(LinkedList.class);
    assertThat(producerVector.primes).isInstanceOf(Vector.class);
  }

  @Test
  void
  storesTopicString() {
    PrimeProducer producerArray = new PrimeProducer(ListType.ARRAY_LIST, KafkaPrimes.producerProps());
    PrimeProducer producerLinked = new PrimeProducer(ListType.LINKED_LIST, KafkaPrimes.producerProps());
    PrimeProducer producerVector = new PrimeProducer(ListType.VECTOR, KafkaPrimes.producerProps());

    assertThat(producerArray.topic).isEqualTo("primes-array");
    assertThat(producerLinked.topic).isEqualTo("primes-linked");
    assertThat(producerVector.topic).isEqualTo("primes-vector");
  }
}
