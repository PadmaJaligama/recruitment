/**
 * Copyright 2015, Padma Jaligama, <a title="http://github.com" href="http://github.com">http://github.com</a>.
 * 
 * This software is released under the terms of the
 * GNU LGPL license. See <a title="http://www.gnu.org/licenses/lgpl.html" href="http://www.gnu.org/licenses/lgpl.html">http://www.gnu.org/licenses/lgpl.html</a>
 * for more information.
 * 
 * Usage:  java ProcessFileMain.jar [srcfile] [dstfile]
 *         Open input_data.bin  [srcFile]
 *		   Create output_data.bin [dstFile] and overwrite if it already exists
 *		   Read in each record (32 bit unsigned integer)
 *         For each record:
 *         		Write the record as read
 *         		Write the record converted from big endian to little endian
 *         		Write the record value as denary number in ASCII right justified within 16 bytes
 *  
 */

package com.snapon.recruit.proj;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * <h1>Process Endian files<h1>
 * 
 * This program expects two arguments as input, srcfile and dstfile.
 * 
 * 	Open input_data.bin
 *  Create output_data.bin and overwrite if it already exists.
 *  Reads input_data.bin file and performs following tasks.
 *	Read in each record (32 bit unsigned integer)
 *	<ul>
 *		<li> For each record:</li>
 *		  <ul> 
 *           <li>Write the record as read</li>
 *			 <li>Write the record converted from big endian to little endian</li>
 *			 <li>Write the record value as denary number in ASCII right justified within 16 bytes</li>
 * 	 	  </ul>
 * </ul>
 * 
 * Command Line execution 
 * 		Usage: java ProcessFileMain srcfile dstfile
 * 
 * 
 * @author Padma
 * @version 1.0
 */
public class ProcessFileMain{
	
	// buffer size
	private final int READ_BUFFER=1024;
	
	private String srcFile;
	private String dstFile;
	
	/**
	 * Implemented logger to trace the information
	 */
	private static org.apache.log4j.Logger log = Logger.getLogger(ProcessFileMain.class);
	
	public ProcessFileMain(String[] args){
		if(args.length!=2){
			usage(args);
		}
		srcFile=args[0];
		dstFile=args[1];
	}
	
	
	/**
	 * This main method expects two arguments. 
	 * Source file name and Destination file name.
	 * @param args
	 */
	public static void main(String[] args) {
		
		log.debug("Processing Big Endian file.");
		
		ProcessFileMain processFile=new ProcessFileMain(args);	
		try {
			processFile.convert();			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * This method reads input file record by record
	 * Converts big endian record to little endian
	 * Converts little endian to denary number in ASCII right justified within 16 bytes 
	 * @throws Exception 
	 * 
	 */
	public void convert() throws Exception{
	
		File src = new File(srcFile);		
		//check src file exists
		if(!ProcessDataUtil.isFileExists(src)){
			log.error("No source file exists in the path: " + srcFile);
			throw new Exception("No Source File Exists in the path: " + srcFile);			
		}
		
		FileOutputStream output;
		try{
			//Create output file. If exists overwrite
			output= new FileOutputStream(dstFile, false);
		}catch(Exception e){
			log.error("Exception while creating output file.");
			throw new Exception("Exception while creating output file.");
		}
		
		//Create InputStream
		InputStream is =  new FileInputStream(srcFile);
		DataInputStream dis = new DataInputStream(is);
		
		byte [] b = new byte[READ_BUFFER];
		
		int bytesRead;
		int offset=0;
		try {			
			// Read file in sub sets to handle large files
			while( (bytesRead=dis.read(b, 0, READ_BUFFER)) != -1){
				
				int mod= bytesRead % 4;
				int bytes = mod==0?bytesRead: (bytesRead+(4-mod));
				
				//Split byte array in 4 byte sub array's and convert.
				for(int i=0; i<bytes/4; i++){
					int pos= (i==0?0:i*4-1);
					byte [] record= getBytes(b,pos);					
					writeOutput(output,record);					
				}	
				b=null;
				b=new byte[READ_BUFFER];
				offset+=READ_BUFFER;
			}						
		} catch (Exception e) {
			log.error("Exception while reading input file from path: " + srcFile);
			throw new Exception("Exception while reading input file from path: " + srcFile);
		}finally{
			try {
				is.close();
				output.flush();
				output.close();
			} catch (Exception e) {
				log.error(e.getMessage());
				System.exit(0);
			}
			
		}
	}

	/**
	 * This method extracts 4 bytes from 'b'
	 * @param record
	 * @param offset
	 * @return
	 */
	private byte [] getBytes(byte [] record, int offset){
		byte [] b= new byte[4];
		for(int i=0; i<4;i++){
			b[i]=record[offset+i];
		}
		return b;
	}
	
	/**
	 * This method performs following tasks and writes data to output file
	 * 			Write the record as read from input file
	 * 			Write the record converted from big endian to little endian
	 * 			Write the record value as denary number in ASCII right justified within 16 bytes
	 * @param fos FileOutputStream for output file
	 * @param record  4 bytes read from input file
	 * @throws Exception 
	 */
	private void writeOutput(FileOutputStream fos, byte [] record) throws Exception{	
		
		//Write the record as read from input file
		fos.write(record);
		
		//swap bytes to get Little Endian
		byte [] oppEndian=ProcessDataUtil.oppEndian(record);
		//Write the record converted from big endian to little endian
		fos.write(oppEndian);
		
		//Get Int for Little Endian
		int intvalue=ProcessDataUtil.getLittleEndianInt(oppEndian);		
		//convert to ASCII		
		byte [] asciiByte= ProcessDataUtil.as_string(intvalue, 16);
		
		//Write the record value as denary number in ASCII right justified within 16 bytes
		fos.write(asciiByte);		
		
	}
	
	
	/*
	 * Usage
	 */
	void usage(String[] args){
		if(args[0]==null){
		log.error("Source File Path not specified");
		} else {
			log.error("Destination file path not specified");
		}
		System.exit(0);
	}
}
