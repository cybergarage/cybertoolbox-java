/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Appearance.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class AppearanceNode extends Node {

	private String	materialExposedFieldName			= "material";
	private String	textureExposedFieldName				= "texture";
	private String	textureTransformExposedFieldName	= "textureTransform";

	public AppearanceNode() {
		setHeaderFlag(false);
		setType(appearanceTypeName);

		SFNode material = new SFNode();
		addExposedField(materialExposedFieldName, material);

		SFNode texture = new SFNode();
		addExposedField(textureExposedFieldName, texture);

		SFNode texTransform = new SFNode();
		addExposedField(textureTransformExposedFieldName, texTransform);
	}

	////////////////////////////////////////////////
	//	SFNode Fields
	////////////////////////////////////////////////

	public SFNode getMaterialField() {
		return (SFNode)getExposedField(materialExposedFieldName);
	}

	public SFNode getTextureField() {
		return (SFNode)getExposedField(textureExposedFieldName);
	}

	public SFNode getTextureTransformField() {
		return (SFNode)getExposedField(textureTransformExposedFieldName);
	}

	public void updateMaterialField() {
		getMaterialField().setValue(getMaterialNodes());
	}

	public void updateTextureField() {
		getTextureField().setValue(getTextureNode());
	}

	public void updateTextureTransformlField() {
		getTextureTransformField().setValue(getTextureTransformNodes());
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Appearance next() {
		return (Appearance)next(getType());
	}

	public Appearance nextTraversal() {
		return (Appearance)nextTraversalByType(getType());
	}
*/

	////////////////////////////////////////////////
	//	abstract functions
	////////////////////////////////////////////////
	
	public boolean isChildNodeType(Node node){
		if (node.isMaterialNode() || node.isTextureNode() || node.isTextureTransformNode())
			return true;
		else
			return false;
	}

	public void initialize() {
		super.initialize();
		updateMaterialField();
		updateTextureField();
		updateTextureTransformlField();
	}

	public void uninitialize() {
	}

	public void update() {
	}

	////////////////////////////////////////////////
	//	Infomation
	////////////////////////////////////////////////

	public void outputContext(PrintWriter printStream, String indentString) {
		MaterialNode material = getMaterialNodes();
		if (material != null) {
			if (material.isInstanceNode() == false) {
				String nodeName = material.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "material DEF " + material.getName() + " Material {");
				else
			printStream.println(indentString + "\t" + "material Material {");
			material.outputContext(printStream, indentString + "\t");
			printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "material USE " + material.getName());
		}

		Node texture = getTextureNode();
		if (texture != null) {
			if (texture.isInstanceNode() == false) {
				String nodeName = texture.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "texture DEF " + texture.getName() + " " + texture.getType() + " {");
				else
					printStream.println(indentString + "\t" + "texture " + texture.getType() + " {");
				texture.outputContext(printStream, indentString + "\t");
				printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "texture USE " + texture.getName());
		}

		TextureTransformNode textureTransform = getTextureTransformNodes();
		if (textureTransform != null) {
			if (textureTransform.isInstanceNode() == false) {
				String nodeName = textureTransform.getName();
				if (nodeName != null && 0 < nodeName.length())
					printStream.println(indentString + "\t" + "textureTransform DEF " + textureTransform.getName() + " TextureTransform {");
				else
				printStream.println(indentString + "\t" + "textureTransform TextureTransform {");
			textureTransform.outputContext(printStream, indentString + "\t");
			printStream.println(indentString + "\t" + "}");
			}
			else 
				printStream.println(indentString + "\t" + "textureTransform USE " + textureTransform.getName());
		}
	}
}