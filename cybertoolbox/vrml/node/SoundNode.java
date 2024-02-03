/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Sound.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class SoundNode extends Node {
	
	//// Exposed Field ////////////////
	private String	minFrontExposedFieldName	= "minFront";
	private String	maxFrontExposedFieldName	= "maxFront";
	private String	minBackExposedFieldName		= "minBack";
	private String	maxBackExposedFieldName		= "maxBack";
	private String	intensityExposedFieldName	= "intensity";
	private String	priorityExposedFieldName	= "priority";
	private String	directionExposedFieldName	= "direction";
	private String	locationExposedFieldName	= "location";

	//// Field ////////////////
	private String	spatializeFieldName			= "spatialize";

	public SoundNode() {
		setHeaderFlag(false);
		setType(soundTypeName);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// minFront exposed field
		SFFloat minFront = new SFFloat(1);
		addExposedField(minFrontExposedFieldName, minFront);

		// maxFront exposed field
		SFFloat maxFront = new SFFloat(10);
		addExposedField(maxFrontExposedFieldName, maxFront);

		// minBack exposed field
		SFFloat minBack = new SFFloat(1);
		addExposedField(minBackExposedFieldName, minBack);

		// maxBack exposed field
		SFFloat maxBack = new SFFloat(10);
		addExposedField(maxBackExposedFieldName, maxBack);

		// intensity exposed field
		SFFloat intensity = new SFFloat(10);
		addExposedField(intensityExposedFieldName, intensity);

		// priority exposed field
		SFFloat priority = new SFFloat(10);
		addExposedField(priorityExposedFieldName, priority);

		// direction exposed field
		SFVec3f direction = new SFVec3f(0, 0, 1);
		addExposedField(directionExposedFieldName, direction);

		// location exposed field
		SFVec3f location = new SFVec3f(0, 0, 0);
		addExposedField(locationExposedFieldName, location);

		///////////////////////////
		// Field 
		///////////////////////////

		// spatialize exposed field
		SFBool spatialize = new SFBool(true);
		addField(spatializeFieldName, spatialize);
	}

	////////////////////////////////////////////////
	//	Direction
	////////////////////////////////////////////////

	public void setDirection(float value[]) {
		SFVec3f direction = (SFVec3f)getExposedField(directionExposedFieldName);
		direction.setValue(value);
	}
	public void setDirection(float x, float y, float z) {
		SFVec3f direction = (SFVec3f)getExposedField(directionExposedFieldName);
		direction.setValue(x, y, z);
	}
	public void getDirection(float value[]) {
		SFVec3f direction = (SFVec3f)getExposedField(directionExposedFieldName);
		direction.getValue(value);
	}

	////////////////////////////////////////////////
	//	Location
	////////////////////////////////////////////////

	public void setLocation(float value[]) {
		SFVec3f location = (SFVec3f)getExposedField(locationExposedFieldName);
		location.setValue(value);
	}
	public void setLocation(float x, float y, float z) {
		SFVec3f location = (SFVec3f)getExposedField(locationExposedFieldName);
		location.setValue(x, y, z);
	}
	public void getLocation(float value[]) {
		SFVec3f location = (SFVec3f)getExposedField(locationExposedFieldName);
		location.getValue(value);
	}

	////////////////////////////////////////////////
	//	MinFront
	////////////////////////////////////////////////
	
	public void setMinFront(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(minFrontExposedFieldName);
		sffloat.setValue(value);
	}
	public float getMinFront() {
		SFFloat sffloat = (SFFloat)getExposedField(minFrontExposedFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	MaxFront
	////////////////////////////////////////////////
	
	public void setMaxFront(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(maxFrontExposedFieldName);
		sffloat.setValue(value);
	}
	public float getMaxFront() {
		SFFloat sffloat = (SFFloat)getExposedField(maxFrontExposedFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	MinBack
	////////////////////////////////////////////////
	
	public void setMinBack(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(minBackExposedFieldName);
		sffloat.setValue(value);
	}
	public float getMinBack() {
		SFFloat sffloat = (SFFloat)getExposedField(minBackExposedFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	MaxBack
	////////////////////////////////////////////////
	
	public void setMaxBack(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(maxBackExposedFieldName);
		sffloat.setValue(value);
	}
	public float getMaxBack() {
		SFFloat sffloat = (SFFloat)getExposedField(maxBackExposedFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	Intensity
	////////////////////////////////////////////////
	
	public void setIntensity(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(intensityExposedFieldName);
		sffloat.setValue(value);
	}
	public float getIntensity() {
		SFFloat sffloat = (SFFloat)getExposedField(intensityExposedFieldName);
		return sffloat.getValue();
	}

	////////////////////////////////////////////////
	//	Priority
	////////////////////////////////////////////////
	
	public void setPriority(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(priorityExposedFieldName);
		sffloat.setValue(value);
	}
	public float getPriority() {
		SFFloat sffloat = (SFFloat)getExposedField(priorityExposedFieldName);
		return sffloat.getValue();
	}


	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Sound next() {
		return (Sound)next(getType());
	}

	public Sound nextTraversal() {
		return (Sound)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	Spatialize
	////////////////////////////////////////////////
	
	public void setSpatialize(boolean value) {
		SFBool bSpatialize = (SFBool)getField(spatializeFieldName);
		bSpatialize.setValue(value);
	}
	public boolean getSpatialize() {
		SFBool bSpatialize = (SFBool)getField(spatializeFieldName);
		return bSpatialize.getValue();
	}

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isAudioClipNode() || node.isMovieTextureNode())
			return true;
		else
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
		SFBool spatialize = (SFBool)getField(spatializeFieldName);
		SFVec3f direction = (SFVec3f)getExposedField(directionExposedFieldName);
		SFVec3f location = (SFVec3f)getExposedField(locationExposedFieldName);

		printStream.println(indentString + "\t" + "direction " + direction );
		printStream.println(indentString + "\t" + "location " + location );
		printStream.println(indentString + "\t" + "maxFront " + getMaxFront() );
		printStream.println(indentString + "\t" + "minFront " + getMinFront() );
		printStream.println(indentString + "\t" + "maxBack " + getMaxBack() );
		printStream.println(indentString + "\t" + "minBack " + getMinBack() );
		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "priority " + getPriority() );
		printStream.println(indentString + "\t" + "spatialize " + spatialize );

		AudioClipNode aclip = getAudioClipNodes();
		if (aclip != null) {
			if (aclip.isInstanceNode() == false) {
				String nodeName = aclip.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "source DEF " + aclip.getName() + " AudioClip {");
				else
					printStream.println(indentString + "\t" + "source AudioClip {");
				aclip.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "source USE " + aclip.getName());
		}

		MovieTextureNode mtexture = getMovieTextureNodes();
		if (mtexture != null) {
			if (mtexture.isInstanceNode() == false) {
				String nodeName = mtexture.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "source DEF " + mtexture.getName() + " MovieTexture {");
				else
					printStream.println(indentString + "\t" + "source MovieTexture {");
				mtexture.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "source USE " + mtexture.getName());
		}
	}
}
