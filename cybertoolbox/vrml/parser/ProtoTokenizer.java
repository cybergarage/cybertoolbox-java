/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ProtoTokenizer.java
*
******************************************************************/

package vrml.parser;

import java.io.*;

public class ProtoTokenizer extends StreamTokenizer {

	public ProtoTokenizer(Reader reader) { 
		super(reader);
		initializeTokenizer();
	}

	public void initializeTokenizer() {
		eolIsSignificant(true);
		commentChar('#');
		wordChars ('{', '}');
		wordChars ('[', ']');
		wordChars (',', ',');
		wordChars ('"', '"');
		wordChars ('_', '_');
	}
};
