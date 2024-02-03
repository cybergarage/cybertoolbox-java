/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : DirectionalLight.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class DirectionalLightNode extends LightNode {
	
	private String	ambientIntensityFieldName	= "ambientIntensity";
	private String	directionFieldName			= "direction";

	public DirectionalLightNode() {
		setHeaderFlag(false);
		setType(directionalLightTypeName);

		// ambient intensity exposed field
		SFFloat ambientIntensity = new SFFloat(0.0f);
		ambientIntensity.setName(ambientIntensityFieldName);
		addExposedField(ambientIntensity);

		// direction exposed field
		SFVec3f direction = new SFVec3f(0.0f, 0.0f, -1.0f);
		direction.setName(directionFieldName);
		addExposedField(direction);
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
	//	Direction
	////////////////////////////////////////////////

	public void setDirection(float value[]) {
		SFVec3f direction = (SFVec3f)getExposedField(directionFieldName);
		direction.setValue(value);
	}
	public void setDirection(float x, float y, float z) {
		SFVec3f direction = (SFVec3f)getExposedField(directionFieldName);
		direction.setValue(x, y, z);
	}
	public void getDirection(float value[]) {
		SFVec3f direction = (SFVec3f)getExposedField(directionFieldName);
		direction.getValue(value);
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
	public DirectionalLight next() {
		return (DirectionalLight)next(getType());
	}

	public DirectionalLight nextTraversal() {
		return (DirectionalLight)nextTraversalByType(getType());
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
		float rot[] = new float[4];

		SFBool bon = getOnField();
		printStream.println(indentString + "\t" + "on " + bon );

		printStream.println(indentString + "\t" + "intensity " + getIntensity() );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getColor(vec);			printStream.println(indentString + "\t" + "color " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getDirection(vec);		printStream.println(indentString + "\t" + "direction " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
	}
}
