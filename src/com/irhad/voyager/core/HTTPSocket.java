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
import java.net.URL;
import java.net.UnknownHostException;


/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class HTTPSocket extends HTTP {
        
    synchronized public void lowLevelSend(String url,String post)
    throws UnknownHostException, IOException {
        String command;
        StringBuffer headers;
        int i;
        Attrib a;
        int port=80;
        boolean https = false;
        URL u;
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            if (url.toLowerCase().startsWith("https")) {
                url = "http" + url.substring(5);
                u = new URL(url);
                if (u.getPort()==-1)
                    port=443;
                https = true;
            } else {
                u = new URL(url);
            }
            
            if ( u.getPort()!=-1 )
                port = u.getPort();
            
            // connect
            socket = SocketFactory.getSocket(u.getHost(),port,https);
            
            socket.setSoTimeout(timeout);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            
            if (post == null) command = "GET ";
            else command = "POST ";
            
            String file = u.getFile();
            if (file.length()==0) file="/";
            
            
            command = command + file + " HTTP/1.0";
            SocketFactory.writeString(out,command);
            
            if (post!=null) clientHeaders.set("Content-Length","" + post.length());
            
            clientHeaders.set("Host", u.getHost() );
            
            i=0;
            headers = new StringBuffer();
            do {
                a = clientHeaders.get(i++);
                if ( a!=null ) {
                    headers.append(a.getName());
                    headers.append(": ");
                    headers.append(a.getValue());
                    headers.append("\r\n");
                }
            } while (a!=null);
            
            if (headers.length()>=0) out.write(headers.toString().getBytes() );
            
            SocketFactory.writeString(out,"");
            if ( post!=null ) out.write(post.getBytes());
            
            header.setLength(0);
            int chars = 0;
            boolean done = false;
            
            while (!done) {
                int ch;
                ch = in.read();
                if (ch==-1) done=true;

                switch (ch) {
                    case '\r':
                        break;
                    case '\n':
                        if (chars==0) done = true;
                        chars=0;
                        break;
                    default:
                        chars++;
                        break;
                }
                header.append((char)ch);
            }
            parseHeaders();
            Attrib acl = serverHeaders.get("Content-length");
            int contentLength=0;
            try {
                if (acl!=null) contentLength = Integer.parseInt(acl.getValue());
            } catch ( Exception e ) {}
            
            int max;
            if (maxBodySize!=-1) max = Math.min(maxBodySize,contentLength );
            else max = contentLength;
            
            if (max<1) max=-1;
            
            ByteList byteList = new ByteList();
            byteList.read(in,max);
            body = byteList.detach();
            
            if ( (err>=400) && (err<=599) ) {
                throw new IOException(response);
            }
            
        }
        finally {
            if ( out!=null ) {
                try {
                    out.close();
                } catch ( Exception e ) {}
            }
            if ( in!=null ) {
                try {
                    in.close();
                } catch ( Exception e ) {}
            }
            
            if ( socket!=null ) {
                try {
                    socket.close();
                } catch ( Exception e ) {}
            }
        }
    }
    
    public HTTP copy() {
        return new HTTPSocket();
    }
}
