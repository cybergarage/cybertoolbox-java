/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldTreeCellRenderer.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

import vrml.*;
import vrml.node.*;
import vrml.util.Debug;

public class WorldTreeCellRenderer extends JLabel implements WorldConstants, TreeCellRenderer {

	static private		Font				mDefaultFont;
	static private		Color				mSelectedBackgroundColor = Color.yellow;

	static {
		try {
			mDefaultFont = new Font("SansSerif", Font.BOLD, 12);
		} catch (Exception e) {}
	}

	static public ImageIcon getImageIcon(WorldTreeData treeData) {
	
		if (treeData == null)
			return null;
		
		Node node = treeData.getNode();
		
		return NodeImage.getImageIcon(node);
	}
	
	public WorldTreeCellRenderer() {
	}
	
	private	boolean	selected;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		String          stringValue = tree.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		WorldTreeData	treeData = (WorldTreeData)((DefaultMutableTreeNode)value).getUserObject();

		setFont(mDefaultFont);

		setText(stringValue + "    ");
		setToolTipText(stringValue);

		setIcon(getImageIcon(treeData));

		if(hasFocus)
		    setForeground(Color.blue);
		else
			setForeground(Color.black);

		this.selected = selected;
	
		return this;
    }

    public void paint(Graphics g) {
		Color            bColor;
		Icon             currentI = getIcon();

		if(selected)
			bColor = mSelectedBackgroundColor;
		else if(getParent() != null)
		    bColor = getParent().getBackground();
		else
			bColor = getBackground();

		g.setColor(bColor);
		
		if(currentI != null && getText() != null) {
			int offset = (currentI.getIconWidth() + getIconTextGap());
			g.fillRect(getX() + offset, getY(), getWidth() - 1 - offset, getHeight() - 1);
		}
		else
			g.fillRect(getX(), getY(), getWidth()-1, getHeight()-1);
		
		super.paint(g);
    }

}
