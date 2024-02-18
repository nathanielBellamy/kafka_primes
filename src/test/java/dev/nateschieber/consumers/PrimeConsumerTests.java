package dev.nateschieber.consumers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import dev.nateschieber.kafka_primes.KafkaPrimes;

@SpringBootTest(classes = KafkaPrimes.class)
class PrimeConsumerTests {

	@Test
  void
  consumesPrimes() {
    // TODO
  }

}
