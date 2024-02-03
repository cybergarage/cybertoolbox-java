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
import vrml.j3d.*;
import vrml.node.*;

public class DialogPerspectiveView extends Dialog {
	
	private RenderingStyleComponent	mRenderingStyleComponent	= null;
	private SpeedComponent				mSpeedComponent				= null;
	private HeadlightComponent			mHeadlightComponent			= null;
			
	public DialogPerspectiveView(Frame parentFrame, World world) {
		super(parentFrame, "Perspective View");
		mRenderingStyleComponent	= new RenderingStyleComponent(world);
		mSpeedComponent				= new SpeedComponent(world);
		mHeadlightComponent			= new HeadlightComponent(world);
		JComponent dialogComponent[] = new JComponent[3];
		dialogComponent[0] = mRenderingStyleComponent;
		dialogComponent[1] = mSpeedComponent;
		dialogComponent[2] = mHeadlightComponent;
		setComponents(dialogComponent);
	}
	
	private class RenderingStyleComponent extends JPanel {
	
		private ButtonGroup		mButtonGroup = null;
		private JRadioButton		mRenderingWire;
		private JRadioButton		mRenderingShade;
		private int					mRenderingStyle;
					
		public RenderingStyleComponent(World world) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(BorderFactory.createTitledBorder("Rendering Style"));

			mRenderingStyle = world.getSceneGraph().getRenderingMode();
		
			mButtonGroup = new ButtonGroup();
			ButtonListener buttonListener = new ButtonListener();
			
			mRenderingWire = new JRadioButton("Wireframe", (mRenderingStyle == SceneGraph.RENDERINGMODE_LINE));
			mButtonGroup.add(mRenderingWire);
			mRenderingWire.setMnemonic('w');
			add(mRenderingWire);
			mRenderingWire.addActionListener(buttonListener);

			mRenderingShade = new JRadioButton("Shading", (mRenderingStyle == SceneGraph.RENDERINGMODE_FILL));
			mButtonGroup.add(mRenderingShade);
			mRenderingShade.setMnemonic('s');
			add(mRenderingShade);
			mRenderingShade.addActionListener(buttonListener);
		}
		
		private  class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JComponent c = (JComponent) e.getSource();
				if (c == mRenderingWire)
			    	mRenderingStyle = SceneGraph.RENDERINGMODE_LINE;
				else if (c == mRenderingShade)
			    	mRenderingStyle = SceneGraph.RENDERINGMODE_FILL;
			}
		}
		
		public int getRenderingStyle() {
			return mRenderingStyle;
		}
	}

	private class SpeedComponent extends DialogValueFieldComponent {
	
		public SpeedComponent(World world) {
			super("Navigation Speed");
			SceneGraph sg = world.getSceneGraph();
			setValue(sg.getNavigationSpeed());
		}
	}

	private class HeadlightComponent extends JPanel {
	
		private ButtonGroup		mButtonGroup = null;
		private JRadioButton		mTrueButton;
		private JRadioButton		mFalseButton;
		private boolean			mValue;
					
		public HeadlightComponent(World world) {
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(BorderFactory.createTitledBorder("Headlight"));
		
			mValue = world.getSceneGraph().isHeadlightOn();
			
			mButtonGroup = new ButtonGroup();
			
			Dimension spaceDim = new Dimension(10,1);
			
			ButtonListener buttonListener = new ButtonListener();
			
//			add(Box.createRigidArea(spaceDim));
			
			mTrueButton = new JRadioButton("ON", mValue);
			mButtonGroup.add(mTrueButton);
			mTrueButton.setMnemonic('o');
			add(mTrueButton);
			mTrueButton.addActionListener(buttonListener);

//			add(Box.createRigidArea(spaceDim));
			
			mFalseButton = new JRadioButton("OFF", !mValue);
			mButtonGroup.add(mFalseButton);
			mFalseButton.setMnemonic('f');
			add(mFalseButton);
			mFalseButton.addActionListener(buttonListener);
			
//			add(Box.createRigidArea(spaceDim));
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
		
		public boolean isHeadlightOn() {
			return mValue;
		}
	}
	
	public int getRenderingStyle() {
		return mRenderingStyleComponent.getRenderingStyle();
	}

	public float getNavigationSpeed() {
		return (float)mSpeedComponent.getValue();
	}

	public boolean isHeadlightOn() {
		return mHeadlightComponent.isHeadlightOn();
	}
}
		
