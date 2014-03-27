package com.faro.puzzles;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class RandomWithDistributionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNull() {
        RandomWithDistribution randDist = new RandomWithDistribution();
        randDist.pickString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmpty() {
        RandomWithDistribution randDist = new RandomWithDistribution();
        randDist.pickString(Collections.<String, Integer>emptyMap());
    }

    @Test
    public void testOneDistribution() {
        RandomWithDistribution randDist = new RandomWithDistribution();

        Map<String, Integer> distributions = new HashMap<String, Integer>();
        distributions.put("a", 1);
        Assert.assertEquals("a", randDist.pickString(distributions));
    }

    @Test
    public void testTwoDistributions() {
        LongRand rand = mock(LongRand.class);
        when(rand.nextLong(anyLong())).thenReturn(0L).thenReturn(1L);

        RandomWithDistribution randDist = new RandomWithDistribution(rand);

        Map<String, Integer> distributions = new HashMap<String, Integer>();
        distributions.put("a", 1);
        distributions.put("z", 1);
        Assert.assertEquals("a", randDist.pickString(distributions));
        Assert.assertEquals("z", randDist.pickString(distributions));
    }

    @Test
    public void testTwoLargeDistributions() {
        LongRand rand = mock(LongRand.class);
        when(rand.nextLong(anyLong()))
          .thenReturn(Integer.valueOf(Integer.MAX_VALUE).longValue()-1L)
          .thenReturn(Integer.valueOf(Integer.MAX_VALUE).longValue());

        RandomWithDistribution randDist = new RandomWithDistribution(rand);

        Map<String, Integer> distributions = new HashMap<String, Integer>();
        distributions.put("a", Integer.MAX_VALUE);
        distributions.put("z", 1);
        Assert.assertEquals("a", randDist.pickString(distributions));
        Assert.assertEquals("z", randDist.pickString(distributions));
    }
    
    @Test
    public void testAZeroDistributions() {
        RandomWithDistribution randDist = new RandomWithDistribution();

        Map<String, Integer> distributions = new HashMap<String, Integer>();
        distributions.put("a", 1);
        distributions.put("z", 0);
        
        /*
         * if it was factoring in 'z', the odds that it would pick 'a' 100 times in a row 
         * is virtually impossible(1/1.26E30).  But at 100 times the test still runs super fast.
         */
        for(int i=0; i<100; i++) {
          Assert.assertEquals("a", randDist.pickString(distributions));
        }
    }
}
