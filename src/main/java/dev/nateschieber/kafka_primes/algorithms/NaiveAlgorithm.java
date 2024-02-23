package dev.nateschieber.kafka_primes.algorithms;

import java.util.Iterator;

public class NaiveAlgorithm extends PrimeAlgorithm {
  public
  Integer
  nextPrime() // recursive method
  {
    boolean isPrime = true;
    Iterator<Integer> j = this.primes.iterator();
    while (j.hasNext())
    {
      if (this.getN() % j.next() == 0)
      {
        isPrime = false;
        break;
      }
    }

    if (isPrime)
    {
      int newPrime = this.getN();
      this.primes.add(newPrime);
      this.incrN();
      return newPrime;
    }
    else
    {
      this.incrN();
      return this.nextPrime(); // recursion
    }
  }
}
