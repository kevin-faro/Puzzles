package com.faro.puzzles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * DESCRIPTION: By starting at the top of the triangle and moving to adjacent numbers 
 * 				on the row below, the maximum total from top to bottom is 27.
 * EX:
 *    5
 *   9 6
 *  4 6 8
 * 0 7 1 5
 * 
 * 5 + 9 + 6 + 7 = 27
 * 
 * NOTE: No error checking, I am assuming that the triangle is properly formed.
 */
public class PassTriangle {

	private MaxSumNode root;
	private long maxTotal;
		
	public PassTriangle(final int root) {
		super();
		this.root = new MaxSumNode(root);
		this.maxTotal = root;
	}
	
	/**
	 * Get the max total  of the triangle 
	 * 
	 * @return the max total of the triangle.
	 */
	public long getMaxTotal() {
		return maxTotal;
	}

	/*
	 * Rather than iterating through the list at the end, 
	 * we can just keep track as we go.
	 */
	private void updateMaxTotal(long maxTotalCandidate) {
		maxTotal = Math.max(maxTotal, maxTotalCandidate);
	}
	
	public void processRow(int[] row) {
		MaxSumNode cursor = root;
		
		/*
		 * need to add a left-most node since the only way to get there is to 
		 * traverse down the left side of the tree
		 */
		root = new MaxSumNode(root.maxSum+row[0], root);
		updateMaxTotal(root.maxSum); /* update max total - optimization */
		
		/* 
		 * NOTE: this is the tricky part ... need to update 
		 * the maxSum of this node to be the max of current index 
		 * in the row passed in + the current node or the next node
		 * since we could get to this point in the tree by either node 
		 */
		for(int i=1; i<row.length-1; i++) {
			long l = cursor.maxSum + row[i];
			long r = cursor.next.maxSum + row[i];

			cursor.maxSum = Math.max(l, r);
			updateMaxTotal(cursor.maxSum); /* update max total  - optimization*/
			
			cursor = cursor.next; /* move to next node in list */
		}
		
		/*
		 * update the right-most node since the only way to get there is to 
		 * traverse down the right side of the tree
		 */
		cursor.maxSum += row[row.length-1];
		updateMaxTotal(cursor.maxSum); /* update max total  - optimization*/
		
	}
	
	/**
	 * 
	 * @param args[0] is the file to read in. It is formatted with each level of
	 *            the triangle on a new line and each value is separated by a
	 *            space
	 *            
	 * NOTE: run with test_input/triangle.txt as long as Puzzles is in your classpath
	 * 
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = null;

		try {
			File file = new File(args[0]);
			in = new BufferedReader(new FileReader(file));
			PassTriangle passTri = new PassTriangle(Integer.parseInt(in.readLine().trim()));
			String line;
			int[] arr;
			while ((line = in.readLine()) != null) {
				arr = toIntArray(line.split("\\s"));
				passTri.processRow(arr);
			}

			System.out.println(passTri.getMaxTotal());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

	/*
	 * Convert an array of Integer Strings into an array of ints
	 * 
	 * NOTE: wish java had closures, it would be nice to use something like ...
	 * return map(lambda x: int(x), stringArray) //python
	 */
	private static int[] toIntArray(final String[] stringArray) {
		int[] intArray = new int[stringArray.length];

		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Integer.parseInt(stringArray[i].trim());
		}

		return intArray;
	}
}

/*
 * Inner-class that maintains the linked list of max sums
 */
class MaxSumNode {
	MaxSumNode next = null;
	long maxSum = 0;

	MaxSumNode(long maxSum) {
		this(maxSum, null);
	}
	
	MaxSumNode(long maxSum, MaxSumNode next) {
		super();
		this.maxSum = maxSum;
		this.next = next;
	}
}
