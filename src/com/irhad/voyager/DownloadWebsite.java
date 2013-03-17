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

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.irhad.voyager.core.HTTP;
import com.irhad.voyager.core.HTTPSocket;
import com.irhad.voyager.core.Log;
/**
 *
 * Class responsible with downloading HTML files 
 * from a website.
 *
 * @author Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.3
 */
public class DownloadWebsite extends javax.swing.JFrame implements VoyagerInterface {


    private static final long serialVersionUID = 5323611826664754643L;

    /** Main voyager object */
	Voyager voyager = null; 

	int numberPages;

	VoyagerSwing frame;

	/**
	 * Download website constructor used to set
	 * up GUI interface
	 */
	public DownloadWebsite(VoyagerSwing frame) {

		this.frame = frame;

		StartListener startListener = new StartListener();
		frame.startDownloadButton.addActionListener(startListener);
	}



	/**
	 * Used to download a file from a website.
	 *
	 * @param page The current HTTP page
	 */
	protected void downloadPage(HTTP page) {
		try {
			if(frame.pathField.getText().length()>0) {
				int i = page.getURL().lastIndexOf('/');
				if (i != -1) {
					String filename = page.getURL().substring(i);
					if ( filename.equals("/")) filename="index.html";
					FileOutputStream outStream = new FileOutputStream(new File(frame.pathField.getText(),filename));
					outStream.write(page.getBody().getBytes("8859_1"));
					outStream.close();
				}
			}
		} catch(Exception e) {
			Log.logException("Can't save output page: ",e);
		}
	}
	
	/**
	 * 
	 * Initiates download once 
	 * the start button is pressed
	 *
	 * @param event The event
	 */
	void downloadStartAction(java.awt.event.ActionEvent event) {

		QueueInterface wl = new QueueManager();		
		if(voyager!=null) {			
			Runnable doLater = new Runnable() {
				public void run() {
					frame.startDownloadButton.setText("Canceling...");
				}
			};
			SwingUtilities.invokeLater(doLater);
			voyager.beginTermination();
			return;
		}
		try {
			if(frame.downloadUrlField.getText().length()>0) {
				HTTPSocket http = new HTTPSocket();
				http.send(frame.downloadUrlField.getText(),null);
			}
		} catch(Exception e) {
			JOptionPane.showMessageDialog(frame,e,"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Runnable doLater = new Runnable(){
			public void run() {
				frame.startDownloadButton.setText("Cancel");
			}	    
		};
		SwingUtilities.invokeLater(doLater);
		numberPages = 0;
		voyager = new Voyager(this,	frame.downloadUrlField.getText(), new HTTPSocket(), ValidateWebsite.threadNumber, wl);
		voyager.setMaxBody(Voyager.UNLIMITED);
		voyager.start();
	}
	
	/**
	 * Method called when voyager detects an internal link
	 *
	 * @param url The URL of the link that was found
	 * @return True should voyager process this page 
	 */
	public boolean internalLinkDetected(String url) {
		return true;
	}
	/**
	 * Method called when voyager detects an outside link
	 *
	 * @param url The URL of the link that was found
	 * @return True should voyager process this page
	 */
	public boolean outsideLinkDetected(String url) {
		return ValidateWebsite.voyageMode;
	}
	/**
    /**
     * Method called when voyager detects a nonUrlLink link.
     * Links to resources (multimedia files), links to email 
     * for example belong to this category. 
     *
     * @param url The URL of the link that was found
     * @return True should voyager process this page
     */
	public boolean nonUrlLinkDetected(String url) {
		return false;
	}

	/**
	 * Saves the currently loaded page
	 *
	 * @param page The current Http page
	 */
	public void saveLoadedPage(HTTP page) {
		numberPages++;
		RefreshURL ut = new RefreshURL();
		SwingUtilities.invokeLater(ut);
		downloadPage(page);
	}

	public void completePage(HTTP page,boolean error) {
	}
	/**
	 * Should queuries be extracted
	 * @return Returns true if queries should be extracted 
	 */
	public boolean stripQuery() {
		return true;
	}
	/**
	 * Called when the voyager
	 * no longer operates which can be due to the user
	 * terminating the process, or the voyager
	 * having finished his task. 
	 */
	public void voyagerDone() {
		if(voyager.isTerminated()) {
			JOptionPane.showMessageDialog(frame,
					"Download of website has been canceled. " +
					"Check logs for errors.",
					"Done",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(frame,
					"Download of website is complete. " +
					"Check logs for errors.",
					"Done",
					JOptionPane.INFORMATION_MESSAGE);
		}

		voyager = null;

		Runnable doLater = new Runnable(){
			public void run() {
				frame.startDownloadButton.setText("Start download");
			}
		};
		SwingUtilities.invokeLater(doLater);
	}

	class StartListener implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			downloadStartAction(event);
		}
	}

	/**
	 * Notifies the user of the current page being processed
	 *
	 */
	class RefreshURL implements Runnable {
		public void run() {
			frame.pagesLabel.setText( "Pages downloaded: " + numberPages );
		}
	}
}