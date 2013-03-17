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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.irhad.voyager.core.Log;

/**
*  @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class ValidateWebsite extends javax.swing.JFrame implements Runnable,LinksVoyagerInterface {
    
	private static final long serialVersionUID = -3778799498155855083L;
	
    /** The background voyager thread. */
    protected Thread backgroundThread;
    
    protected LinksVoyager linksVoyager;
    
    /** Starting url */
    protected URL startingURL;
    
    /** Erroneous counter */
    protected int erroneousCounter = 0;
    
    /** successful hits */
    protected int successfullHits = 0; 


	/** the number of threads working on the downloading of a website */
    public static int threadNumber = 25;
    
    /** boolean to denote whether outside links should be processed as well */    
    public static boolean voyageMode = false;
    
    VoyagerSwing frame = new VoyagerSwing();
    
    public ValidateWebsite() {
    	frame.showFrame();
        frame.startButton.addActionListener(new actionListener());
        
        Log.setTextArea(frame.logsTextArea);
    }
    
	public static void main(String args[]) {
        ValidateWebsite links = new ValidateWebsite();
        DownloadWebsite getSite = new DownloadWebsite(links.frame);
    }
    
    /**
     * Performs the correct action when start or stop is pressed
     * 
     * @param event the event for respective buttons
     */
    void performOnAction(java.awt.event.ActionEvent event) {
        if (backgroundThread==null) {
            frame.startButton.setText("Stop");
            frame.urlField.setEditable(false);
            backgroundThread = new Thread(this);
            backgroundThread.start();
            successfullHits=0;
            erroneousCounter=0;
        } else {
            linksVoyager.cancel();
            frame.startButton.setText("Start");
            frame.urlField.setEditable(true);
        }
    }
    
    /**
     * Start background thread
     */
    public void run() {
        try {
            frame.badText.setText("");
            linksVoyager = new LinksVoyager(this);
            linksVoyager.clear();
            startingURL = new URL(frame.urlField.getText());
            linksVoyager.appendURL(startingURL);
            linksVoyager.initiateLinksVoyager();
            
            Runnable doLater = new Runnable() {
                public void run() {
                    frame.startButton.setText("Start");
                    frame.urlField.setEditable(true);
                    JOptionPane.showMessageDialog(frame,
        					"Checking of links complete",
        					"Done!",
        					JOptionPane.INFORMATION_MESSAGE);
                }
            };
            SwingUtilities.invokeLater(doLater);
            backgroundThread=null;
            
        } catch ( MalformedURLException e ) {
            GuiErrorsRefresh err = new GuiErrorsRefresh();
            err.msg = "Bad address.";
            SwingUtilities.invokeLater(err);          
        }
    }
    
    /**
     * Validate links
     * 
     * @param base the page on which link was found
     * @param url link
     */
    public boolean linkDetected(URL base,URL url) {
        GuiStatisticsRefresh cs = new GuiStatisticsRefresh();
        cs.msg = url.toString();
        SwingUtilities.invokeLater(cs);
        
        if (!testConnection(url)) {
            GuiErrorsRefresh err = new GuiErrorsRefresh();
            err.msg = url+"(on page " + base + ")\n";
            Log.log("Found bad url:" + url);
            erroneousCounter++;
            SwingUtilities.invokeLater(err);          
            return false;
        }

        Log.log("Found good url:" + url);
        successfullHits++;
        if (voyageMode || url.getHost().equalsIgnoreCase(base.getHost()))
            return true;
        else return false;
    }
    /**
     * Erroneous url detected
     * 
     * @param url erroneous url
     */
    public void erroneousLink(URL url) {
    }
    
    /**
     * Test a connection to a url
     * 
     * @param url the link that needs to be checked
     * @return True if the link was good, false otherwise.
     */
    protected boolean testConnection(URL url) {
        try {
            URLConnection connection = url.openConnection();
            connection.connect();         
            return true;
        } catch ( IOException e ) {
            return false;
        }
    }
    
    /**
     * Email link detected
     *; 
     * @param email the email address
     */
    public void emailLink(String email) {
    }
    
    class GuiErrorsRefresh implements Runnable {
        public String msg;
        public void run() {
            frame.badText.append(msg);
            frame.badLinks.setText("Erroneous links detected: " + erroneousCounter);
        }
    }
    
    class GuiStatisticsRefresh implements Runnable {
        public String msg;
        public void run() {
            frame.currentlyProcessing.setText("Currently Processing: " + msg );
            Log.log("Currently Processing: " + msg );
            frame.goodLink.setText("Successful hits: " + successfullHits);            
            frame.badLinks.setText("Erroneous links detected: " + erroneousCounter);
        }
    }
    
    class actionListener implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent event) {
            performOnAction(event);
        }
    }
    
}
