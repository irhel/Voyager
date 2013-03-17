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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class SocketFactory {

	private static String proxyHost;
	private static int proxyPort;
	private static String proxyUID;
	private static String proxyPWD;

	public static void setProxyHost(String proxyHost) {
		SocketFactory.proxyHost = proxyHost;
	}
	public static void setProxyUID(String proxyUID) {
		SocketFactory.proxyUID = proxyUID;
	}
	public static void setProxyPWD(String proxyPWD) {
		SocketFactory.proxyPWD = proxyPWD;
	}
	public static void setProxyPort(int id) {
		SocketFactory.proxyPort = id;
	}
	public static String getProxyHost() {
		return proxyHost;
	}
	public static String getProxyUID() {
		return proxyUID;
	}
	public static String getProxyPWD() {
		return proxyPWD;
	}
	public static int getProxyPort() {
		return proxyPort;
	}
	public static boolean useProxy() {
		return( (proxyHost!=null) && (proxyHost.length()>0) );
	}
	
	public static void writeString(OutputStream out,String str) throws java.io.IOException {
		out.write( str.getBytes() );
		out.write( "\r\n".getBytes() );
	}

	public static String receive(InputStream in) throws IOException {
		String result = "";
		boolean done = false;

		while (!done) {
			int ch = in.read();
			switch (ch) {
			case 13:
				break;
			case 10:
				done=true;
				break;
			default:
				result+=(char)ch;
			}
		}
		return result;
	}    


	public static Socket getSocket(String host,int port,boolean https) throws IOException {
		Socket socket;

		if( useProxy() ) {
			Log.log("Connection to: " + proxyHost+"(" + proxyPort + ")" );
			try {
				socket = new Socket(proxyHost,proxyPort);
			}
			catch(IOException e) {
				throw new IOException("Proxy connect failed: " + e.getMessage() );
			}
		}
		else {
			socket = new Socket(host,port);
		}
		return socket;
	}
}
