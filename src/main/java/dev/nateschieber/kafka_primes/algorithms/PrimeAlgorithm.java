package dev.nateschieber.kafka_primes.algorithms;

import java.util.ArrayList;
import java.util.Collection;

import dev.nateschieber.kafka_primes.enums.AlgorithmType;
import dev.nateschieber.kafka_primes.enums.CollectionType;

public abstract class PrimeAlgorithm {
  private Integer N = Integer.valueOf(2);
  public Collection<Integer> primes;

  public
  abstract
  Integer
  nextPrime(); // recursively find the next prime

  public
  Integer
  getN()
  {
    return this.N;
  }

  public
  void
  incrN()
  {
    this.N++;
  }
}
