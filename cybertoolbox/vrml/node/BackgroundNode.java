/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Background.java
*
******************************************************************/

package vrml.node;

import java.io.PrintWriter;
import java.util.Date;
import vrml.*;
import vrml.field.*;

public class BackgroundNode extends BindableNode {
	
	private String	groundColorFieldName	= "groundColor";
	private String	skyColorFieldName		= "skyColor";
	private String	groundAngleFieldName	= "groundAngle";
	private String	skyAngleFieldName		= "skyAngle";
	private String	frontUrlFieldName		= "frontUrl";
	private String	backUrlFieldName		= "backUrl";
	private String	leftUrlFieldName		= "leftUrl";
	private String	rightUrlFieldName		= "rightUrl";
	private String	topUrlFieldName			= "topUrl";
	private String	bottomUrlFieldName		= "bottomUrl";

	public BackgroundNode() {
		setHeaderFlag(false);
		setType(backgroundTypeName);

		// groundColor exposed field
		MFColor groundColor = new MFColor();
		addExposedField(groundColorFieldName, groundColor);

		// skyColor exposed field
		MFColor skyColor = new MFColor();
		addExposedField(skyColorFieldName, skyColor);

		// groundAngle exposed field
		MFFloat groundAngle = new MFFloat();
		addExposedField(groundAngleFieldName, groundAngle);

		// skyAngle exposed field
		MFFloat skyAngle = new MFFloat();
		addExposedField(skyAngleFieldName, skyAngle);

		// url exposed field
		MFString frontUrl = new MFString();
		addExposedField(frontUrlFieldName, frontUrl);

		// url exposed field
		MFString backUrl = new MFString();
		addExposedField(backUrlFieldName, backUrl);

		// url exposed field
		MFString leftUrl = new MFString();
		addExposedField(leftUrlFieldName, leftUrl);

		// url exposed field
		MFString rightUrl = new MFString();
		addExposedField(rightUrlFieldName, rightUrl);

		// url exposed field
		MFString topUrl = new MFString();
		addExposedField(topUrlFieldName, topUrl);

		// url exposed field
		MFString bottomUrl = new MFString();
		addExposedField(bottomUrlFieldName, bottomUrl);
	}

	////////////////////////////////////////////////
	// groundColor
	////////////////////////////////////////////////

