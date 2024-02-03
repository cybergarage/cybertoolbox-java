/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	System_Frame.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemFrame extends Script {

	SFString	frame;
	String		frameString;
	int			frameCount;

    public void initialize () {
		frame = (SFString)getEventOut("frame"); 
		frameString = new String();
		frameCount = 0;
    }

    public void processEvent (Event e){
		frameCount++;
		frameString = frameString.valueOf(frameCount);
		frame.setValue(frameString);
    }

}
