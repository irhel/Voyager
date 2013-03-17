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

import com.irhad.voyager.core.HTTP;


/**
 * 
 * Interface for the Voyager
 *
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 * 
 */
public interface VoyagerInterface {
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
	public boolean internalLinkDetected(String url);
	
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
	public boolean outsideLinkDetected(String url);
	
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
	public boolean nonUrlLinkDetected(String url);
	
	/**
	 * Save a loaded page
	 * 
	 * @param page 
	 */
	public void saveLoadedPage(HTTP page);

	/**
	 * Called to request that a page be processed.
	 * This page was just downloaded by the spider.
	 * 
	 * Process a loaded page
	 *
	 * @param page page
	 * @param error whether an error occured
	 */
	public void completePage(HTTP page,boolean error);
	
	/**
	 * @return true - whether queuries should be stripped
	 */
	public boolean stripQuery();
	
	/**
	 * When the voyager is finished
	 */
	public void voyagerDone();
}