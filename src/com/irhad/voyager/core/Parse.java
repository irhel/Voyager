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
class Parse extends AttribList {
	public StringBuffer source;
	protected int idx;
	protected char parseDelim;
	protected String parseName;
	protected String parseValue;
	public String tag;

	void addAttribute() {
		Attrib a = new Attrib(parseName, parseValue,parseDelim);
		add(a);
	}
	String getParseName() {
		return parseName;
	}
	void setParseName(String s) {
		parseName = s;
	}
	String getParseValue() {
		return parseValue;
	}
	void setParseValue(String s) {
		parseValue = s;
	}
	char getParseDelim() {
		return parseDelim;
	}
	void setParseDelim(char s) {
		parseDelim = s;
	}
	
	public static boolean isWhiteSpace(char ch) {
		return( "\t\n\r ".indexOf(ch) != -1 );
	}

	public void eatWhiteSpace() {
		while (!eof()) {
			if (!isWhiteSpace(source.charAt(idx))) return;
			idx++;
		}
	}

	public boolean eof() {
		return(idx>=source.length() );
	}

	public void parseAttributeName() {
		eatWhiteSpace();
		if ((source.charAt(idx)=='\'') || (source.charAt(idx)=='\"')) {
			parseDelim = source.charAt(idx);
			idx++;
			while ( source.charAt(idx)!=parseDelim ) {
				parseName+=source.charAt(idx);
				idx++;
			}
			idx++;
		} else {
			while ( !eof() ) {
				if ( isWhiteSpace(source.charAt(idx)) ||
						(source.charAt(idx)=='=') ||
						(source.charAt(idx)=='>') )
					break;
				parseName+=source.charAt(idx);
				idx++;
			}
		}
		eatWhiteSpace();
	}

	public void parseAttributeValue() {
		if ( parseDelim!=0 ) return;

		if ( source.charAt(idx)=='=' ) {
			idx++;
			eatWhiteSpace();
			if ( (source.charAt(idx)=='\'') || (source.charAt(idx)=='\"') ) {
				parseDelim = source.charAt(idx);
				idx++;
				while ( source.charAt(idx)!=parseDelim ) {
					parseValue+=source.charAt(idx);
					idx++;
				}
				idx++;
			} else {
				while ( !eof() &&
						!isWhiteSpace(source.charAt(idx)) &&
						(source.charAt(idx)!='>') ) {
					parseValue+=source.charAt(idx);
					idx++;
				}
			}
			eatWhiteSpace();
		}
	}
	
}
