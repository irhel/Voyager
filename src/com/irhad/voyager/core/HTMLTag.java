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

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class HTMLTag extends AttribList implements Cloneable {
	protected String name;

	public Object clone() {
		int i;
		AttribList rtn = new AttribList();
		for ( i=0;i<attributeVector.size();i++ )
			rtn.add((Attrib)get(i).clone());
		rtn.setName(name);
		return rtn;
	}

	public void setName(String s) {
		name = s;
	}
	public String getName() {
		return name;
	}

	public String getAttributeValue(String name) {
		Attrib a = get(name);
		if (a==null) return null;
		return a.getValue();
	}
}