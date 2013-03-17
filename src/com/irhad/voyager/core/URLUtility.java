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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class URLUtility {

	public static String indexFile = "index.html";

	private URLUtility() {}

	public static URL stripQuery(URL url) throws MalformedURLException {
		String file = url.getFile();
		int i=file.indexOf("?");
		if ( i==-1 ) return url;
		file = file.substring(0,i);
		return new URL(url.getProtocol(),url.getHost(),url.getPort(),file);
	}

	public static URL stripAnhcor(URL url) throws MalformedURLException {
		String file = url.getFile();
		return new URL(url.getProtocol(),url.getHost(),url.getPort(),file);
	}

	public static String convertFilename(String base,String path,boolean mkdir) {
		String result = base;
		int index1;
		int index2;
		if( result.charAt(result.length()-1)!=File.separatorChar ){
			result = result+File.separator;
		}
		int queryIndex = path.indexOf("?");
		if(queryIndex!=-1 ) path = path.substring(0,queryIndex);
		int lastSlash = path.lastIndexOf(File.separatorChar);
		int lastDot = path.lastIndexOf('.');
		if( path.charAt(path.length()-1)!='/' ) {
			if(lastSlash>lastDot) path+="/"+indexFile;
		}
		lastSlash = path.lastIndexOf('/');
		String filename = "";
		if(lastSlash!=-1) {
			filename=path.substring(1+lastSlash);
			path = path.substring(0,1+lastSlash);
			if(filename.equals("")) filename=indexFile;
		}
		
		index1 = 1;
		do {
			index2 = path.indexOf('/',index1);

			if(index2!=-1) {
				String dirpart = path.substring(index1,index2);
				result+=dirpart;
				result+=File.separator;

				if(mkdir) {
					File f = new File(result);
					f.mkdir();
				}
				index1 = index2+1; 
			}
		} while(index2!=-1);

		result+=filename;
		return result;
	}  
}
