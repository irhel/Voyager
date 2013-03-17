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

import java.net.MalformedURLException;
import java.net.URL;

import com.irhad.voyager.core.Attrib;
import com.irhad.voyager.core.HTMLParser;
import com.irhad.voyager.core.HTMLTag;
import com.irhad.voyager.core.HTTP;
import com.irhad.voyager.core.Log;
import com.irhad.voyager.core.URLUtility;

/**
 * 
 * Explores websites.
 *
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class WebsiteExplorer extends Thread {

    /** Class members. Variable names are self explanatorys */
	protected String urlToDownload;

	/** Simple getter and setters*/
	
	public String getUrlToDownload() {
        return urlToDownload;
    }

    public void setUrlToDownload(String urlToDownload) {
        this.urlToDownload = urlToDownload;
    }

    public Voyager getVoyager() {
        return voyager;
    }

    public void setVoyager(Voyager voyager) {
        this.voyager = voyager;
    }

    public HTTP getHTTP() {
        return httpObject;
    }

    public void setHttpObject(HTTP httpObject) {
        this.httpObject = httpObject;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }
    protected Voyager voyager;

	protected boolean isBusy;

	protected HTTP httpObject;

	public WebsiteExplorer(Voyager owner,HTTP http)	{
		httpObject = http;
		voyager = owner;
	}

	
	public boolean isBusy()	{
		return isBusy;
	}
	
	/**
	 * Thread goes idle until a url is received from a queue, 
	 * and then handles url.
	 * 
	 */
	public void run() {
		while(true)	{
			urlToDownload = voyager.getLinksFromQueues();
			if(urlToDownload==null)	return;
			voyager.isVoyagerCompleted().operationInitiated();
			processWorkload();
			voyager.isVoyagerCompleted().operationComplete();
		}
	}

	protected void processWorkload(){
		try {
			isBusy = true;
			httpObject.send(urlToDownload,null);
			HTMLParser parse = new HTMLParser();
			parse.source = new StringBuffer(httpObject.getBody());
			voyager.saveLoadedPage(httpObject);
			// find all the links
			while(!parse.eof())
			{
				char ch = parse.get();
				if(ch==0)
				{
					HTMLTag tag = parse.getTag();
					Attrib href = tag.get("HREF");
					if(href==null) continue;
					URL target=null;
					try	{
						target = new URL(new URL(urlToDownload),href.getValue());
					} catch(MalformedURLException e) {
						Log.log("Spider found other link: " + href );
						voyager.nonUrlLinkDetected(href.getValue());
						continue;
					}
					if(voyager.stripQuery()) target = URLUtility.stripQuery(target);
					target = URLUtility.stripAnhcor(target);
					if(target.getHost().equalsIgnoreCase(
							new URL(urlToDownload).getHost())) {
						Log.log("Spider found internal link: " + target.toString() );
						voyager.internalLinkDetected(target.toString());
					}
					else {
						Log.log("Spider found outside link: " + target.toString() );
						voyager.outsideLinkDetected(target.toString());
					}
					voyager.completePage(httpObject,false);
				}
			}
		}
		catch(java.io.IOException e) {
			Log.log("Error loading file("+ urlToDownload +"): " + e );
		}
		catch(Exception e) {
			Log.logException(
					"Exception while processing file("+ urlToDownload +"): ", e );
		}
		finally {
			voyager.completePage(httpObject,true);
			isBusy = false;
		}
	}
}