/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	SystemClock.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class SystemClock extends Script {

	SFString	frame;
	String		frameString;
	int			frameCount;

    public void initialize () {
		frame = (SFString)getEventOut("outValue"); 
		frameString = new String();
		frameCount = 0;
    }

    public void processEvent (Event e){
		frameCount++;
		frameString = frameString.valueOf(frameCount);
		frame.setValue(frameString);
    }

}
