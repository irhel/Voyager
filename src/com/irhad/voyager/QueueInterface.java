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
/**
 * 
 * Interface that deals with the statuses of detected links
 * and distribution of tasks to the voyager threads
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
*/

public interface QueueInterface {
	
    /** UNKNOWN status */
	public static final char UNKNO = 'U';
	/** waiting status */
	public static final char WAITING = 'W';
	/** finished status */
	public static final char FINISHED = 'C';
	/** hustling status */
	public static final char HUSTLING = 'R';
	/** error status */
	public static final char ERROR = 'E';
	
	public String distributeTask();
	
	public void taskFinished(String url,boolean error);
	
	public void addQueueManager(String url);
	
	public char getURLStatus (String url);
	
	public void clear();
}