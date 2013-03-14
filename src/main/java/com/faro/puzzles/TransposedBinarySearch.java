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

	private static boolean inRange(float targetValue, float[] list, int start,
			int end) {
		if (start > end) {
			return false;
		} else if (start == end) {
			return targetValue == list[start];
		}

		int midIndex = (int) Math.ceil((end - start) / 2.0) + start;
		float mid = list[midIndex];

		if (targetValue == mid) {
			return true;
		} else {
			if (list[start] > list[end]) {
				// transposed
				if (targetValue > mid) {
					if (targetValue <= list[end]) {
						return inRange(targetValue, list, midIndex + 1, end);
					} else {
						return inRange(targetValue, list, start, midIndex - 1);
					}
				} else {
					if (targetValue >= list[start]) {
						return inRange(targetValue, list, start, midIndex - 1);
					} else {
						return inRange(targetValue, list, midIndex + 1, end);
					}
				}
			} else {
				if (targetValue > mid) {
					return inRange(targetValue, list, midIndex + 1, end);
				} else {
					return inRange(targetValue, list, start, midIndex - 1);
				}
			}
		}
	}
}
