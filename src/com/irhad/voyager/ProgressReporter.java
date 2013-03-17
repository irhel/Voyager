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
 * Keep track of the voyager progress
 *
 * @author Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
class ProgressReporter
{
	/**
	 * Number of voyager threads operating
	 */
	private int numberThreads = 0;
	
	/**
	 * Whether the first thread has started operating
	 */
	private boolean firstThreadStarted = false;
	
	/**
	 * Block the current thread until voyager is completed
	 */
	synchronized public void blockUntilCompleted() {
		try {
			while(numberThreads>0) wait();
		}
		catch(InterruptedException e){}
	}
	
	/**
	 * Called when a thread is complete with his task. 
	 * Number of operating threads is decreased
	 */
	synchronized public void operationComplete() {
		numberThreads--;
		notify();
	}
	
	/**
	 * Reverse to initial state
	 */
	synchronized public void reinitiate(){
		numberThreads = 0;
	}
	
	
	/**
	 * Wait until the first thread is started
	 */
	synchronized public void untilFirstThreadStarts()
	{
		try{
			while(!firstThreadStarted) wait();
		}
		catch(InterruptedException e){}
	}
	
	/**
	 * Called when a thread starts working on a task. 
	 * Number of operating threads is increased
	 */
	synchronized public void operationInitiated() {
		numberThreads++;
		firstThreadStarted = true;
		notify();
	}
	
}