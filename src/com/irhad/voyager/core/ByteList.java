/*
 * 
 * Voyager - Bon Voyage through the World Wide Web
 * 
 * Version: 1.0
 * Build id: I21707987-2009
 *
 * (c) Copyright Voyager contributors and mentors, 2009.  All rights reserved.
 * 
 * Student: Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project mentors: Elchin Asgarov, Semir Elezovikj
 * 
 */
package com.irhad.voyager.core;

import java.io.*;

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class ByteList {

	public static final int INITIAL_SIZE = 32768;

	private byte buffer[] = new byte[INITIAL_SIZE];
	private int index = 0;

	public byte[] detach() {
		byte result[] = new byte[index];
		System.arraycopy(buffer,0,result,0,index);
		buffer = null;
		index = 0;
		return result;
	}

	private int capacity() {
		return(buffer.length-index);
	}

	private void expand() {
		byte newbuffer[] = new byte[buffer.length*2];
		System.arraycopy(buffer,0,newbuffer,0,index);
		buffer = newbuffer;
	}

	public void append(byte buffer[]) {        
		System.arraycopy(buffer,0,this.buffer,index,buffer.length);
	}

	public void read(InputStream in,int max) throws IOException {       
		long length = 0;

		do {
			int size;
			if (max!=-1) size = Math.min(capacity(),max);
			else size = capacity();
			length = in.read(buffer,index,capacity());
			if (length<0) break;
			index+=length;
			if (capacity()<10) expand();
			if ( max!=-1 ) {
				max-=length;
				if (max<=0) break;
			}
		} while (length!=0);
	}
}
