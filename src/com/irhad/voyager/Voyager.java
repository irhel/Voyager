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
 * spidering. It delegates work to the SpiderWorker class.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton
 * @version 1.0
 */

import com.irhad.voyager.core.HTTP;
import com.irhad.voyager.core.Log;


/**
 *
 * Voyager is an intelligent agent that explores a given website and extracts data regarding how the website is 
 * interconnected with other websites on the web and/or internally. It validates the links that the current website 
 * links to by extracting all the links from the HTML source of the website and checking their availability. 
 * When Voyager finds links, it sends them to a queue, so that when the current page is processed, 
 * Voyager goes on to process the links on the queue.
 * Voyager can also copy the entire content of a given website. 
 * The user can select the number of threads to process a given website and download the pages and the content of the website.
 * If the user selects to enter a world wide voyage mode, then Voyager treats external links as internal and processes them as well.
 * Hypothetically, Voyager can explore the whole world wide web based on the availability of inter connectivity between websites.
 * 
 * 
 * 
 * 
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class Voyager extends Thread
implements VoyagerInterface
{
	protected QueueInterface queueManager;
	protected WebsiteExplorer collection[];
	protected boolean voyageWWW;
	protected VoyagerInterface voyagerManager;
	protected boolean terminated = false;
	protected ProgressReporter isCompleted = new ProgressReporter();
	protected int maxSizeHttpBody;
	public static final int UNLIMITED = -1;
	
	/**
	 * @param manager manager that manages information provided by voyager.
	 * @param url starting url.
	 * @param http handler used by voyager.
	 * @param threadNumber number of threads.
	 */
	public Voyager(VoyagerInterface manager,String url,HTTP http,int threadNumber)
	{
		this(manager,url,http,threadNumber,new QueueManager());
	}
	
	/**
	 *  * @param manager manager that manages information provided by voyager.
     * @param url starting url.
     * @param http handler used by voyager.
     * @param threadNumber number of threads.
	 * @param queueManager 
	 */
	public Voyager(VoyagerInterface manager,String url,HTTP http,int
			threadNumber,QueueInterface queueInterface) {
		voyagerManager = manager;
		voyageWWW = false;
		collection = new WebsiteExplorer[threadNumber];
		for(int i=0;i<collection.length;i++){
			HTTP hc = http.copy();
			collection[i] = new WebsiteExplorer( this,hc );
		}
		queueManager = queueInterface;
		if(url.length()>0) {
			queueManager.clear();
			distributeWork(url);
		}
	}
	
	/**
	 * @return Returns progress reporter
	 */
	public ProgressReporter isVoyagerCompleted() {
		return isCompleted;
	}
	
	/**
	 * Performs work of voyager
	 */
	public void run() {
		if(terminated) return;
		for(int i=0;i<collection.length;i++) collection[i].start();
		try	{
			isCompleted.untilFirstThreadStarts();
			isCompleted.blockUntilCompleted();
			Log.log("Voyager is idle");
			voyagerDone();
			for(int i=0;i<collection.length;i++) {
				collection[i].interrupt();
				collection[i].join();
				collection[i] = null;
			}
		}
		catch(Exception e) {
			Log.logException("Exception occured while initiating voyager", e);
		}
	}
	
	/**
	 * Get links from QueueManager
	 *
	 * @return Returns the next URL.
	 */
	synchronized public String getLinksFromQueues() {
		try {
			while(true)	{
				if(terminated) return null;
				String nextUrl = queueManager.distributeTask();
				if(nextUrl!=null)
					return nextUrl;
				wait();
			}
		}
		catch( java.lang.InterruptedException e) {
		}
		return null;
	}
	
	/**
	 * Add links to respective queues
	 *
	 * @param url The URL to be added to queues.
	 */
	synchronized public void distributeWork(String url)	{
		if(terminated) return;
		queueManager.addQueueManager(url);
		notify();
	}
	
	/**
	 *
	 * @param bonVoyage Whether the voyager should include 
	 * outside links in the search
	 */
	public void setWorldSpider(boolean bonVoyage) {
		voyageWWW = bonVoyage;
	}
	
	/**
	 * @return whether the voyager is done world voyaging (will likely go on forever)s
	 */
	public boolean getWorldSpider()	{
		return voyageWWW;
	}
	
	
	/**
     * Invoked when an internal link is detected.
     * An internal link is considered to be a link 
     * that has the same host as the URL that 
     * the user specified to be explored. 
     *  
     *
     * @param url the url
     * @return boolean whether the link should be processed
     */
	synchronized public boolean internalLinkDetected(String url) {
		if(voyagerManager.internalLinkDetected(url)) distributeWork(url);
		return true;
	}
	
	/**
     * Invoked when an outside link is detected.
     * An outisde link is considered to be a link 
     * that does not have the same host as the URL that 
     * the user specified to be explored. 
     *  
     *
     * @param url the url
     * @return boolean whether the link should be processed
     */
	synchronized public boolean outsideLinkDetected(String url)	{
		if(voyageWWW) {
			internalLinkDetected(url);
			return true;
		}
		if(voyagerManager.outsideLinkDetected(url))	distributeWork(url);
		return true;
	}

	/**
	 * @param page the contents of the HTTP page
	 * @param error whether an error occured while donwloading the page
	 */
	synchronized public void completePage(HTTP page,boolean error) {
		queueManager.taskFinished(page.getURL(),error);
	}
	
	/**
	 * When the voyager is finished
	 * 
	 */
	synchronized public void voyagerDone() {
		voyagerManager.voyagerDone();
	}
	
	/**
	 * Start the termination of the voyager
	 */
	synchronized public void beginTermination()	{
		terminated = true;
		queueManager.clear();
		notifyAll();
	}
	
	/**
	 * @return true if the spider is terminated
	 */
	public boolean isTerminated() {
		return terminated;
	}

	public void setMaxBody(int mx) {
		maxSizeHttpBody = mx;
		for(int i=0;i<collection.length;i++) collection[i].getHTTP().setMaxBody(mx);
	}
	
	/**
	 * @return The max size, or unlimited
	 */
	public int getMaxBody() {
		return maxSizeHttpBody;
	}

	/**
     * Invoked when other link is detected.
     * Other link is considered to be a link 
     * that does not link to another page, 
     * and could be a location of a resource or 
     * mailto link for example. 
     *  
     *
     * @param url the url
     * @return boolean whether the link should be processed
     */
	synchronized public boolean nonUrlLinkDetected(String url) {
		if(voyagerManager.nonUrlLinkDetected(url)) distributeWork(url);
		return true;
	}
	
	/**
     * Save a loaded page
     * 
     * @param page 
     */
	synchronized public void saveLoadedPage(HTTP page) {
		voyagerManager.saveLoadedPage(page); 
	}
	/**
     * @return true - whether queuries should be stripped
     */
	synchronized public boolean stripQuery() {
		return true;
	}

}