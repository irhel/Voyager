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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Manages the queues responsible for storing URLs. 
 * 
 *
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class QueueManager implements QueueInterface {
	
    
    /** table of processed URLs */
	Hashtable<String, String> processedTable = new Hashtable<String, String>();

	/** list of pending URLs */
	Vector<String> pendingQueue = new Vector<String>();

	/** list of URLs */
	Vector<String> runningQueue = new Vector<String>();
	
	/**
	 * Add url to pendinqQueue
	 * 
	 * @param url The URL to be added.
	 */
	synchronized public void addQueueManager(String url){
		if (getURLStatus(url) != QueueInterface.UNKNO) return;
		pendingQueue.addElement(url);
	}
	
	/**
	 * Get the state of a URL.
	 *
	 * @param url returns the status
	 */
	synchronized public char getURLStatus(String url){
		if (processedTable.get(url)!=null)return FINISHED;
		if (pendingQueue.size()>0) 
			for (Enumeration<String> e = pendingQueue.elements() ;e.hasMoreElements();) {
				if (((String)e.nextElement()).equals(url))
					return WAITING;
			}

		if (runningQueue.size()>0)
			for ( Enumeration<String> e = runningQueue.elements();e.hasMoreElements();) {
				if (((String)e.nextElement()).equals(url) )
					return HUSTLING;
			}
		return UNKNO;
	}
	
	/**
	 * Distribute a URL to the respective queue
	 *
	 * @return The URL that was distributed
	 */
	synchronized public String distributeTask()	{
		if (pendingQueue.size()<1) return null;
		String firstElement = (String)pendingQueue.firstElement();
		if ( firstElement!=null ) {
			pendingQueue.remove(firstElement);
			runningQueue.addElement(firstElement);
		}
		return firstElement;
	}
	
	
	/**
	 * Denotes the current URL as either succesfully completed
	 * or erroneous
	 *
	 * @param url The URL to complete.
	 * @param error the state of url, true if completed, false if erroneous
	 */
	synchronized public void taskFinished(String url,boolean error)	{
		
		if ( runningQueue.size()>0 ) {
			for ( Enumeration<String> e = runningQueue.elements() ;e.hasMoreElements(); ) {
				String nextElement = (String)e.nextElement();
				if ( nextElement.equals(url) ) {
					runningQueue.remove(nextElement);
					if (error) processedTable.put(nextElement,"e");
					else processedTable.put(nextElement,"c");
					return;
				}
			}
		}
	}
	
	synchronized public void clear() {
		pendingQueue.clear();
		processedTable.clear();
		runningQueue.clear();
	}
}