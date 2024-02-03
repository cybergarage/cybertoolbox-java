/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Fastrak.java
*
******************************************************************/

package vrml.sensor;

public class Fastrak extends SerialPort {

	public static final int X = 0;
	public static final int Y = 1;
	public static final int Z = 2;

	private static final String toBinaryCommand				= "f";
	private static final String toASCIICommand				= "F";
	private static final String toContinuousCommand	 		= "C";
	private static final String toNonContinuousCommand		= "c";
	private static final String retrieveStatusCommand		= "S";
	private static final String getRecordCommand				= "P";
	private static final String systemResetCommand		    = "W";
	private static final String performBoresightCommand		= "B1\r";
	private static final String activeStationStateCommand	= "l1\r";	
	
	public static final int RECEIVER1 = 1;
	public static final int RECEIVER2 = 2;
	public static final int RECEIVER3 = 3;
	public static final int RECEIVER4 = 4;
	
	private float mPositionData[][] = new float[4][3];
	private float mRotationData[][] = new float[4][3];
	
	private int mUpdateFlag = 0;
	private int mnReciver = 0;

	/**
	 *	@param deviceName	name of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	@param baudrate		baudrate of the port.
	 *	<UL>
	 *	9600<BR>
	 *	19200<BR>
	 *	38400<BR>
	 *	....<BR> 
	 *	</UL>
	 *	@see vrml.sensor.SerialPort#SerialPort(String deviceName, int baudrate, int dataBits, int stopBits, int parity)
	 */
	public Fastrak(String deviceName, int baudrate) {
		super(deviceName, baudrate, DATABITS_8, STOPBITS_1, PARITY_NONE);
		initialize();
	}

	/**
	 *	@param device		number of the port to open.  ex. "COM1", "/dev/ttyd1"
	 *	<UL>
	 *	SERIAL1<BR>
	 *	SERIAL2<BR>
	 *	SERIAL3<BR>
	 *	SERIAL4<BR> 
	 *	</UL>
	 *	@param baudrate		baudrate of the port.
	 *	<UL>
	 *	9600<BR>
	 *	19200<BR>
	 *	38400<BR>
	 *	....<BR> 
	 *	</UL>
	 *	@see vrml.sensor.SerialPort#SerialPort(int serialport, int baudrate, int dataBits, int stopBits, int parity)
	 */
	public Fastrak(int device, int baudrate) {
		super(device, baudrate, DATABITS_8, STOPBITS_1, PARITY_NONE);
		initialize();
	}

	private void initialize() {
		int n;
		int nReceiver = 0;
		write(activeStationStateCommand);
		byte data[] = getMessage(9).getBytes();
		if (data.length == 9) {
			for (n=0; n<4; n++) {
				if (data[n+3] == '1')
			    nReceiver++;	
			}
		}
		setActiveNReceivers(nReceiver);
		
	    for (n=1; n<=4; n++) {
			String commSetRecvN = "O" + n + ",2,0,4,1\r";
			write(commSetRecvN);
	    }

		write(toASCIICommand);
		write(toNonContinuousCommand); 

	    for (n=0; n<4; n++) {
			initData(mPositionData[n]);
			initData(mRotationData[n]);
		}

		setReceiverUpdateFlag(0);
	}

	private void setActiveNReceivers(int n) {
		mnReciver = n;
	}

	/**
	 *	@return the active receiver number
	 */
	public int getActiveNReceivers() {
		return mnReciver;
	}
	
	private void setReceiverUpdateFlag(int flag) {
		mUpdateFlag = flag;
	}
	
	private int getReceiverUpdateFlag() {
		return mUpdateFlag;
	}

	private void initData(float data[]) {
		for (int n=0; n<data.length; n++) 
			data[n] = 0.0f;
	}
	
	private void copyData(float srcData[], float destData[]) {
		for (int n=0; n<srcData.length; n++) 
			destData[n] = srcData[n];
	}
	
