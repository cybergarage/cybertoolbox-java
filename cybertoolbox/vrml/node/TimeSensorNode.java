/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : TimeSensor.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;
import vrml.util.Debug;

public class TimeSensorNode extends Node {
	
	private String	enabledFieldName			= "enabled";
	private String	loopFieldName				= "loop";
	private String	cybleIntervalFieldName		= "cycleIntervalIntensity";
	private String	startTimeFieldName			= "startTime";
	private String	stopTimeFieldName			= "stopTime";
	private String	cycleTimeFieldName			= "cycleTime";
	private String	timeFieldName				= "time";

	public TimeSensorNode() {
		setHeaderFlag(false);
		setType(timeSensorTypeName);
		setRunnable(true);

		// enabled exposed field
		SFBool enabled = new SFBool(true);
		addExposedField(enabledFieldName, enabled);

		// loop exposed field
		SFBool loop = new SFBool(true);
		addExposedField(loopFieldName, loop);

		// cybleInterval exposed field
		SFTime cybleInterval = new SFTime(1.0);
		addExposedField(cybleIntervalFieldName, cybleInterval);

		// startTime exposed field
		SFTime startTime = new SFTime(0.0f);
		addExposedField(startTimeFieldName, startTime);

		// stopTime exposed field
		SFTime stopTime = new SFTime(0.0f);
		addExposedField(stopTimeFieldName, stopTime);

	
		// cycleTime eventOut field
		ConstSFTime cycleTime = new ConstSFTime(-1.0f);
		addEventOut(cycleTimeFieldName, cycleTime);

		// time eventOut field
		ConstSFTime time = new ConstSFTime(-1.0f);
		addEventOut(timeFieldName, time);

		// isActive eventOut field
		ConstSFBool isActive = new ConstSFBool(false);
		addEventOut(isActiveFieldName, isActive);

		// fraction_changed eventOut field
		ConstSFFloat fractionChanged = new ConstSFFloat(0.0f);
		addEventOut(fractionFieldName, fractionChanged);
	}

	////////////////////////////////////////////////
	//	Enabled
	////////////////////////////////////////////////
	
	public void setEnabled(boolean value) {
		SFBool bEnabled = (SFBool)getExposedField(enabledFieldName);
		bEnabled.setValue(value);
	}
	public boolean getEnabled() {
		SFBool bEnabled = (SFBool)getExposedField(enabledFieldName);
		return bEnabled.getValue();
	}
	public boolean isEnabled() {
		return getEnabled();
	}
	public Field getEnabledField() {
		return getExposedField(enabledFieldName);
	}

	////////////////////////////////////////////////
	//	Loop
	////////////////////////////////////////////////
	
	public void setLoop(boolean value) {
		SFBool loop = (SFBool)getExposedField(loopFieldName);
		loop.setValue(value);
	}
	public boolean getLoop() {
		SFBool loop = (SFBool)getExposedField(loopFieldName);
		return loop.getValue();
	}
	public boolean isLoop() {
		return getLoop();
	}
	public Field getLoopField() {
		return getExposedField(loopFieldName);
	}

	////////////////////////////////////////////////
	//	Cyble Interval
	////////////////////////////////////////////////
	
	public void setCycleInterval(double value) {
		SFTime cycle = (SFTime)getExposedField(cybleIntervalFieldName);
		cycle.setValue(value);
	}
	public double getCycleInterval() {
		SFTime cycle = (SFTime)getExposedField(cybleIntervalFieldName);
		return cycle.getValue();
	}
	public Field getCycleIntervalField() {
		return getExposedField(cybleIntervalFieldName);
	}

	////////////////////////////////////////////////
	//	Start time
	////////////////////////////////////////////////
	
	public void setStartTime(double value) {
		SFTime time = (SFTime)getExposedField(startTimeFieldName);
		time.setValue(value);
	}
	public double getStartTime() {
		SFTime time = (SFTime)getExposedField(startTimeFieldName);
		return time.getValue();
	}
	public Field getStartTimeField() {
		return getExposedField(startTimeFieldName);
	}

	////////////////////////////////////////////////
	//	Stop time
	////////////////////////////////////////////////
	
	public void setStopTime(double value) {
		SFTime time = (SFTime)getExposedField(stopTimeFieldName);
		time.setValue(value);
	}
	public double getStopTime() {
		SFTime time = (SFTime)getExposedField(stopTimeFieldName);
		return time.getValue();
	}
	public Field getStopTimeField() {
		return getExposedField(stopTimeFieldName);
	}

	////////////////////////////////////////////////
	//	isActive
	////////////////////////////////////////////////
	
