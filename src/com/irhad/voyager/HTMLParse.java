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
import javax.swing.text.html.HTMLEditorKit;

/**
 * To overcome visibility issues of the getParser method
 *
 * @author Irhad Elezovikj
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class HTMLParse extends HTMLEditorKit {

	private static final long serialVersionUID = 7229212159340098398L;

	public HTMLEditorKit.Parser getParser() {
        return super.getParser();
    }
}
