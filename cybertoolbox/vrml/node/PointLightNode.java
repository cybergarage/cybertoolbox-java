/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : PointLight.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class PointLightNode extends LightNode {

	private String	ambientIntensityFieldName	= "ambientIntensity";
	private String	locationFieldName			= "location";
	private String	radiusFieldName				= "radius";
	private String	attenuationFieldName		= "attenuation";
	
	public PointLightNode() {
		setHeaderFlag(false);
		setType(pointLightTypeName);

		// ambient intensity exposed field
		SFFloat ambientIntensity = new SFFloat(0.0f);
		ambientIntensity.setName(ambientIntensityFieldName);
		addExposedField(ambientIntensity);

		// location exposed field
		SFVec3f location = new SFVec3f(0.0f, 0.0f, 0.0f);
		location.setName(locationFieldName);
		addExposedField(location);

		// radius exposed field
		SFFloat radius = new SFFloat(100.0f);
		radius.setName(radiusFieldName);
		addExposedField(radius);

		// attenuation exposed field
		SFVec3f attenuation = new SFVec3f(1.0f, 0.0f, 0.0f);
		attenuation.setName(attenuationFieldName);
		addExposedField(attenuation);
	}

	////////////////////////////////////////////////
	//	AmbientIntensity
	////////////////////////////////////////////////
	
	public void setAmbientIntensity(float value) {
		SFFloat intensity = (SFFloat)getExposedField(ambientIntensityFieldName);
		intensity.setValue(value);
	}
	public float getAmbientIntensity() {
		SFFloat intensity = (SFFloat)getExposedField(ambientIntensityFieldName);
		return intensity.getValue();
	}

	////////////////////////////////////////////////
	//	Location
	////////////////////////////////////////////////

	public void setLocation(float value[]) {
		SFVec3f location = (SFVec3f)getExposedField(locationFieldName);
		location.setValue(value);
	}
	public void setLocation(float x, float y, float z) {
		SFVec3f location = (SFVec3f)getExposedField(locationFieldName);
		location.setValue(x, y, z);
	}
	public void getLocation(float value[]) {
		SFVec3f location = (SFVec3f)getExposedField(locationFieldName);
		location.getValue(value);
	}

	////////////////////////////////////////////////
	//	Radius
	////////////////////////////////////////////////
	
	public void setRadius(float value) {
		SFFloat radius = (SFFloat)getExposedField(radiusFieldName);
		radius.setValue(value);
	}
	public float getRadius() {
		SFFloat radius = (SFFloat)getExposedField(radiusFieldName);
		return radius.getValue();
	}

	////////////////////////////////////////////////
	//	Attenuation
	////////////////////////////////////////////////

	public void setAttenuation(float value[]) {
		SFVec3f attenuation = (SFVec3f)getExposedField(attenuationFieldName);
		attenuation.setValue(value);
	}
	public void setAttenuation(float x, float y, float z) {
		SFVec3f attenuation = (SFVec3f)getExposedField(attenuationFieldName);
		attenuation.setValue(x, y, z);
	}
	public void getAttenuation(float value[]) {
		SFVec3f attenuation = (SFVec3f)getExposedField(attenuationFieldName);
		attenuation.getValue(value);
	}

	////////////////////////////////////////////////
	//	Diffuse Color
	////////////////////////////////////////////////

	public void getDiffuseColor(float value[]) {
		getColor(value);
		float	intensity = getIntensity();
		value[0] *= intensity;
		value[1] *= intensity;
		value[2] *= intensity;
	}

	////////////////////////////////////////////////
	//	Ambient Color
	////////////////////////////////////////////////

	public void getAmbientColor(float value[]) {
		getColor(value);
		float	intensity = getIntensity();
		float	ambientIntensity = getAmbientIntensity();
		value[0] *= intensity * ambientIntensity;
		value[1] *= intensity * ambientIntensity;
		value[2] *= intensity * ambientIntensity;
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public PointLight next() {
		return (PointLight)next(getType());
	}

	public PointLight nextTraversal() {
		return (PointLight)nextTraversalByType(getType());
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
		float vec[] = new float[3];

		SFBool bon = getOnField();
		printStream.println(indentString + "\t" + "on " + bon );

		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getColor(vec);			printStream.println(indentString + "\t" + "color " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getLocation(vec);		printStream.println(indentString + "\t" + "location " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		printStream.println(indentString + "\t" + "radius " + getRadius() );
		getAttenuation(vec);	printStream.println(indentString + "\t" + "attenuation " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
