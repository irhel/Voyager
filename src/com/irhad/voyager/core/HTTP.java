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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.UnknownHostException;


/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public abstract class HTTP {

	protected byte body[];
	protected StringBuffer header = new StringBuffer();
	protected String url;
	protected boolean autoRedirect = true;
	protected AttribList clientHeaders = new AttribList();
	protected AttribList serverHeaders = new AttribList();
	protected String response;
	protected int timeout = 30000;
	protected String referrer = null;
	protected String agent = "Firefox/3.0.5";
	protected int maxBodySize = -1;
	protected String rootURL;
	protected int err;

	public abstract HTTP copy();
	protected abstract void lowLevelSend(String url,String post) throws UnknownHostException, IOException;

	public String getBody(){
		return new String(body);
	}
	public String getBody(String enc) throws UnsupportedEncodingException {
		return new String(body,enc);
	}
	public byte[] getBodyBytes() {
		return body;
	}
	public String getURL() {
		return url;
	}
	public void setURL(String u) {
		url = u;
	}
	public void SetAutoRedirect(boolean b) {
		autoRedirect = b;
	}
	public AttribList getClientHeaders() {
		return clientHeaders;
	}
	public AttribList getServerHeaders() {
		return serverHeaders;
	}
	public void setTimeout(int i) {
		timeout = i;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setMaxBody(int i) {
		maxBodySize = i;
	}
	public int getMaxBody() {
		return maxBodySize;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String a) {
		agent = a;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String p) {
		referrer = p;
	}
	public String getRootURL() {
		return rootURL;
	}
	public String getContentType() {
		Attrib a = serverHeaders.get("Content-Type");
		if(a==null) return null;
		else return a.getValue();
	}
	
	public void send(String requestedURL,String post) throws UnknownHostException,IOException {
		rootURL = requestedURL;
		setURL(requestedURL);
		if (clientHeaders.get("referrer")==null)
			if (referrer!=null) clientHeaders.set("referrer",referrer.toString());
		if ( clientHeaders.get("Accept")==null )
			clientHeaders.set("Accept","image/gif,"
					+ "image/x-xbitmap,image/jpeg, "
					+ "image/pjpeg, application/vnd.ms-excel,"
					+ "application/msword,"
					+ "application/vnd.ms-powerpoint, */*");

		if (clientHeaders.get("Accept-Language")==null) clientHeaders.set("Accept-Language","en-us");
		if (clientHeaders.get("User-Agent")==null) clientHeaders.set("User-Agent",agent);

		while ( true ) {
			lowLevelSend(this.url,post);
			Attrib a = serverHeaders.get("Location");
			if ((a==null) || !autoRedirect) {
				referrer = getURL();
				return;
			}

			URL u = new URL(new URL(url),a.getValue());
			setURL(u.toString());
		}
	}

	protected void processResponse(String name) throws java.io.IOException {
		int i1,i2;
		response = name;
		i1 = response.indexOf(' ');
		if (i1!=-1) {
			i2 = response.indexOf(' ',i1+1);
			if (i2!=-1) {
				try {
					err=Integer.parseInt(response.substring(i1+1,i2));
				} catch ( Exception e ) {}
			}
		}
	}

	protected void parseHeaders() throws java.io.IOException {
		boolean first = true;
		String name,value;
		int l;

		serverHeaders.clear();
		name = "";

		String parse = new String(header);

		for (l=0;l<parse.length();l++) {
			if (parse.charAt(l)=='\n') {
				if (name.length()==0) {
					return;
				}
				else {
					if (first) {
						first = false;
						processResponse(name);
					} else {
						int ptr=name.indexOf(':');
						if ( ptr!=-1 ) {
							value = name.substring(ptr+1).trim();
							name = name.substring(0,ptr);
							Attrib a = new Attrib(name,value);
							serverHeaders.add(a);
						}
					}
				}
				name = "";
			} else if ( parse.charAt(l)!='\r' )
				name+=parse.charAt(l);
		}
	}
}