	public void setIsActive(boolean value) {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveFieldName);
		field.setValue(value);
	}
	public boolean getIsActive() {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveFieldName);
		return field.getValue();
	}
	public boolean isActive() {
		ConstSFBool field = (ConstSFBool)getEventOut(isActiveFieldName);
		return field.getValue();
	}
	public Field getIsActiveField() {
		return getEventOut(isActiveFieldName);
	}

	////////////////////////////////////////////////
	//	fraction_changed
	////////////////////////////////////////////////
	
	public void setFractionChanged(float value) {
		ConstSFFloat field = (ConstSFFloat)getEventOut(fractionFieldName);
		field.setValue(value);
	}
	public float getFractionChanged() {
		ConstSFFloat field = (ConstSFFloat)getEventOut(fractionFieldName);
		return field.getValue();
	}
	public Field getFractionChangedField() {
		return getEventOut(fractionFieldName);
	}

	////////////////////////////////////////////////
	//	Cycle time
	////////////////////////////////////////////////
	
	public void setCycleTime(double value) {
		ConstSFTime time = (ConstSFTime)getEventOut(cycleTimeFieldName);
		time.setValue(value);
	}
	public double getCycleTime() {
		ConstSFTime time = (ConstSFTime)getEventOut(cycleTimeFieldName);
		return time.getValue();
	}
	public Field getCycleTimeField() {
		return getEventOut(cycleTimeFieldName);
	}

	////////////////////////////////////////////////
	//	Time
	////////////////////////////////////////////////
	
	public void setTime(double value) {
		ConstSFTime time = (ConstSFTime)getEventOut(timeFieldName);
		time.setValue(value);
	}
	public double getTime() {
		ConstSFTime time = (ConstSFTime)getEventOut(timeFieldName);
		return time.getValue();
	}
	public Field getTimeField() {
		return getEventOut(timeFieldName);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public TimeSensor next() {
		return (TimeSensor)next(getType());
	}

	public TimeSensor nextTraversal() {
		return (TimeSensor)nextTraversalByType(getType());
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
		setCycleTime(-1.0f);
		setIsActive(false);
		setRunnableIntervalTime((int)(getCycleInterval() * 1000));
	}

	public void uninitialize() {
	}

	public double getCurrentSystemTime() {
		Date	date = new Date();
		return (double)date.getTime() / 1000.0;
	}
	
	private double currentTime = 0;
	
	public void update() {
	
		double startTime = getStartTime();
		double stopTime = getStopTime();
		double cycleInterval = getCycleInterval();

		boolean bActive	= isActive();
		boolean bEnable	= isEnabled();
		boolean bLoop		= isLoop();

		if (currentTime == 0)
			currentTime = getCurrentSystemTime();

		// isActive 
		if (bEnable == false && bActive == true) {
			setIsActive(false);
			sendEvent(getIsActiveField());
			return;
		}

		if (bActive == false && bEnable == true) {
			if (startTime <= currentTime) {
				if (bLoop == true && stopTime <= startTime)
					bActive = true;
				else if (bLoop == false && stopTime <= startTime)
					bActive = true;
				else if (currentTime <= stopTime) {
					if (bLoop == true && startTime < stopTime)
						bActive = true;
					else if	(bLoop == false && startTime < (startTime + cycleInterval) && (startTime + cycleInterval) <= stopTime)
						bActive = true;
					else if (bLoop == false && startTime < stopTime && stopTime < (startTime + cycleInterval))
						bActive = true;
				}
			}
			if (bActive) {
				setIsActive(true);
				sendEvent(getIsActiveField());
				setCycleTime(currentTime);
				sendEvent(getCycleTimeField());
			}
		}

		currentTime = getCurrentSystemTime();
	
		if (bActive == true && bEnable == true) {
			if (bLoop == true && startTime < stopTime) {
				if (stopTime < currentTime)
					bActive = false;
			}
			else if (bLoop == false && stopTime <= startTime) {
				if (startTime + cycleInterval < currentTime)
					bActive = false;
			}
			else if (bLoop == false && startTime < (startTime + cycleInterval) && (startTime + cycleInterval) <= stopTime) {
				if (startTime + cycleInterval < currentTime)
					bActive = false;
			}
			else if (bLoop == false && startTime < stopTime && stopTime < (startTime + cycleInterval)) {
				if (stopTime < currentTime)
					bActive = false;
			}

			if (bActive == false) {
				setIsActive(false);
				sendEvent(getIsActiveField());
			}
		}

		if (bEnable == false || isActive() == false)
			return;

		// fraction_changed 
		double	fraction = (currentTime - startTime) % cycleInterval;
		if (fraction == 0.0 && startTime < currentTime)
			fraction = 1.0;
		else
			fraction /= cycleInterval;
		setFractionChanged((float)fraction);
		sendEvent(getFractionChangedField());

		// cycleTime
		double	cycleTime		= getCycleTime();
		double	cycleEndTime	= cycleTime + cycleInterval;
		while (cycleEndTime < currentTime) {
			setCycleTime(cycleEndTime);
			cycleEndTime += cycleInterval;
			setCycleTime(currentTime);
			sendEvent(getCycleTimeField());
		}

		// time
		setTime(currentTime);
		sendEvent(getTimeField());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		SFBool bEnabled = (SFBool)getExposedField(enabledFieldName);
		SFBool loop = (SFBool)getExposedField(loopFieldName);

		printStream.println(indentString + "\t" + "cycleInterval " + getCycleInterval() );
		printStream.println(indentString + "\t" + "enabled " + bEnabled );
		printStream.println(indentString + "\t" + "loop " + loop );
		printStream.println(indentString + "\t" + "startTime " + getStartTime() );
		printStream.println(indentString + "\t" + "stopTime " + getStopTime() );
	}
}
