/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringSetValue.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringSetValue extends Script {

	Node		node;
	SFString	nodeValue;
	boolean		bExecution;
	String		inValueString;
	int			nData;

    public void initialize () {

		SFNode nodeField	= (SFNode)getField("SYSTEM_FIELD_MODULE_TARGET_NODE");
		if (nodeField != null) {
			node = (Node)nodeField.getValue();
			if (node != null)
				nodeValue = (SFString)node.getExposedField("description");
			else
				nodeValue = null;
		}
		else {
			node = null;
			nodeValue = null;
		}

		inValueString	= null;
   		bExecution		= true;
		nData			= 0;
	 }

    public void processEvent (Event e){

		if (nodeValue == null)
			return;

		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
			else {
				try {
					Double dvalue = new Double(value);
					nData = dvalue.intValue();
				}
				catch (NumberFormatException exp) {}
			}
		}

		if (e.getName().equals("value") == true) 
			inValueString = ((ConstSFString)e.getValue()).getValue();

		if (bExecution == true) { 
			if (0 < inValueString.length())
				setData(nData, inValueString);
		}
    }

	//////////////////////////////////////////
	//	Array Value
	//////////////////////////////////////////

	private char tokenChar = ':';

	public int getNData() {
		if (nodeValue == null)
			return 0;
		
		String	value = nodeValue.getValue();
		if (value == null)
			return 0;

		int		nData = 0;
		for (int n=0; n<value.length(); n++) {
			if (value.charAt(n) == tokenChar)
				nData++;
		}	

		return (nData+1);
	}

	public String getData(int dataNum) {
		if (nodeValue == null)
			return null;
			
		String	value = nodeValue.getValue();
		if (value == null)
			return null;

		int		n;
		
		int index1 = 0;
		for (n=0; n<dataNum; n++) {
			index1 = value.indexOf(tokenChar, index1);
			if (index1 <= -1)
				break;
			index1++; // Set index1 at next ':' position
		}

		int index2 = 0;
		for (n=0; n<(dataNum+1); n++) {
			index2 = value.indexOf(tokenChar, index2);
			if (index2 <= -1)
				break;
			index2++; // Set index2 at next ':' position
		}
		index2--; // Set index2 at ':' position
		
		if (index1 <= -1)
			return null;
	
		String data;		
		if (index2 <= -1)
			data = new String(value.toCharArray(), index1, (value.length() - index1));
		else
			data = new String(value.toCharArray(), index1, (index2 - index1));

		return data;
	}


	public void setData(String dataArray[]) {
		if (nodeValue == null)
			return;
			
		int	nDataArray	= dataArray.length;
		StringBuffer value = new StringBuffer();
		
		for (int n=0; n<nDataArray; n++) {
			if (dataArray[n] != null)
				value.append(dataArray[n]);

			if (n < (nDataArray - 1))
				value.append(tokenChar);
		}

		nodeValue.setValue(value.toString());
	}
	
	public void setData(int dataNum, String data) {

		if (nodeValue == null)
			return;
		int nData = getNData();
		int maxDataNum = nData;
		if (nData < (dataNum + 1))
			maxDataNum = dataNum + 1;
		String dataArray[] = new String[maxDataNum];
		for (int n=0; n<maxDataNum; n++) {
			if (n == dataNum)
				dataArray[n] = data;
			else
				dataArray[n] = getData(n);
		}
		setData(dataArray);
	}

}
