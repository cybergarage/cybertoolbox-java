/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Undo.java
*
******************************************************************/

import java.util.Vector;

public class Undo extends Object {
	
	private int mUndoLevel;
	private Vector mUndoObject = new Vector();
	
	public Undo(int undoLevel) {
		setUndoQueueLevel(undoLevel);
	}

	private void setUndoQueueLevel(int undoLevel) { 
		mUndoLevel = undoLevel; 
	}
	
	private int getUndoQueueLevel() { 
		return mUndoLevel; 
	}
	
	private int getNQueues() {
		return mUndoObject.size();
	}
	
	public void add(UndoObject undoObject) {
		mUndoObject.addElement(undoObject);
		if (getUndoQueueLevel() < getNQueues())
			mUndoObject.removeElement(mUndoObject.firstElement());
	}
	
	public boolean undo() {
		try {
			Object lastElement = mUndoObject.lastElement();
			if (lastElement == null)
				return false;
			UndoObject undoObject = (UndoObject)lastElement;
			undoObject.undo();
			mUndoObject.removeElement(undoObject);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
