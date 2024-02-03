/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogModuleStringBool.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class DialogModuleStringBool extends Dialog {

	private BooleanValueComponent mBooleanValueComponent = null;
	
	public DialogModuleStringBool(Frame parentFrame, Module module) {
		super(parentFrame, "Module String::Bool");
		mBooleanValueComponent = new BooleanValueComponent(module);
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = mBooleanValueComponent;
		setComponents(dialogComponent);
	}
	
	public class BooleanValueComponent extends JPanel {
	
		private ButtonGroup		mButtonGroup = null;
		private JRadioButton		mTrueButton;
		private JRadioButton		mFalseButton;
		private boolean			mValue;
					
		public BooleanValueComponent(Module module) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(BorderFactory.createTitledBorder("Boolean value"));
		
			String value = module.getValue();
			mValue = value.equalsIgnoreCase("true");
			
			mButtonGroup = new ButtonGroup();
			
			Dimension spaceDim = new Dimension(10,1);
			
			ButtonListener buttonListener = new ButtonListener();
			
			add(Box.createRigidArea(spaceDim));
			
			mTrueButton = new JRadioButton("true", mValue);
			mButtonGroup.add(mTrueButton);
			mTrueButton.setMnemonic('t');
			add(mTrueButton);
			mTrueButton.addActionListener(buttonListener);

			add(Box.createRigidArea(spaceDim));
			
			mFalseButton = new JRadioButton("false", !mValue);
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
			return ((mValue == true)? "true" : "false");
		}
	}
	
	public String getBooleanValue() {
		return mBooleanValueComponent.getBooleanValue();
	}
}
		