	public void addGroundColor(float value[]) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.addValue(value);
	}
	public void addGroundColor(float r, float g, float b) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.addValue(r, g, b);
	}
	public int getNGroundColors() {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		return groundColor.getSize();
	}
	public void setGroundColor(int index, float value[]) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.set1Value(index, value);
	}
	public void setGroundColor(int index, float r, float g, float b) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.set1Value(index, r, g, b);
	}
	public void getGroundColor(int index, float value[]) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.get1Value(index, value);
	}
	public float[] getGroundColor(int index) {
		float value[] = new float[3];
		getGroundColor(index, value);
		return value;
	}
	public void removeGroundColor(int index) {
		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		groundColor.removeValue(index);
	}

	////////////////////////////////////////////////
	// skyColor
	////////////////////////////////////////////////

	public void addSkyColor(float value[]) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.addValue(value);
	}
	public void addSkyColor(float r, float g, float b) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.addValue(r, g, b);
	}
	public int getNSkyColors() {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		return skyColor.getSize();
	}
	public void setSkyColor(int index, float value[]) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.set1Value(index, value);
	}
	public void setSkyColor(int index, float r, float g, float b) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.set1Value(index, r, g, b);
	}
	public void getSkyColor(int index, float value[]) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.get1Value(index, value);
	}
	public float[] getSkyColor(int index) {
		float value[] = new float[3];
		getSkyColor(index, value);
		return value;
	}
	public void removeSkyColor(int index) {
		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		skyColor.removeValue(index);
	}

	////////////////////////////////////////////////
	// groundAngle
	////////////////////////////////////////////////

	public void addGroundAngle(float value) {
		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		groundAngle.addValue(value);
	}
	public int getNGroundAngles() {
		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		return groundAngle.getSize();
	}
	public void setGroundAngle(int index, float value) {
		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		groundAngle.set1Value(index, value);
	}
	public float getGroundAngle(int index) {
		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		return groundAngle.get1Value(index);
	}
	public void removeGroundAngle(int index) {
		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		groundAngle.removeValue(index);
	}

	////////////////////////////////////////////////
	// skyAngle
	////////////////////////////////////////////////

	public void addSkyAngle(float value) {
		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		skyAngle.addValue(value);
	}
	public int getNSkyAngles() {
		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		return skyAngle.getSize();
	}
	public void setSkyAngle(int index, float value) {
		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		skyAngle.set1Value(index, value);
	}
	public float getSkyAngle(int index) {
		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		return skyAngle.get1Value(index);
	}
	public void removeSkyAngle(int index) {
		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		skyAngle.removeValue(index);
	}

	////////////////////////////////////////////////
	// frontUrl
	////////////////////////////////////////////////

	public void addFrontUrl(String value) {
		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		frontUrl.addValue(value);
	}
	public int getNFrontUrls() {
		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		return frontUrl.getSize();
	}
	public void setFrontUrl(int index, String value) {
		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		frontUrl.set1Value(index, value);
	}
	public String getFrontUrl(int index) {
		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		return frontUrl.get1Value(index);
	}
	public void removeFrontUrl(int index) {
		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		frontUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	// backUrl
	////////////////////////////////////////////////

	public void addBackUrl(String value) {
		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		backUrl.addValue(value);
	}
	public int getNBackUrls() {
		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		return backUrl.getSize();
	}
	public void setBackUrl(int index, String value) {
		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		backUrl.set1Value(index, value);
	}
	public String getBackUrl(int index) {
		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		return backUrl.get1Value(index);
	}
	public void removeBackUrl(int index) {
		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		backUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	// leftUrl
	////////////////////////////////////////////////

	public void addLeftUrl(String value) {
		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		leftUrl.addValue(value);
	}
	public int getNLeftUrls() {
		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		return leftUrl.getSize();
	}
	public void setLeftUrl(int index, String value) {
		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		leftUrl.set1Value(index, value);
	}
	public String getLeftUrl(int index) {
		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		return leftUrl.get1Value(index);
	}
	public void removeLeftUrl(int index) {
		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		leftUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	// rightUrl
	////////////////////////////////////////////////

	public void addRightUrl(String value) {
		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		rightUrl.addValue(value);
	}
	public int getNRightUrls() {
		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		return rightUrl.getSize();
	}
	public void setRightUrl(int index, String value) {
		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		rightUrl.set1Value(index, value);
	}
	public String getRightUrl(int index) {
		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		return rightUrl.get1Value(index);
	}
	public void removeRightUrl(int index) {
		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		rightUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	// topUrl
	////////////////////////////////////////////////

	public void addTopUrl(String value) {
		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		topUrl.addValue(value);
	}
	public int getNTopUrls() {
		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		return topUrl.getSize();
	}
	public void setTopUrl(int index, String value) {
		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		topUrl.set1Value(index, value);
	}
	public String getTopUrl(int index) {
		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		return topUrl.get1Value(index);
	}
	public void removeTopUrl(int index) {
		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		topUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	// bottomUrl
	////////////////////////////////////////////////

	public void addBottomUrl(String value) {
		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		bottomUrl.addValue(value);
	}
	public int getNBottomUrls() {
		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		return bottomUrl.getSize();
	}
	public void setBottomUrl(int index, String value) {
		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		bottomUrl.set1Value(index, value);
	}
	public String getBottomUrl(int index) {
		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		return bottomUrl.get1Value(index);
	}
	public void removeBottomUrl(int index) {
		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		bottomUrl.removeValue(index);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Background next() {
		return (Background)next(getType());
	}

	public Background nextTraversal() {
		return (Background)nextTraversalByType(getType());
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

		MFColor groundColor = (MFColor)getExposedField(groundColorFieldName);
		printStream.println(indentString + "\t" + "groundColor [");
		groundColor.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFColor skyColor = (MFColor)getExposedField(skyColorFieldName);
		printStream.println(indentString + "\t" + "skyColor [");
		skyColor.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");


		MFFloat groundAngle = (MFFloat)getExposedField(groundAngleFieldName);
		printStream.println(indentString + "\t" + "groundAngle [");
		groundAngle.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFFloat skyAngle = (MFFloat)getExposedField(skyAngleFieldName);
		printStream.println(indentString + "\t" + "skyAngle [");
		skyAngle.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");


		MFString frontUrl = (MFString)getExposedField(frontUrlFieldName);
		printStream.println(indentString + "\t" + "frontUrl [");
		frontUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString backUrl = (MFString)getExposedField(backUrlFieldName);
		printStream.println(indentString + "\t" + "backUrl [");
		backUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString leftUrl = (MFString)getExposedField(leftUrlFieldName);
		printStream.println(indentString + "\t" + "leftUrl [");
		leftUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString rightUrl = (MFString)getExposedField(rightUrlFieldName);
		printStream.println(indentString + "\t" + "rightUrl [");
		rightUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString topUrl = (MFString)getExposedField(topUrlFieldName);
		printStream.println(indentString + "\t" + "topUrl [");
		topUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");

		MFString bottomUrl = (MFString)getExposedField(bottomUrlFieldName);
		printStream.println(indentString + "\t" + "bottomUrl [");
		bottomUrl.outputContext(printStream, indentString + "\t\t");
		printStream.println(indentString + "\t" + "]");
	}
}
