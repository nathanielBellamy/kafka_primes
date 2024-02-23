package dev.nateschieber.kafka_primes.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import dev.nateschieber.kafka_primes.KafkaPrimes;
import dev.nateschieber.kafka_primes.enums.AlgorithmType;
import dev.nateschieber.kafka_primes.enums.CollectionType;

@SpringBootTest(classes = KafkaPrimes.class)
class PrimeAlgorithmTests {
	@Test
  void producesTheSamePrimesWithEveryAlgorithm() {
    // TODO: other algorithms
    PrimeAlgorithm algorithm = PrimeAlgorithm.create(
      AlgorithmType.NAIVE,
      CollectionType.ARRAY_LIST
    );

    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(2));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(3));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(5));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(7));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(11));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(13));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(17));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(19));
    assertThat(algorithm.nextPrime()).isEqualTo(Integer.valueOf(23));
  }

  @Test
  void
  storesPrimesInSpecifiedCollectionType() {
    PrimeAlgorithm algorithmArray = PrimeAlgorithm.create(
      AlgorithmType.NAIVE,
      CollectionType.ARRAY_LIST
    );
    PrimeAlgorithm algorithmLinked = PrimeAlgorithm.create(
      AlgorithmType.NAIVE,
      CollectionType.LINKED_LIST
    );
    PrimeAlgorithm algorithmVector = PrimeAlgorithm.create(
      AlgorithmType.NAIVE,
      CollectionType.VECTOR
    );

    assertThat(algorithmArray.primes).isInstanceOf(ArrayList.class);
    assertThat(algorithmLinked.primes).isInstanceOf(LinkedList.class);
    assertThat(algorithmVector.primes).isInstanceOf(Vector.class);
  }
}
