package com.faro.puzzles;

public class TransposedBinarySearch {

    // Given a sorted list that has been transposed
    // that is, a portion has been removed from one end and attached to the
    // other
    // write a function to determine if a given number is present in the list.
    public static boolean isInList(float targetValue, float[] list) {
        if (list == null || list.length == 0) {
            return false;
        }
        return inRange(targetValue, list, 0, list.length - 1);
    }

    private static boolean inRange(float targetValue, float[] list, int start, int end) {
        if (start > end) {
            return false;
        }

        int midIndex = start + (end - start) / 2;

        if (targetValue == list[midIndex]) {
            return true;
        }

        if (isTransposed(targetValue, list, start, end)) {
            // transposed
            if (targetValue > list[end]) {
                return inRange(targetValue, list, start, midIndex - 1);
            } else {
                return inRange(targetValue, list, midIndex + 1, end);
            }
        } else {
            if (targetValue > list[midIndex]) {
                return inRange(targetValue, list, midIndex + 1, end);
            } else {
                return inRange(targetValue, list, start, midIndex - 1);
            }
        }
    }

    private static boolean isTransposed(float targetValue, float[] list, int start, int end) {
        return list[start] > list[end];
    }
}
