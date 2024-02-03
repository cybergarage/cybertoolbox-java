/*----------------------------------------------------------------
*
*	CyberToolBox
*
*	Copyright (C) Satoshi Konno 1996-1997
*
*	File:	NumericAdd2Values.java
*
----------------------------------------------------------------*/
import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class NumericMulti2Values extends Script {

	SFString	valueOut;
	String		value1String;
	String		value2String;
	String		valueOutString;
	boolean		bExecution;

    public void initialize () {
		valueOut		= (SFString)getEventOut("value"); 
		value1String	= new String("0");
		value2String	= new String("0");
		valueOutString	= new String();
   		bExecution		= true;
   }

	private int getNValues(int index1, int index2, int index3) {
		if (0 <= index1) {
			if (index1 < index2) {
				if (index2 < index3) 
					return 4;
				else
					return 3;
			} 
			else
				return 2;
		}
		return 1;
	}

    public void processEvent (Event e){
		if (e.getName().equals("SYSTEM_EVENTIN_MODULE_EXECUTION") == true) { 
			String value = ((ConstSFString)e.getValue()).getValue().toUpperCase();
			if (value.compareTo("TRUE") == 0)
				bExecution = true;
			else if (value.compareTo("FALSE") == 0)
				bExecution = false;
		}

		if (e.getName().equals("value1") == true) 
			value1String = ((ConstSFString)e.getValue()).getValue();
		else if (e.getName().equals("value2") == true) 
			value2String = ((ConstSFString)e.getValue()).getValue();

		if (bExecution) {

			int value1Index1 = value1String.indexOf(',');
			int value1Index2 = value1String.indexOf(',', value1Index1+1);
			int value1Index3 = value1String.indexOf(',', value1Index2+1);

			int value1NValues = getNValues(value1Index1, value1Index2, value1Index3);

			int value2Index1 = value2String.indexOf(',');
			int value2Index2 = value2String.indexOf(',', value2Index1+1);
			int value2Index3 = value2String.indexOf(',', value2Index2+1);
		
			int value2NValues = getNValues(value2Index1, value2Index2, value2Index3);

			try {
				if ((value1NValues == 4 && value2NValues == 3) || (value1NValues == 3 && value2NValues == 4)) {
					
					String	valueString;
					int		index1, index2, index3;
					Double	xDouble, yDouble, zDouble, rDouble;

					if (value1NValues == 4) {
						valueString = value1String;
						index1 = value1Index1;
						index2 = value1Index2;
						index3 = value1Index3;
					}
					else {
						valueString = value2String;
						index1 = value2Index1;
						index2 = value2Index2;
						index3 = value2Index3;
					}

					xDouble = new Double(new String(valueString.toCharArray(), 0, (index1-1)+1));
					yDouble = new Double(new String(valueString.toCharArray(), index1+1, (index2-1)-(index1)));
					zDouble = new Double(new String(valueString.toCharArray(), index2+1, (index3-1)-(index2)));
					rDouble = new Double(new String(valueString.toCharArray(), index3+1, (valueString.length()-1) - index3));
					
					double rotValue[] = new double[4];

					rotValue[0] = xDouble.doubleValue();
					rotValue[1] = yDouble.doubleValue();
					rotValue[2] = zDouble.doubleValue();
					rotValue[3] = rDouble.doubleValue();

					if (value1NValues == 3) {
						valueString = value1String;
						index1 = value1Index1;
						index2 = value1Index2;
					}
					else {
						valueString = value2String;
						index1 = value2Index1;
						index2 = value2Index2;
					}

					xDouble = new Double(new String(valueString.toCharArray(), 0, (index1-1)+1));
					yDouble = new Double(new String(valueString.toCharArray(), index1+1, (index2-1)-(index1)));
					zDouble = new Double(new String(valueString.toCharArray(), index2+1, (valueString.length()-1) - index2));

					double vector[] = new double[3];

					vector[0] = xDouble.doubleValue();
					vector[1] = yDouble.doubleValue();
					vector[2] = zDouble.doubleValue();

					setRotationValue(rotValue);
					multi(vector);

					valueOutString = Double.toString(vector[0]) + "," + Double.toString(vector[1]) + "," + Double.toString(vector[2]);
					valueOut.setValue(valueOutString);

				}
				else if (value1NValues == 3 || value2NValues == 3) {
					
					String	valueString;
					int		index1, index2;
					Double	xDouble, yDouble, zDouble;

					if (value1NValues == 3) {
						valueString = value1String;
						index1 = value1Index1;
						index2 = value1Index2;
					}
					else {
						valueString = value2String;
						index1 = value2Index1;
						index2 = value2Index2;
					}

					xDouble = new Double(new String(valueString.toCharArray(), 0, (index1-1)+1));
					yDouble = new Double(new String(valueString.toCharArray(), index1+1, (index2-1)-(index1)));
					zDouble = new Double(new String(valueString.toCharArray(), index2+1, (valueString.length()-1) - index2));

					double vector[] = new double[3];

					vector[0] = xDouble.doubleValue();
					vector[1] = yDouble.doubleValue();
					vector[2] = zDouble.doubleValue();

					Double scaleDouble;

					if (value1NValues == 3) 
						scaleDouble = new Double(value2String);
					else 
						scaleDouble = new Double(value1String);
					
					double scale = scaleDouble.doubleValue();

					vector[0] *= scale;
					vector[1] *= scale;
					vector[2] *= scale;

					valueOutString = Double.toString(vector[0]) + "," + Double.toString(vector[1]) + "," + Double.toString(vector[2]);
					valueOut.setValue(valueOutString);
				}
				else {
					Double	dvalue1 = new Double(value1String);
					Double	dvalue2 = new Double(value2String);
					valueOutString = Double.toString(dvalue1.doubleValue() * dvalue2.doubleValue());
					valueOut.setValue(valueOutString);
				}
			}
			catch (NumberFormatException exception) {
				valueOutString = valueOutString.valueOf("");
				valueOut.setValue(valueOutString);
			}
		}
		else {
			valueOut.setValue(value1String);
		}
    }

	////////////////////////////////////////////////
	//	Rotation
	////////////////////////////////////////////////

	private double mRotationValue[] = new double[4];
	private double mMatrixValue[][] = new double[4][4]; 

	////////////////////////////////////////////////
	//	set value
	////////////////////////////////////////////////

	public void setRotationValue(double x, double y, double z, double rot) {
		mRotationValue[0] = x;
		mRotationValue[1] = y;
		mRotationValue[2] = z;
		mRotationValue[3] = rot;
	}

	public void setRotationValue(double rotation[]) {
		setRotationValue(rotation[0], rotation[1], rotation[2], rotation[3]);
	}

	////////////////////////////////////////////////
	//	get value
	////////////////////////////////////////////////

	public double[] getRotationVector() {
		return mRotationValue;
	}
	
	public double getRotationAngle() {
		return mRotationValue[3];
	}

	////////////////////////////////////////////////
	//	multi 
	////////////////////////////////////////////////

	public void multi(double vector[])
	{
		setRotationMatrix();
		multiMatrix(vector);
	}

	public void multiMatrix(double vector[])
	{
		double vectorIn[] = new double[4];

		vectorIn[0] = vector[0];
		vectorIn[1] = vector[1];
		vectorIn[2] = vector[2];
		vectorIn[3] = 1.0f;

		double vectorOut[] = new double[4];

		for (int n = 0; n<4; n++)
			vectorOut[n] = mMatrixValue[0][n]*vectorIn[0] + mMatrixValue[1][n]*vectorIn[1] + mMatrixValue[2][n]*vectorIn[2] + mMatrixValue[3][n]*vectorIn[3];

		vector[0] = vectorOut[0];
		vector[1] = vectorOut[1];
		vector[2] = vectorOut[2];
	}

	////////////////////////////////////////////////
	//	convert
	////////////////////////////////////////////////

	public void setRotationMatrix()
	{
		double vector[] = getRotationVector();
		double rotation = getRotationAngle();

		double k = (1.0 - Math.cos(rotation));
		double s = Math.sin(rotation);
		double c = Math.cos(rotation);
		double ax = vector[0];
		double ay = vector[1];
		double az = vector[2];
		double ax2 = ax * ax;
		double ay2 = ay * ay;
		double az2 = az * az;

		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				mMatrixValue[i][j] = 0.0f;
		}

		mMatrixValue[0][0] = k*ax2 + c;
		mMatrixValue[0][1] = k*ax*ay + s*az;
		mMatrixValue[0][2] = k*ax*az - s*ay;
		mMatrixValue[1][0] = k*ax*ay - s*az;
		mMatrixValue[1][1] = k*ay2 + c;
		mMatrixValue[1][2] = k*ay*az + s*ax;
		mMatrixValue[2][0] = k*ax*az + s*ay;
		mMatrixValue[2][1] = k*ay*az - s*ax;
		mMatrixValue[2][2] = k*az2 + c;
	}

}
