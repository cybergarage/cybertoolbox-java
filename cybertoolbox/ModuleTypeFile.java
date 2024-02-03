/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModuleFile.java
*
******************************************************************/

import java.io.*;

public class ModuleTypeFile extends File {

	StreamTokenizer	mTokenizer;
	
	public ModuleTypeFile(String filename) throws FileNotFoundException {
		super(filename);
		mTokenizer = initializeTokenizer(this);
	}

	public StreamTokenizer initializeTokenizer(File file) throws FileNotFoundException {
		InputStream inputStream = new FileInputStream(file);
		Reader r = new BufferedReader(new InputStreamReader(inputStream));
		StreamTokenizer st = new StreamTokenizer(r);
		st.eolIsSignificant(false);
		st.commentChar('#');
		return st;
	}

	public String nextString() {
		try {
			mTokenizer.nextToken();
			int type = mTokenizer.ttype;
			while (type != mTokenizer.TT_WORD && type != mTokenizer.TT_EOF) {
				mTokenizer.nextToken();
				type = mTokenizer.ttype;
			}
			if (type == mTokenizer.TT_EOF)
				return null;
			else
				return mTokenizer.sval;
		}
		catch (IOException e) {
			return null;
		}
	}

	public void findString(String string) throws IOException {
		String tokenString = nextString();
		while (tokenString.equals(string) == false && tokenString != null) 
			tokenString = nextString();
		if (tokenString == null)
			throw new IOException(string);
	}
	
	public double nextValue() {
		try {
			mTokenizer.nextToken();
			int type = mTokenizer.ttype;
			while (type != mTokenizer.TT_NUMBER && type != mTokenizer.TT_EOF) {
				mTokenizer.nextToken();
				type = mTokenizer.ttype;
			}
			if (type == mTokenizer.TT_EOF)
				return 0.0;
			else
				return mTokenizer.nval;
		}
		catch (IOException e) {
			return 0.0;
		}
	}
};
