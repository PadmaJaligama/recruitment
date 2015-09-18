package com.snapon.recruit.proj;


import java.io.File;
import java.util.Arrays;



/**
 * <h1>Process Data Util<h1>
 * 
 * @author  Padma
 * @version 1.0
 */

public class ProcessDataUtil {
	
	/**
	 *  This method is used to swap endian-ness of some bytes
	 *  @param b This is Byte Array either in big endian or little endian format
	 *  @return result This returns byte Array after swap
	 *  @exception If input Byte Array is null throws Exception
	 */
	@SuppressWarnings("unused")
	protected static byte[] oppEndian(byte[] b) throws Exception {
		byte[] result = new byte[b.length];
		if(b == null) throw new Exception("Cannot be null");
		for(int i = 0; i < b.length; i++) {
			// swap the bytes
			result[(result.length-1) - i] = b[i];
		}
		// return the swapped bytes
		return result;
	}
	
	/**
	 * This method is used to create ASCII bytes for string of length l
	 * Denary number in ASCII right justified within 16 bytes
	 * @param n Input int arg
	 * @param l number of byte in ASCII
	 * @return ASCII bytes will be return on length l
	 */	
	public static byte[] as_string(int n, int l){
		byte[] Result = new byte[l];
		Arrays.fill(Result, (byte)0);
		String s = Integer.toString(n);
		byte[] bytes = s.getBytes();
		
		for(int i = 0; i <bytes.length ; i++){
			leftShift(Result);						 
			Result[Result.length-1] = (byte)bytes[i];				
		}	
		
		return Result;
	}
	/**
	 * This method is used shit Result Array to left to right justified within 16 bytes
	 * @param Result
	 */
	private static void leftShift(byte [] Result){
		//left shift
		byte first = Result[0]; 
		for( int index =1; index <= Result.length-1 ; index++ )
		 		Result[index-1] = Result [index];
		
		 Result[Result.length-1] = first;
	}
	
	// test the above code
	private static boolean testString() {
		byte[] EXPECTED = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,49,50};
		
		byte[] actual = as_string(12, 16);
		
		for(int i = 0; i < actual.length; i ++) {
			System.out.println("R: " + actual[i]);
		}
		
		for(int i = 0; i < EXPECTED.length; i ++) {
			
			if (i >= actual.length)
				return true;
			
			if (EXPECTED[i] != actual[i])
			{
				throw new AssertionError("Did not passed");
			}
		}
		System.out.println("Passed");
		return Boolean.TRUE;
	}
	
	/**
	 * This method is used to convert little endian byte array to Int
	 * @param b byte array
	 */
	static int getLittleEndianInt(byte [] b){
		return java.nio.ByteBuffer.wrap(b).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 *  This method is used to check if given file exists or not.
	 *  @param f File 
	 */
	static boolean isFileExists(File f){
		return f.exists();		
	}	
}
