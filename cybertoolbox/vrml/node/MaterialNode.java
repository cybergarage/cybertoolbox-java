/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Material.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class MaterialNode extends Node {
	
	private String	transparencyFieldName			= "transparency";
	private String	ambientIntensityFieldName		= "ambientIntensity";
	private String	shininessFieldName				= "shininess";
	private String	diffuseColorFieldName			= "diffuseColor";
	private String	specularColorFieldName			= "specularColor";
	private String	emissiveColorFieldName			= "emissiveColor";

	public MaterialNode() {
		setHeaderFlag(false);
		setType(materialTypeName);

		// tranparency exposed field
		SFFloat transparency = new SFFloat(0.0f);
		transparency.setName(transparencyFieldName);
		addExposedField(transparency);

		// ambientIntesity exposed field
		SFFloat ambientIntensity = new SFFloat(0.2f);
		ambientIntensity.setName(ambientIntensityFieldName);
		addExposedField(ambientIntensity);

		// shininess exposed field
		SFFloat shininess = new SFFloat(0.2f);
		shininess.setName(shininessFieldName);
		addExposedField(shininess);

		// diffuseColor exposed field
		SFColor diffuseColor = new SFColor(0.8f, 0.8f, 0.8f);
		diffuseColor.setName(diffuseColorFieldName);
		addExposedField(diffuseColor);

		// specularColor exposed field
		SFColor specularColor = new SFColor(0.0f, 0.0f, 0.0f);
		specularColor.setName(specularColorFieldName);
		addExposedField(specularColor);

		// emissiveColor exposed field
		SFColor emissiveColor = new SFColor(0.0f, 0.0f, 0.0f);
		emissiveColor.setName(emissiveColorFieldName);
		addExposedField(emissiveColor);
	}

	////////////////////////////////////////////////
	//	Transparency
	////////////////////////////////////////////////
	
	public void setTransparency(float value) {
		SFFloat transparency = (SFFloat)getExposedField(transparencyFieldName);
		transparency.setValue(value);
	}
	public float getTransparency() {
		SFFloat transparency = (SFFloat)getExposedField(transparencyFieldName);
		return transparency.getValue();
	}

	////////////////////////////////////////////////
	//	AmbientIntensity
	////////////////////////////////////////////////
	
	public void setAmbientIntensity(float intensity) {
		SFFloat ambientIntensity = (SFFloat)getExposedField(ambientIntensityFieldName);
		ambientIntensity.setValue(intensity);
	}
	public float getAmbientIntensity() {
		SFFloat ambientIntensity = (SFFloat)getExposedField(ambientIntensityFieldName);
		return ambientIntensity.getValue();
	}

	////////////////////////////////////////////////
	//	Shininess
	////////////////////////////////////////////////
	
	public void setShininess(float value) {
		SFFloat shininess = (SFFloat)getExposedField(shininessFieldName);
		shininess.setValue(value);
	}
	public float getShininess() {
		SFFloat shininess = (SFFloat)getExposedField(shininessFieldName);
		return shininess.getValue();
	}

	////////////////////////////////////////////////
	//	AmbientColor
	////////////////////////////////////////////////

	public void getAmbientColor(float value[]) {
		float ambientIntensity = getAmbientIntensity();
		getDiffuseColor(value);
		value[0] *= ambientIntensity;
		value[1] *= ambientIntensity;
		value[2] *= ambientIntensity;
	}

	////////////////////////////////////////////////
	//	DiffuseColor
	////////////////////////////////////////////////

	public void setDiffuseColor(float value[]) {
		SFColor color = (SFColor)getExposedField(diffuseColorFieldName);
		color.setValue(value);
	}
	public void setDiffuseColor(float r, float g, float b) {
		SFColor color = (SFColor)getExposedField(diffuseColorFieldName);
		color.setValue(r, g, b);
	}
	public void getDiffuseColor(float value[]) {
		SFColor color = (SFColor)getExposedField(diffuseColorFieldName);
		color.getValue(value);
	}

	////////////////////////////////////////////////
	//	SpecularColor
	////////////////////////////////////////////////

	public void setSpecularColor(float value[]) {
		SFColor color = (SFColor)getExposedField(specularColorFieldName);
		color.setValue(value);
	}
	public void setSpecularColor(float r, float g, float b) {
		SFColor color = (SFColor)getExposedField(specularColorFieldName);
		color.setValue(r, g, b);
	}
	public void getSpecularColor(float value[]) {
		SFColor color = (SFColor)getExposedField(specularColorFieldName);
		color.getValue(value);
	}

	////////////////////////////////////////////////
	//	EmissiveColor
	////////////////////////////////////////////////

	public void setEmissiveColor(float value[]) {
		SFColor color = (SFColor)getExposedField(emissiveColorFieldName);
		color.setValue(value);
	}
	public void setEmissiveColor(float r, float g, float b) {
		SFColor color = (SFColor)getExposedField(emissiveColorFieldName);
		color.setValue(r, g, b);
	}
	public void getEmissiveColor(float value[]) {
		SFColor color = (SFColor)getExposedField(emissiveColorFieldName);
		color.getValue(value);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Material next() {
		return (Material)next(getType());
	}

	public Material nextTraversal() {
		return (Material)nextTraversalByType(getType());
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
		float color[] = new float[3];
		getDiffuseColor(color);		printStream.println(indentString + "\t" + "diffuseColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		printStream.println(indentString + "\t" + "ambientIntensity " + getAmbientIntensity() );
		getSpecularColor(color);	printStream.println(indentString + "\t" + "specularColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		getEmissiveColor(color);	printStream.println(indentString + "\t" + "emissiveColor " + color[X] + " "+ color[Y] + " " + color[Z] );
		printStream.println(indentString + "\t" + "shininess " + getShininess() );
		printStream.println(indentString + "\t" + "transparency " + getTransparency() );
	}
}
