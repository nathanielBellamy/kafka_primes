package dev.nateschieber.kafka_primes.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import dev.nateschieber.kafka_primes.enums.AlgorithmType;
import dev.nateschieber.kafka_primes.enums.CollectionType;

public class PrimeAlgorithmBuilder {
  private PrimeAlgorithm alg;

  public
  PrimeAlgorithmBuilder()
  {
    // this.alg = new NaiveAlgorithm();
  }

  public
  PrimeAlgorithmBuilder
  withAlgorithmType(AlgorithmType algorithmType)
  {
    switch (algorithmType) {
      case AlgorithmType.NAIVE -> this.alg = new NaiveAlgorithm();
      default                  -> throw new IllegalArgumentException("Unrecognized Algorithm Type");
    }

    return this;
  }

  public
  PrimeAlgorithmBuilder
  withCollectionType(CollectionType collectionType)
  {
    switch (collectionType) {
        case ARRAY_LIST     -> this.alg.primes = new ArrayList<Integer>();
        case LINKED_LIST    -> this.alg.primes = new LinkedList<Integer>();
        case VECTOR         -> this.alg.primes = new Vector<Integer>();
        default             -> throw new IllegalArgumentException("Unrecognized Collection Type");
    }

    return this;
  }

  public
  PrimeAlgorithm
  build()
  {
    return this.alg;
  }
}
