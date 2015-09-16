package com.snapon.recruit.proj;

import java.util.*;
import com.snapon.recruit.*;
import java.util.Arrays;

/**
 * @author AUTHOR
 */
public class ProcessDataUtil {
	
	// swap endian-ness of some bytes
	@SuppressWarnings("unused")
	static byte[] oppEndian(byte[] b) throws Exception {
		byte[] result = new byte[b.length];
		if(b == null) throw new Exception("Cannot be null");
		for(int i = 0; i < b.length; i++) {
			// swap the bytes
			result[result.length - i] = b[i];
		}
		// return the swapped bytes
		return result;
	}
	
	// create ASCII bytes for string of length "l"
	public byte[] as_string(int n, int l) {
		byte[] Result = new byte[16];
		Arrays.fill(Result, (byte)0);
		String s = Integer.toString(n);
		byte[] bytes = s.getBytes();
		for(int i = 0; i < bytes.length; i++){
			Result[i] = (byte)bytes[i];
		}
		return Result;
	}
	
	// test the above code
	public static boolean testString() {
		byte[] EXPECTED = new byte[]{0,0,0,0,0,0,00,0,0,0,0,0,0,0,31,32};
		ProcessDataUtil test = new ProcessDataUtil();
		byte[] actual = test.as_string(12, 16);
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
}
