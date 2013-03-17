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

import java.util.Vector;

/**
 * @author Irhad Elezovikj 
 * Teacher: Nahit Yilmaz
 * Project Mentors: Elchin Asgarov, Semir Elezovikj
 * @version 1.0
 */
public class AttribList extends Attrib implements Cloneable {

	protected Vector<Attrib> attributeVector;

	public Object clone() {
		AttribList attributeList = new AttribList();

		for (int i=0;i<attributeVector.size();i++ ){
			attributeList.add( (Attrib)get(i).clone() );
		}
		return attributeList;
	}

	public AttribList() {
		super("","");
		attributeVector = new Vector<Attrib>();
	}

	synchronized public Attrib get(int id) {
		if (id<attributeVector.size()) return(Attrib)attributeVector.elementAt(id);
		else return null;
	}

	synchronized public Attrib get(String id) {
		int i=0;
		while (get(i)!=null) {
			if (get(i).getName().equalsIgnoreCase(id)) return get(i);
			i++;
		}
		return null;
	}
	
	synchronized public void add(Attrib a) {
		attributeVector.addElement(a);
	}

	synchronized public void clear() {
		attributeVector.removeAllElements();
	}

	synchronized public boolean isEmpty() {
		return( attributeVector.size()<=0);
	}

	synchronized public int length() {
		return attributeVector.size();
	}

	synchronized public void set(String name,String value) {
		if (name==null) return;
		if (value==null) value="";
		Attrib a = get(name);
		if (a==null) {
			a = new Attrib(name,value);
			add(a);
		} else {
			a.setValue(value);
		}
	}
}
