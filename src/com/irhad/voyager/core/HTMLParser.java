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
public class HTMLParser extends Parse {
	
	public HTMLTag getTag() {
		int i;

		HTMLTag tag = new HTMLTag();
		tag.setName(this.tag);

		for ( i=0;i<attributeVector.size();i++ )
			tag.add((Attrib)get(i).clone());
		return tag;
	}

	public String buildTag() {
		String buffer="<";
		buffer+=tag;
		int i=0;
		while (get(i)!=null) {
			buffer+=" ";
			if ( get(i).getValue() == null ) {
				if ( get(i).getDelimiter()!=0 )
					buffer+=get(i).getDelimiter();
				buffer+=get(i).getName();
				if ( get(i).getDelimiter()!=0 )
					buffer+=get(i).getDelimiter();
			} else {
				buffer+=get(i).getName();
				if ( get(i).getValue()!=null ) {
					buffer+="=";
					if ( get(i).getDelimiter()!=0 )
						buffer+=get(i).getDelimiter();
					buffer+=get(i).getValue();
					if ( get(i).getDelimiter()!=0 )
						buffer+=get(i).getDelimiter();
				}
			}
			i++;
		}
		buffer+=">";
		return buffer;
	}

	protected void parseTag() {
		idx++;
		tag="";
		clear();

		if ((source.charAt(idx)=='!') &&
				(source.charAt(idx+1)=='-')&&
				(source.charAt(idx+2)=='-') ) {
			while (!eof()) {
				if ((source.charAt(idx)=='-') &&
						(source.charAt(idx+1)=='-')&&
						(source.charAt(idx+2)=='>'))
					break;
				if (source.charAt(idx)!='\r') tag+=source.charAt(idx);
				idx++;
			}
			tag+="--";
			idx+=3;
			parseDelim=0;
			return;
		}

		while (!eof()) {
			if (isWhiteSpace(source.charAt(idx)) || (source.charAt(idx)=='>') )
				break;
			tag+=source.charAt(idx);
			idx++;
		}
		eatWhiteSpace();
		while (source.charAt(idx)!='>') {
			parseName = "";
			parseValue = "";
			parseDelim=0;
			parseAttributeName();
			if(eof()) break;
			if ( source.charAt(idx)=='>' ) {
				addAttribute();
				break;
			}
			parseAttributeValue();
			addAttribute();
		}
		idx++;
	}

	public char get() {
		if (source.charAt(idx)=='<') {
			char ch=Character.toUpperCase(source.charAt(idx+1));
			if ((ch>='A') && (ch<='Z') || (ch=='!') || (ch=='/')) {
				parseTag();
				return 0;
			} else {
				return(source.charAt(idx++));
			}
		} else {
			return(source.charAt(idx++));
		}
	}
}