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

package com.irhad.voyager.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class Log {

	private static JTextArea textArea;

	public Log(JTextArea logsTextArea) {
		setTextArea(logsTextArea);
	}

	/**
	 * Log an exception.
	 *
	 * @param event  The text to describe this log event.
	 * @param e      The exception.
	 */
	static public void logException(String event,Exception e) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bos);
		e.printStackTrace(ps);
		ps.close();
		log(event + e + ":" + bos);
		try {
			bos.close();
		} catch ( IOException f ) {}
	}

	synchronized static public void log(String event) {
		Date dt = new Date();
		final String log = "[" + dt.toString() + "] [" + "][" + Thread.currentThread().getName() + "] " + event;
		Runnable rn = new Runnable(){
			@Override
			public void run() {
				textArea.append(log + "\n");
			}
		};
		SwingUtilities.invokeLater(rn);
	}

	public synchronized static void setTextArea(JTextArea logsTextArea) {
		textArea = logsTextArea;
	}
}
