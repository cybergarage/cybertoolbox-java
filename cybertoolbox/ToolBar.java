/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ToolBar.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;

public class ToolBar extends JToolBar {

    public ToolBar () {
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        updateUI();
		setBorderPainted(true);
		setFloatable(false);
	}

	public String getUserDir() {
		return System.getProperty("user.dir") + System.getProperty("file.separator");
	}
			
	public JButton createToolBarButton(String name, String filename, Action action) {
		JButton b = new JButton(new ImageIcon(filename, name));
		b.setToolTipText(name);
		b.setMargin(new Insets(0,0,0,0));
		if (action != null)
			b.addActionListener(action);
		return b;
	}

	public JButton addToolBarButton(String name, String filename, Action action) {
		JButton b = createToolBarButton(name, filename, action);
		add(b);
		return b;
	}	
}
