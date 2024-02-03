/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : AudioClip.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class AudioClipNode extends Node {
	
	private String	descriptionExposedFieldName		= "description";
	private String	loopExposedFieldName			= "loop";
	private String	startTimeExposedFieldName		= "startTime";
	private String	stopTimeExposedFieldName		= "stopTime";
	private String	pitchExposedFieldName			= "pitch";
	private String	urlExposedFieldName				= "url";
	private String	isActiveEventInName				= "isActive";
	private String	durationEventOutName			= "duration";

	public AudioClipNode() {
		setHeaderFlag(false);
		setType(audioClipTypeName);

		// description exposed field
		SFString description = new SFString("");
		addExposedField(descriptionExposedFieldName, description);

		// loop exposed field
		SFBool loop = new SFBool(true);
		addExposedField(loopExposedFieldName, loop);

		// startTime exposed field
		SFTime startTime = new SFTime(0.0f);
		addExposedField(startTimeExposedFieldName, startTime);

		// stopTime exposed field
		SFTime stopTime = new SFTime(0.0f);
		addExposedField(stopTimeExposedFieldName, stopTime);

		// pitch exposed field
		SFFloat pitch = new SFFloat(1.0f);
		addExposedField(pitchExposedFieldName, pitch);

		// url exposed field
		MFString url = new MFString();
		addExposedField(urlExposedFieldName, url);

		
		// isActive eventOut field
		ConstSFBool isActive = new ConstSFBool(false);
		addEventOut(isActiveEventInName, isActive);

		// time eventOut field
		ConstSFTime durationChanged = new ConstSFTime(-1.0f);
		addEventOut(durationEventOutName, durationChanged);
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////

	public void setDescription(String value) {
		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		description.setValue(value);
	}

	public String getDescription() {
		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		return description.getValue();
	}

	////////////////////////////////////////////////
	//	Loop
	////////////////////////////////////////////////
	
	public void setLoop(boolean value) {
		SFBool loop = (SFBool)getExposedField(loopExposedFieldName);
		loop.setValue(value);
	}
	public boolean getLoop() {
		SFBool loop = (SFBool)getExposedField(loopExposedFieldName);
		return loop.getValue();
	}
	public boolean isLoop() {
		return getLoop();
	}

	////////////////////////////////////////////////
	//	Pitch
	////////////////////////////////////////////////
	
	public void setPitch(float value) {
		SFFloat pitch = (SFFloat)getExposedField(pitchExposedFieldName);
		pitch.setValue(value);
	}
	public float getPitch() {
		SFFloat pitch = (SFFloat)getExposedField(pitchExposedFieldName);
		return pitch.getValue();
	}

	////////////////////////////////////////////////
	//	Start time
	////////////////////////////////////////////////
	
	public void setStartTime(double value) {
		SFTime time = (SFTime)getExposedField(startTimeExposedFieldName);
		time.setValue(value);
	}
	public double getStartTime() {
		SFTime time = (SFTime)getExposedField(startTimeExposedFieldName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	//	Stop time
	////////////////////////////////////////////////
	
	public void setStopTime(double value) {
		SFTime time = (SFTime)getExposedField(stopTimeExposedFieldName);
		time.setValue(value);
	}
	public double getStopTime() {
		SFTime time = (SFTime)getExposedField(stopTimeExposedFieldName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////
	
	public void setIsActive(boolean value) {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveEventInName);
		field.setValue(value);
	}
	public boolean getIsActive() {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveEventInName);
		return field.getValue();
	}
	public boolean isActive() {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveEventInName);
		return field.getValue();
	}

	////////////////////////////////////////////////
	//	duration_changed
	////////////////////////////////////////////////
	
	public void setDurationChanged(double value) {
		ConstSFTime time = (ConstSFTime)getEventOut(durationEventOutName);
		time.setValue(value);
	}
	public double getDurationChanged() {
		ConstSFTime time = (ConstSFTime)getEventOut(durationEventOutName);
		return time.getValue();
	}

	////////////////////////////////////////////////
	// Url
	////////////////////////////////////////////////

	public void addUrl(String value) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.addValue(value);
	}
	public int getNUrls() {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		return url.getSize();
	}
	public void setUrl(int index, String value) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.set1Value(index, value);
	}
	public String getUrl(int index) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		return url.get1Value(index);
	}
	public void removeUrl(int index) {
		MFString url = (MFString)getExposedField(urlExposedFieldName);
		url.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public AudioClip next() {
		return (AudioClip)next(getType());
	}

	public AudioClip nextTraversal() {
		return (AudioClip)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		return false;
	}

	public void initialize() {
		super.initialize();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFString description = (SFString)getExposedField(descriptionExposedFieldName);
		printStream.println(indentString + "\t" + "description " + description );

		printStream.println(indentString + "\t" + "pitch " + getPitch() );
		printStream.println(indentString + "\t" + "startTime " + getStartTime() );
		printStream.println(indentString + "\t" + "stopTime " + getStopTime() );

		SFBool loop = (SFBool)getExposedField(loopExposedFieldName);
		printStream.println(indentString + "\t" + "loop " + loop);

		MFString url = (MFString)getExposedField(urlExposedFieldName);
		printStream.println(indentString + "\t" + "url [");
		url.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
