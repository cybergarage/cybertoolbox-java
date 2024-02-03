/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ProgressDialog.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;

public class ProgressDialog extends JFrame {
	
	JLabel			mProgressLabel = null;
	JProgressBar	mProgressBar = null;

	public ProgressDialog(String name, int x, int y, int width, int height) {
		super(name);
//		mFrame.addWindowListener(l);
//		mFrame.getAccessibleContext().setAccessibleDescription("A sample application to demonstrate the Swing UI components");

		JPanel progressPanel = new JPanel() {
		    public Insets getInsets() {
			return new Insets(10,10,10,10);
		    }
		};

		progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
		getContentPane().add(progressPanel, BorderLayout.CENTER);

		Dimension d = new Dimension(300, 20);
		mProgressLabel = new JLabel("Loading, please wait...                                                   ");
		mProgressLabel.setAlignmentX(LEFT_ALIGNMENT);
		mProgressLabel.setPreferredSize(d);
		progressPanel.add(mProgressLabel);
		progressPanel.add(Box.createRigidArea(new Dimension(1,20)));

		mProgressBar = new JProgressBar();
		mProgressLabel.setLabelFor(mProgressBar);
		mProgressBar.setAlignmentX(CENTER_ALIGNMENT);
		progressPanel.add(mProgressBar);

		// show the frame
		setSize(width, height);
		setLocation(x, y);
		show();

	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setMinimum(int value) {
		mProgressBar.setMinimum(value);
	}
	
	public void setMaximum(int value) {
		mProgressBar.setMaximum(value);
	}

	public void setValue(int value) {
		mProgressBar.setValue(value);
	}
	
	public void setText(String text) {
		mProgressLabel.setText(text);
	}
}
