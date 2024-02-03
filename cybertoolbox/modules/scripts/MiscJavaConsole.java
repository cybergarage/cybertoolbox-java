/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	MiscJavaConsole.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class MiscJavaConsole extends Script {

    public void initialize () {
	}

    public void processEvent (Event e){
		if (e.getName().equals("value") == true) 
			System.out.println(((ConstSFString)e.getValue()).getValue());
    }

}
