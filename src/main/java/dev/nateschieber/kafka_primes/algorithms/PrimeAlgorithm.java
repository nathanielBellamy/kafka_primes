package dev.nateschieber.kafka_primes.algorithms;

import java.util.Collection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import dev.nateschieber.kafka_primes.enums.AlgorithmType;
import dev.nateschieber.kafka_primes.enums.CollectionType;

public abstract class PrimeAlgorithm {
  private Integer N = Integer.valueOf(2);
  public Collection<Integer> primes;

  public
  static
  PrimeAlgorithm
  create(AlgorithmType algorithmType, CollectionType collectionType)
  {
    PrimeAlgorithm alg;

    switch (algorithmType) {
      case AlgorithmType.NAIVE -> alg = new NaiveAlgorithm();
      default                  -> throw new IllegalArgumentException("Unrecognized Algorithm Type");
    }

    alg.setCollectionType(collectionType);
    return alg;
  }

  protected
  void
  setCollectionType(CollectionType collectionType)
  {
    switch (collectionType) {
        case ARRAY_LIST     -> this.primes = new ArrayList<Integer>();
        case LINKED_LIST    -> this.primes = new LinkedList<Integer>();
        case VECTOR         -> this.primes = new Vector<Integer>();
        default             -> throw new IllegalArgumentException("Unrecognized Collection Type");
    }
  }

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
