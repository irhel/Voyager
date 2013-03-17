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
package com.irhad.voyager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;


/**
 * This class is responsible for voyaging the links of a website, 
 * deciding which ones should be processed, and then testing the
 * connections to the detected links. 
 * 
 * @author Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class LinksVoyager {
    
    /** Queue of erroneous URLs. */
    protected Collection<URL> errorQueue = new ArrayList<URL>(3);
    
    /** Queue of URLs that are yet to be processed. */
    protected Collection<URL> pendingQueue = new ArrayList<URL>(3);
    
    /** Queue of URLs that are already processed. */
    protected Collection<URL> processedQueue = new ArrayList<URL>(3);
    
    protected LinksVoyagerInterface listener;
    
    /**
     * Whether to cancel the current process or not
     */
    protected boolean cancel = false;
    
    /**
     * @param listener listen to information from the voyager
     */
    public LinksVoyager(LinksVoyagerInterface listener) {
        this.listener = listener;
    }
    
    /**
     * Simple getters and setters
     */
    
    public Collection<URL> getErrorQueue() {
        return errorQueue;
    }
    
    public Collection<URL> getPendingQueue() {
        return pendingQueue;
    }
    
    public Collection<URL> getProcessedQueue() {
        return processedQueue;
    }    
    
    /**
     * Empty the queues
     */
    public void clear() {
        getErrorQueue().clear();
        getPendingQueue().clear();
        getProcessedQueue().clear();
    }
    
    /**
     * A flag to denote cancellation of process
     */
    public void cancel() {
        cancel = true;
    }
    
    /**
     * Append a URL to the pending queue
     * 
     * @param url the url to be appended
     */
    public void appendURL(URL url) {
    	
        if (getPendingQueue().contains(url) || 
        		getErrorQueue().contains(url) || 
        		getProcessedQueue().contains(url)) {
            return;
        }
        getPendingQueue().add(url);
    }
    
    /**
     * Called internally to process a URL.
     * Here the validatin of the URL is performed
     * 
     * @param url The URL that needs to be validated
     */
    public void validateURL(URL url) {
        try {
            URLConnection connection = url.openConnection();
            if ((connection.getContentType()!=null) &&
                                            !connection.getContentType().
                                            toLowerCase().startsWith("text/")) {
                getPendingQueue().remove(url);        
                getProcessedQueue().add(url);    
                return;
            }
            
            InputStream inpStream = connection.getInputStream();
            Reader reader = new InputStreamReader(inpStream);
            HTMLEditorKit.Parser parse = new HTMLParse().getParser();
            parse.parse(reader,new HTMLProcessor(url),true);
        } catch (IOException e) {
            getPendingQueue().remove(url);
            getErrorQueue().add(url);
            listener.erroneousLink(url);
            return;
        }
        // mark URL as complete
        getPendingQueue().remove(url);        
        getProcessedQueue().add(url);
    }
    
    /**
     * Initiates the LinksVoyager
     */
    public void initiateLinksVoyager() {
        cancel = false;
        while (!cancel && !getPendingQueue().isEmpty()) {
            Object list[] = getPendingQueue().toArray();
            for (int i=0;!cancel && (i<list.length);i++ ){
                validateURL((URL)list[i]);
            }
        }
    }
    
       
    public void log(String entry, JTextArea area) {
        area.append((new Date()) + ":" + entry + "\n");
    }
    
    public void logFoundUrl(String entry, JTextArea area) {
        area.append(entry + "\n");
    }
    
    /**
     * A HTML parser callback used by this class to
     * detect links.
     * HTML processor, parses HTMLs to extract the links (href)
     * 
     */
    protected class HTMLProcessor extends HTMLEditorKit.ParserCallback {
        protected URL base;        
        public HTMLProcessor(URL base) {
            this.base = base;
        }
        
        public void handleSimpleTag(HTML.Tag t,
                                        MutableAttributeSet a,int pos) {
            String href = (String)a.getAttribute(HTML.Attribute.HREF);
            
            if((href==null) && (t==HTML.Tag.FRAME)) {
            	href = (String)a.getAttribute(HTML.Attribute.SRC);
            }        
            if (href==null) {
            	return;
            }
            int i = href.indexOf('#');
            if (i!=-1){
            	href = href.substring(0,i);
            }
            if (href.toLowerCase().startsWith("mailto:")) {
                listener.emailLink(href);
                return;
            }
            processLink(base,href);
        }
        
        public void handleStartTag(HTML.Tag t,
                                        MutableAttributeSet a,int pos) {
            handleSimpleTag(t,a,pos);
        }
        
        protected void processLink(URL base,String str) {
            try {
                URL url = new URL(base,str);            
                if (listener.linkDetected(base,url)) appendURL(url);
            } catch ( MalformedURLException e ) {}
        }
    }   
 
}