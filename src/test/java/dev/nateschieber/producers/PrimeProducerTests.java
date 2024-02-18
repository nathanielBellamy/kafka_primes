package dev.nateschieber.producers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.LinkedList;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

import dev.nateschieber.kafka_primes.KafkaPrimes;
import dev.nateschieber.producers.ListType;

@SpringBootTest(classes = KafkaPrimes.class)
class PrimeProducerTests {

	@Test
  void producesPrimes() {
    // TODO
  }

  @Test
  void
  storesPrimesInSpecifiedListType() {
    PrimeProducer producerLinked = new PrimeProducer(ListType.LINKED_LIST, KafkaPrimes.producerProps());
    PrimeProducer producerArray = new PrimeProducer(ListType.ARRAY_LIST, KafkaPrimes.producerProps());

    assertThat(producerLinked.primes.size()).isEqualTo(0);
    assertThat(producerLinked.primes).isInstanceOf(LinkedList.class);
    assertThat(producerArray.primes).isInstanceOf(ArrayList.class);
  }

}
