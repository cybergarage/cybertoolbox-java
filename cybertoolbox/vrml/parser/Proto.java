/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PROTO.java
*
******************************************************************/

package vrml.parser;

import java.io.*;

import vrml.util.*;

public class Proto extends LinkedListNode {

	public Proto(String name) {
		setHeaderFlag(false);
		setName(name);
	}
	
	////////////////////////////////////////////////
	//	Name
	////////////////////////////////////////////////

	protected String	mName;

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	////////////////////////////////////////////////
	//	Parameter
	////////////////////////////////////////////////

	protected ProtoParameterList	mParameterList = new ProtoParameterList();

	public ProtoParameterList getParameterList() {
		return mParameterList;
	}
	
	public void addParameter(String type, String name, String defalutValue) {
		mParameterList.addParameter(type, name, defalutValue);
	}

	public ProtoParameter getParameters() {
		return mParameterList.getParameters();
	}
	
	public ProtoParameter getParameter(String name) {
		return mParameterList.getParameter(name);
	}

	////////////////////////////////////////////////
	//	Token Buffer
	////////////////////////////////////////////////

	protected ProtoStream	mProtoStream = new ProtoStream();

	public void addToken(String token) {
		mProtoStream.addToken(token);
	}

	public void addToken(double token) {
		mProtoStream.addToken(token);
	}

	public ProtoStream getTokenStream() {
		return mProtoStream;
	}
	
	////////////////////////////////////////////////
	//	Parameter Value
	////////////////////////////////////////////////

	public String getParameterString(ProtoParameterList paramList, String name) {
		String value = null;
		if (paramList != null) { 
			ProtoParameter param = paramList.getParameter(name);
			if (param != null)
				value = param.getValue();
		}
		if (value == null) {
			ProtoParameter param = getParameter(name);
			if (param != null)
				value = param.getValue();
		}
		return value;
	}

	public String getString(ProtoParameterList paramList) throws IOException {
		ProtoStream protoStream = getTokenStream();
		protoStream.rewind();
		Reader r = new BufferedReader(new InputStreamReader(protoStream));
		ProtoTokenizer stream = new ProtoTokenizer(r);

		StringBuffer protoString = new StringBuffer();
		
		int nToken = 0;
		while (stream.nextToken() != StreamTokenizer.TT_EOF) {
			switch (stream.ttype) {
			case stream.TT_NUMBER:
				protoString.append(stream.nval + " ");
				nToken++;
				break;
			case stream.TT_WORD:
				if (stream.sval.compareTo("IS") == 0) {
					stream.nextToken();
					if (stream.ttype == stream.TT_WORD) {
						String param = getParameterString(paramList, stream.sval);
						if (param != null)
							protoString.append(param + " ");
					}
				}
				else
					protoString.append(stream.sval + " ");
				nToken++;
				break;
			case stream.TT_EOL:
				if (0 < nToken)
					protoString.append("\n");
				nToken = 0;
				break;
			}
		}
		
		return protoString.toString();
	}
	 
	////////////////////////////////////////////////
	//	Next node 
	////////////////////////////////////////////////

	public Proto next() {
		return (Proto)getNextNode();
	}
	
	////////////////////////////////////////////////
	//	toString
	////////////////////////////////////////////////

	public String toString() {
		StringBuffer protoString = new StringBuffer();
		protoString.append(getName() + " ");
		protoString.append("[");
		for (ProtoParameter param=getParameters(); param != null; param=param.next()) {
			protoString.append(param.getType() + " ");
			protoString.append(param.getName() + " ");
			protoString.append(param.getValue() + " ");
		}
		protoString.append("]\n");
		protoString.append("{");
		protoString.append(getTokenStream().getTokenBuffer() + "\n");
		protoString.append("}\n");
		
		return protoString.toString();
	}
}
