package com.faro.puzzles;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TransposedBinarySearchTest {
 
  @Test
  public void testNull() {
    assertFalse(TransposedBinarySearch.isInList(1F, null));
  }

  @Test
  public void testEmpty() {
    assertFalse(TransposedBinarySearch.isInList(1F, new float[0]));
  }
  
  @Test
  public void testLengthOnePositive() {
    assertTrue(TransposedBinarySearch.isInList(1F, new float[] {1F}));
  }
  
  @Test
  public void testLengthOneNegative() {
    assertFalse(TransposedBinarySearch.isInList(0F, new float[] {1F}));
  }
  
  @Test
  public void testLengthTwoPositive() {
    assertTrue(TransposedBinarySearch.isInList(1F, new float[] {1F, 2F}));
  }
  
  @Test
  public void testLengthTwoNegative() {
    assertFalse(TransposedBinarySearch.isInList(0F, new float[] {1F, 2F}));
  }

  @Test
  public void testTransposedPositive() {
    assertTrue(TransposedBinarySearch.isInList(3F, new float[] {3F, 4F, 1F, 2F}));
  }
  
  @Test
  public void testTransposedNegative() {
    assertFalse(TransposedBinarySearch.isInList(5F, new float[] {3F, 4F, 1F, 2F}));
  }
  
  @Test
  public void stressTest() {
    float[] list = new float[] {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F};
    for(int i=0; i<list.length; i++) {
      assertTrue(TransposedBinarySearch.isInList(5F, list));
      assertFalse(TransposedBinarySearch.isInList(0F, list));
      assertFalse(TransposedBinarySearch.isInList(11F, list));
      rotate(list);
    }
  }
  
  private static void rotate(float[] list) {
    float end = list[list.length - 1];
    System.arraycopy(list, 0, list, 1, list.length - 1);
    list[0] = end;
  }
}
