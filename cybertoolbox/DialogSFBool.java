/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogSFBool.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.field.*;

public class DialogSFBool extends DialogSField {
	
	private BooleanValueComponent mBooleanValueComponent = null;
	
	public DialogSFBool(Frame parentFrame, SFBool field) {
		super(parentFrame, field);
		mBooleanValueComponent = new BooleanValueComponent(field);
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mBooleanValueComponent;
		setComponents(dialogComponent);
	}
	
	public class BooleanValueComponent extends JPanel {
	
		private ButtonGroup		mButtonGroup = null;
		private JRadioButton		mTrueButton;
		private JRadioButton		mFalseButton;
		private boolean			mValue;
					
		public BooleanValueComponent(SFBool field) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(BorderFactory.createTitledBorder("Boolean value"));
		
			mValue = field.getValue();
			
			mButtonGroup = new ButtonGroup();
			
			Dimension spaceDim = new Dimension(10,1);
			
			ButtonListener buttonListener = new ButtonListener();
			
			add(Box.createRigidArea(spaceDim));
			
			mTrueButton = new JRadioButton("TRUE", mValue);
			mButtonGroup.add(mTrueButton);
			mTrueButton.setMnemonic('t');
			add(mTrueButton);
			mTrueButton.addActionListener(buttonListener);

			add(Box.createRigidArea(spaceDim));
			
			mFalseButton = new JRadioButton("FALSE", !mValue);
			mButtonGroup.add(mFalseButton);
			mFalseButton.setMnemonic('f');
			add(mFalseButton);
			mFalseButton.addActionListener(buttonListener);
			
			add(Box.createRigidArea(spaceDim));
		}
		
		private  class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JComponent c = (JComponent) e.getSource();
				if (c == mTrueButton)
			    	mValue = true;
				else if (c == mFalseButton)
			    	mValue = false;
			}
		}
		
		public String getBooleanValue() {
			return ((mValue == true)? "TRUE" : "FALSE");
		}
	}
	
	public String getStringValue() {
		return mBooleanValueComponent.getBooleanValue();
	}
}
		
