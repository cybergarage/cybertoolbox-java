/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Dialog.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class Dialog extends JDialog {

	public static final int	OK_OPTION = 0;
	public static final int	CANCEL_OPTION = -1;
	
	private JDialog			mDialog = null;
	private Object				mOptions[] = null;
	private int					mValue = CANCEL_OPTION;
	private boolean			mOnlyOKButton = false;
	private ComponentPanel	mComponentPanel = null;
	
	public Dialog(Frame parentComponent, String title, JComponent components[]) {
		super(parentComponent, title, true);
		setComponents(components);
		setLocationRelativeTo(parentComponent);
		setValue(CANCEL_OPTION);
	}

	public Dialog(Frame parentComponent, String title, boolean onlyOKButton) {
		super(parentComponent, title, true);
		setLocationRelativeTo(parentComponent);
		setValue(CANCEL_OPTION);
		mOnlyOKButton = onlyOKButton;		
	}

	public Dialog(Frame parentComponent, String title) {
		super(parentComponent, title, true);
		setLocationRelativeTo(parentComponent);
		setValue(CANCEL_OPTION);
	}

	public Dialog(Frame parentComponent) {
		super(parentComponent, "", true);
		setLocationRelativeTo(parentComponent);
		setValue(CANCEL_OPTION);
	}
	
	public void setComponents(JComponent components[]) {
		mComponentPanel = new ComponentPanel(components, new BoxLayout(this, BoxLayout.Y_AXIS));
		getContentPane().add(mComponentPanel);
	}

	public void setComponents(JComponent components[], LayoutManager layoutMgr) {
		mComponentPanel = new ComponentPanel(components, layoutMgr);
		getContentPane().add(mComponentPanel);
	}
	
	public void setPreferredSize(Dimension dim) {
		mComponentPanel.setPreferredSize(dim);
	}
	
	public boolean isOnlyOKButton() {
		return mOnlyOKButton;
	}
	
	private class ComponentPanel extends JPanel {
		
		private ConfirmComponent mConfirmComponent;
		
		public ComponentPanel(JComponent components[], LayoutManager layoutMgr) {
			setBorder(new EmptyBorder(5, 5, 0, 5));
			setLayout(layoutMgr);
			for (int n=0; n<components.length; n++)
				add(components[n]);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			mConfirmComponent = new ConfirmComponent(); 
			add(mConfirmComponent);
		}
		
		public ConfirmComponent getComfirmComponent() {
			return mConfirmComponent;
		}
	}
	
	private class ConfirmComponent extends JPanel {
		
		private OkButton		mOkButton;
		private CancelButton	mCancelButton;
		
		public ConfirmComponent() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
			setBorder(new EmptyBorder(5, 5, 5, 5));
			
			mOkButton = new OkButton();
			add(mOkButton);
			if (isOnlyOKButton() == false) {
				mCancelButton = new CancelButton();
				add(mCancelButton);
			}
			//add(new OkButton(new ImageIcon(getUserDir() + "ok.gif", "OK")));
			//add(new CancelButton(new ImageIcon(getUserDir() + "cancel.gif", "Cancel")));
		}

		public String getUserDir() {
			String separator = System.getProperty("file.separator");
			return System.getProperty("user.dir") + separator + "images" + separator + "dialog" + separator;
		}
			
		private class OkButton extends JButton implements ActionListener {
			public OkButton() {
				super("OK");
				addActionListener(this);
			}
			public OkButton(Icon icon) {
				super(icon);
				addActionListener(this);
			}
			public void actionPerformed(ActionEvent e) {
				setValue(OK_OPTION);
				dispose();
			}
		}

		private class CancelButton extends JButton implements ActionListener {
			public CancelButton() {
				super("Cancel");
				addActionListener(this);
			}
			public CancelButton(Icon icon) {
				super(icon);
				addActionListener(this);
			}
			public void actionPerformed(ActionEvent e) {
				setValue(CANCEL_OPTION);
				dispose();
			}
		}
		
		public void setOkButtonEnabled(boolean on) {
			mOkButton.setEnabled(on);
		}

		public boolean isOkButtonEnabled() {
			return mOkButton.isEnabled();
		}
 	}
	
	public void setOkButtonEnabled(boolean on) {
		if (mComponentPanel == null)
			return;
		mComponentPanel.getComfirmComponent().setOkButtonEnabled(on);		
	}

	public boolean isOkButtonEnabled() {
		if (mComponentPanel == null)
			return false;
		return mComponentPanel.getComfirmComponent().isOkButtonEnabled();		
	}
	
	public void setValue(int value) {
		mValue = value;
	}
	
	public int getValue() {
		return mValue;
	}
	
	public int doModal() {
		pack();
		setVisible(true);
		return getValue();
	}

	public int doModal(int width, int height) {
		setSize(width, height);
		pack();
		setVisible(true);
		return getValue();
	}
}