	private void updateData(int updateBit) {
		int recvFlag = getReceiverUpdateFlag();

		if ((updateBit & recvFlag) == 0)
			readData();

		int newRecvFlag = getReceiverUpdateFlag();
		newRecvFlag &= ~updateBit;
		setReceiverUpdateFlag(newRecvFlag);	
	}

	/**
	 *	@param nReciver		number of reciver. (1 - 4)
	 *	<UL>
	 *	RECEIVER1<BR>
	 *	RECEIVER2<BR>
	 *	RECEIVER3<BR>
	 *	RECEIVER4<BR> 
	 *	</UL>
	 *	@param pos			the position of reciver.
	 */
	public void getPosition(int nReciver, float pos[]) {
	    int	bitFlag = 0x01 << (nReciver-1+4);
		updateData(bitFlag);
		copyData(mPositionData[nReciver-1], pos);
	}

	/**
	 *	@param nReciver		number of reciver. (1 - 4)
	 *	<UL>
	 *	RECEIVER1<BR>
	 *	RECEIVER2<BR>
	 *	RECEIVER3<BR>
	 *	RECEIVER4<BR> 
	 *	</UL>
	 *	@return				the position of reciver.
	 */
	public float []getPosition(int nReciver) {
		float pos[] = new float[3];
		getPosition(nReciver, pos);
		return pos;
	}

	/**
	 *	@param nReciver		number of reciver. (1 - 4)
	 *	<UL>
	 *	RECEIVER1<BR>
	 *	RECEIVER2<BR>
	 *	RECEIVER3<BR>
	 *	RECEIVER4<BR> 
	 *	</UL>
	 *	@param euler		the orientation of reciver.
	 */
	public void getOrientation(int nReciver, float euler[]) {
	    int	bitFlag = 0x01 << (nReciver-1);
		updateData(bitFlag);
		copyData(mRotationData[nReciver-1], euler);
	}	

	/**
	 *	@param nReciver		number of reciver. (1 - 4)
	 *	<UL>
	 *	RECEIVER1<BR>
	 *	RECEIVER2<BR>
	 *	RECEIVER3<BR>
	 *	RECEIVER4<BR> 
	 *	</UL>
	 *	@return				the orientation of reciver.
	 */
	public float []getOrientation(int nReciver) {
		float euler[] = new float[3];
		getOrientation(nReciver, euler);
		return euler;
	}	

	private String getMessage(int nMessage) {
		String data = null;
		int n = 0;
		int cnt = 0;
	    while (n < nMessage && cnt < 5) {
			String readData = read(nMessage - n);
			if (readData != null) {
				if (data == null)
					data = readData;
				else
					data = data + readData;
				n += readData.length();
			}
			cnt++;
			//System.out.println("FASTRAK : Received data is " + data + "(" + data.length() + ")");
		}
		if (5 <= cnt)
			System.out.println("Fastrak::getMessage : Couldn't read data (" + cnt + ")"); 
		return data;
	} 

	/////////////////////////////////////////////////////////////////
	//	Sample date : 01F   0.89   0.04   1.82  -70.55  41.83 -23.48
	/////////////////////////////////////////////////////////////////

	private void readData() {
	    int msgn;
		for (int n=0; n<getActiveNReceivers(); n++) {
			write(getRecordCommand);
			byte data[] = getMessage(48).getBytes();
			int i;
			if (data.length == 48) {
				for (i=0; i<3; i++) {
					Float value = new Float(new String(data, 3+(7*i), 7));
					mPositionData[n][i] = value.floatValue();
				}
				for (i=0; i<3; i++) {
					Float value = new Float(new String(data, 25+(7*i), 7));
					mRotationData[n][i] = value.floatValue();
				}
			}
		}
		setReceiverUpdateFlag(0xff);
	}

/*
	public static void main(String args[]) {
		Fastrak frak = new Fastrak(Fastrak.SERIALPORT1, 9600);
	    for (int n=0; n<5; n++) {
			float pos[] = new float[3];
			frak.getPosition(1, pos);
			System.out.println("R1 : " + pos[0] + ", " + pos[1] + ", " + pos[2]);
		};
	}
*/
}
