package com.faro.puzzles;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class PassTriangleTest {

	@Test
	public void testRoot() {
		PassTriangle passTri = new PassTriangle(5);
		assertEquals(5, passTri.getMaxTotal());
	}
	
	@Test
	public void testDepth2() {
		PassTriangle passTri = new PassTriangle(5);
		passTri.processRow(new int[]{9,6});
		assertEquals(14, passTri.getMaxTotal());
	}
	
	@Test
	public void testDepth3() {
		PassTriangle passTri = new PassTriangle(5);
		passTri.processRow(new int[]{9,6});
		passTri.processRow(new int[]{4,6,8});
		assertEquals(20, passTri.getMaxTotal());
	}
	
	@Test
	public void testDepth4() {
		PassTriangle passTri = new PassTriangle(5);
		passTri.processRow(new int[]{9,6});
		passTri.processRow(new int[]{4,6,8});
		passTri.processRow(new int[]{0,7,1,5});
		assertEquals(27, passTri.getMaxTotal());
	}
	
	@Test
	public void testDepth5() {
		PassTriangle passTri = new PassTriangle(5);
		passTri.processRow(new int[]{9,6});
		passTri.processRow(new int[]{4,6,8});
		passTri.processRow(new int[]{0,7,1,5});
		passTri.processRow(new int[]{3,6,2,4,1});
		assertEquals(33, passTri.getMaxTotal());
	}
	
	@Test 
	public void testFile() throws Exception {
		BufferedReader in = null;
		
		try {
			in = new BufferedReader(new FileReader("test_input/triangle.txt"));
			PassTriangle passTri = new PassTriangle(Integer.parseInt(in.readLine().trim()));
			String line;
			int[] arr;
			
			while((line=in.readLine()) != null) {
				arr = toIntArray(line.split("\\s"));
				passTri.processRow(arr);
			}
						
			assertEquals(732506, passTri.getMaxTotal());
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					//ignore
				}
			}
		}
	}
	
	private static int[] toIntArray(String[] stringArray) {
		int[] intArray = new int[stringArray.length];
		
		for(int i=0; i<stringArray.length; i++) {
			intArray[i] = Integer.parseInt(stringArray[i].trim());
		}
		
		return intArray;
	}
	
}
