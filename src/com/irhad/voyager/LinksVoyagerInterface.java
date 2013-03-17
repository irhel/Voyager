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
import java.net.URL;

/**
 * Interface to listen to info from a voyager
 */
interface LinksVoyagerInterface {

  /**
   * Invoked when voyager detects a link
   * 
   * @param host The page that the URL was found on.
   * @param url detected url
   * @return True should the voyager process this link
   */
  public boolean linkDetected(URL host,URL url);

  /**
   * Invoked when voyager gets a failure when 
   * connecting to url
   * 
   * @param url erroneous url
   */
  public void erroneousLink(URL url);

  /**
   * email link detected
   * 
   * @param email email address
   */
  public void emailLink(String email);
}