package com.faro.puzzles;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * This class will provide a random string from the map given the distribution provided.
 * 
 * Room for improvement:
 *  - if it is going to be called a lot, initialize with the map and precompute the distributions.
 *    - store the distributions in a balanced binary search tree and find the floor of the random long.
 *  - refactor to use BigInteger, we can overflow if the distributions are large
 *    - also, could normalize the distribution from (0, 1] and use a double.  
 *      But would still need to make sure we don't overflow when finding total.
 *      
 * @author Kevin Faro
 */
public class RandomWithDistribution {
    private final LongRand rand;
    
    public RandomWithDistribution() {
        this(new Random());
    }
    
    public RandomWithDistribution(Random rand) {
        this(new LongRand(rand));
    }
    
    /*
     * used for unit testing
     */
    RandomWithDistribution(LongRand rand) {
        super();
        this.rand = rand;
    }
    
    /**
     * Return a random string based on the distributions
     * 
     * @param stringDistrobutions
     * @return a random string
     */
    public String pickString(Map<String, Integer> stringDistrobutions) {
        if(stringDistrobutions == null || stringDistrobutions.isEmpty()) {
            throw new IllegalArgumentException("must supply distributions");
        }
        
        //TODO: assuming no overflow, refactor to BigInteger
        long totalPopulation = 0;

        for (Integer population : stringDistrobutions.values()) {
            totalPopulation += population;
        }

        long randValue = rand.nextLong(totalPopulation) + 1;

        for (Entry<String, Integer> entry : stringDistrobutions.entrySet()) {
            randValue -= entry.getValue();

            if (randValue <= 0) {
                return entry.getKey();
            }
        }

        //should never happen
        throw new IllegalArgumentException();
    }
}

/*
 * used for mocking and simplifies the api
 */
class LongRand {
    private final Random rand;
    
    LongRand(Random rand) {
        super();
        this.rand = rand;
    }
    
    /*
     * Adapted from java.util.Random.nextInt(int)
     */
    public long nextLong(long n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");

          if ((n & -n) == n)  // i.e., n is a power of 2
            return ((n * (long)rand.nextInt(31)) >> 31);
        
        long bits, val;
        do {
            bits = (rand.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }
}
