/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Viewpoint.java
*
******************************************************************/

package vrml.node;

import java.lang.String;
import java.io.PrintWriter;
import vrml.*;
import vrml.field.*;

public class ViewpointNode extends BindableNode {
	
	private String	positionFieldName			= "position";
	private String	orientationFieldName		= "orientation";
	private String	fieldOfViewFieldName		= "fieldOfView";
	private String	descriptionFieldName		= "description";
	private String	jumpFieldName				= "jump";

	public ViewpointNode() {
		setHeaderFlag(false);
		setType(viewpointTypeName);

		// position exposed field
		SFVec3f position = new SFVec3f(0.0f, 0.0f, 0.0f);
		position.setName(positionFieldName);
		addExposedField(position);

		// orientation exposed field
		SFRotation orientation = new SFRotation(0.0f, 0.0f, 1.0f, 0.0f);
		orientation.setName(orientationFieldName);
		addExposedField(orientation);

		// description exposed field
		SFString description = new SFString("");
		description.setName(descriptionFieldName);
		addExposedField(description);

		// fov exposed field
		SFFloat fov = new SFFloat(0.785398f);
		fov.setName(fieldOfViewFieldName);
		addExposedField(fov);

		// jump exposed field
		SFBool jump = new SFBool(true);
		jump.setName(jumpFieldName);
		addExposedField(jump);
	}

	////////////////////////////////////////////////
	//	Jump
	////////////////////////////////////////////////
	
	public void setJump(boolean value) {
		SFBool jump = (SFBool)getExposedField(jumpFieldName);
		jump.setValue(value);
	}
	public boolean getJump() {
		SFBool jump = (SFBool)getExposedField(jumpFieldName);
		return jump.getValue();
	}

	////////////////////////////////////////////////
	//	FieldOfView
	////////////////////////////////////////////////
	
	public void setFieldOfView(float value) {
		SFFloat fov = (SFFloat)getExposedField(fieldOfViewFieldName);
		fov.setValue(value);
	}
	public float getFieldOfView() {
		SFFloat fov = (SFFloat)getExposedField(fieldOfViewFieldName);
		return fov.getValue();
	}

	////////////////////////////////////////////////
	//	Description
	////////////////////////////////////////////////
	
	public void setDescription(String value) {
		SFString description = (SFString)getExposedField(descriptionFieldName);
		description.setValue(value);
	}
	public String getDescription() {
		SFString description = (SFString)getExposedField(descriptionFieldName);
		return description.getValue();
	}

	////////////////////////////////////////////////
	//	Position
	////////////////////////////////////////////////

	public void setPosition(float value[]) {
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		position.setValue(value);
	}
	public void setPosition(float x, float y, float z) {
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		position.setValue(x, y, z);
	}
	public void getPosition(float value[]) {
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		position.getValue(value);
	}
	public SFVec3f getPosition() {
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		return position;
	}

	////////////////////////////////////////////////
	//	Orientation
	////////////////////////////////////////////////

	public void setOrientation(float value[]) {
		SFRotation orientation = (SFRotation)getExposedField(orientationFieldName);
		orientation.setValue(value);
	}
	public void setOrientation(float x, float y, float z, float w) {
		SFRotation orientation = (SFRotation)getExposedField(orientationFieldName);
		orientation.setValue(x, y, z, w);
	}
	public void getOrientation(float value[]) {
		SFRotation orientation = (SFRotation)getExposedField(orientationFieldName);
		orientation.getValue(value);
	}
	public SFRotation getOrientation() {
		SFRotation orientation = (SFRotation)getExposedField(orientationFieldName);
		return orientation;
	}

	////////////////////////////////////////////////
	//	Add position
	////////////////////////////////////////////////

	public void addPosition(float worldTranslation[]) { 
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		position.add(worldTranslation);
	}

	public void addPosition(float localTranslation[], float frame[][]) { 
		SFVec3f position = (SFVec3f)getExposedField(positionFieldName);
		float	translation[] = new float[3];
		for (int axis=0; axis<3; axis++) {
			SFVec3f vector = new SFVec3f(frame[axis]);
			vector.scale(localTranslation[axis]);
			vector.getValue(translation);
			position.add(translation);
		}
	}

