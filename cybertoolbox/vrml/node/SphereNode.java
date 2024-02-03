/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Sphere.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.util.*;
import vrml.field.*;

public class SphereNode extends GeometryNode {
	
	//// Field ////////////////
	private String	radiusFieldName	= "radius";

	public SphereNode() {
		setHeaderFlag(false);
		setType(sphereTypeName);

		///////////////////////////
		// Exposed Field 
		///////////////////////////

		// radius exposed field
		SFFloat radius = new SFFloat(1);
		addExposedField(radiusFieldName, radius);
	}

	////////////////////////////////////////////////
	//	Radius
	////////////////////////////////////////////////
	
	public void setRadius(float value) {
		SFFloat sffloat = (SFFloat)getExposedField(radiusFieldName);
		sffloat.setValue(value);
	}
	public float getRadius() {
		SFFloat sffloat = (SFFloat)getExposedField(radiusFieldName);
		return sffloat.getValue();
	} 

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Sphere next() {
		return (Sphere)next(getType());
	}

	public Sphere nextTraversal() {
		return (Sphere)nextTraversalByType(getType());
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
		calculateBoundingBox();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public void calculateBoundingBox() {
		setBoundingBoxCenter(0.0f, 0.0f, 0.0f);
		setBoundingBoxSize(getRadius(), getRadius(), getRadius());
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		printStream.println(indentString + "\t" + "radius " + getRadius() );
	}
}
