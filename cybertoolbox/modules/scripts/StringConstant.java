/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	StringConstant.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class StringConstant extends Script {

	SFString	fieldValue;
	SFString	eventOutValue;

    public void initialize () {
		fieldValue = (SFString)getField("SYSTEM_FIELD_MODULE_VALUE"); 
		eventOutValue = (SFString)getEventOut("value"); 
    }

    public void processEvent (Event e){
		eventOutValue.setValue(fieldValue.getValue());
    }

}