	public void addPosition(float x, float y, float z, float frame[][]) { 
		float localTranslation[] = new float[3];
		localTranslation[0] = x;
		localTranslation[1] = y;
		localTranslation[2] = z;
		addPosition(localTranslation, frame);
	}

	////////////////////////////////////////////////
	//	Add orientation
	////////////////////////////////////////////////

	public void addOrientation(SFRotation rot) {
		SFRotation viewOrientation = (SFRotation)getExposedField(orientationFieldName);
		viewOrientation.add(rot);
	}

	public void addOrientation(float rotationValue[]) {
		SFRotation viewOrientation = (SFRotation)getExposedField(orientationFieldName);
		viewOrientation.add(rotationValue);
	}

	public void addOrientation(float x, float y, float z, float rot) {
		SFRotation viewOrientation = (SFRotation)getExposedField(orientationFieldName);
		viewOrientation.add(x, y, z, rot);
	}

	////////////////////////////////////////////////
	//	List
	////////////////////////////////////////////////

/* for Visual C++
	public Viewpoint next() {
		return (Viewpoint)next(getType());
	}

	public Viewpoint nextTraversal() {
		return (Viewpoint)nextTraversalByType(getType());
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
		SFBool jump = (SFBool)getExposedField(jumpFieldName);
		SFString description = (SFString)getExposedField(descriptionFieldName);

		printStream.println(indentString + "\t" + "fieldOfView " + getFieldOfView() );
		printStream.println(indentString + "\t" + "jump " + jump);
		getPosition(vec);			printStream.println(indentString + "\t" + "position " + vec[X] + " "+ vec[Y] + " " + vec[Z] );
		getOrientation(rot);		printStream.println(indentString + "\t" + "orientation " + rot[X] + " "+ rot[Y] + " " + rot[Z] + " " + rot[W] );
		printStream.println(indentString + "\t" + "description " + description );
	}

	////////////////////////////////////////////////
	//	Local frame
	////////////////////////////////////////////////

	public void getFrame(float frame[][]) {

		SFRotation orientation = getOrientation();

		// local x frame
		frame[X][X] = 1.0f;
		frame[X][Y] = 0.0f;
		frame[X][Z] = 0.0f;
		orientation.multi(frame[X]);

		// local x frame
		frame[Y][X] = 0.0f;
		frame[Y][Y] = 1.0f;
		frame[Y][Z] = 0.0f;
		orientation.multi(frame[Y]);

		// local x frame
		frame[Z][X] = 0.0f;
		frame[Z][Y] = 0.0f;
		frame[Z][Z] = 1.0f;
		orientation.multi(frame[Z]);
	}

	public float[][] getFrame() {
		float frame[][] = new float[3][3];
		getFrame(frame);
		return frame;
	}

	////////////////////////////////////////////////
	//	Viewpoint Matrix
	////////////////////////////////////////////////

	public void getMatrix(SFMatrix matrix) {
		float	position[] = new float[3];
		float	rotation[] = new float[4];
		
		getPosition(position);
		SFVec3f	transView = new SFVec3f(position);
		transView.invert();

		getOrientation(rotation);
		SFRotation rotView = new SFRotation(rotation);
		rotView.invert();

		SFMatrix	mxTrans = new SFMatrix();
		SFMatrix	mxRot = new SFMatrix();
		mxTrans.setValueAsTranslation(transView);
		mxRot.setValueAsRotation(rotView);

		matrix.init();
		matrix.add(mxRot);
		matrix.add(mxTrans);
	}

	public SFMatrix getMatrix() {
		SFMatrix mx = new SFMatrix();
		getMatrix(mx);
		return mx;
	}

	public void getMatrix(float value[][]) {
		SFMatrix mx = new SFMatrix();
		getMatrix(mx);
		mx.getValue(value);
	}
}
