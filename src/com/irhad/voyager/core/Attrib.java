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
public class Attrib implements Cloneable {

	private String name;
	private String value;

	private char delimiter;

	/* Simple getters and setter */
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	public char getDelimiter() {
		return delimiter;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setDelimiter(char ch) {
		delimiter = ch;
	}
	public Object clone() {
		return new Attrib(name,value,delimiter);
	}

	public Attrib(String name,String value,char delim) {
		this.name = name;
		this.value = value;
		this.delimiter = delim;
	}

	public Attrib() {
		this("", "", (char)0);
	}

	public Attrib(String name,String value) {
		this(name, value, (char)0);
	}
}
