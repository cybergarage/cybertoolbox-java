/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Script.java
*
******************************************************************/

package vrml.node;

import vrml.*;
import vrml.field.*;

public class Script extends BaseNode { 
 
	public Script() {
		setScriptNode(null);
	}

	public Script(ScriptNode script) {
		setScriptNode(script);
	}
	
	////////////////////////////////////////////////
	//	ScriptNode
	////////////////////////////////////////////////
	
	private ScriptNode mScriptNode;
	
	public void setScriptNode(ScriptNode script) {
		mScriptNode = script;
	}

	public ScriptNode getScriptNode() {
		return mScriptNode;
	}
		
	////////////////////////////////////////////////
	//	Basic methods
	////////////////////////////////////////////////

	// This method is called before any event is generated
	public void initialize() {
	}   

	// Get a Field by name.
	//   Throws an InvalidFieldException if fieldName isn't a valid
	//   field name for a node of this type.
	protected final Field getField(String fieldName) {
		if (mScriptNode != null)
			return mScriptNode.getField(fieldName);
		throw new InvalidFieldException(fieldName + " is not found.");
//		return null;
	}

	// Get an EventOut by name.
	//   Throws an InvalidEventOutException if eventOutName isn't a valid
	//   eventOut name for a node of this type.
	protected final Field getEventOut(String eventOutName) {
		if (mScriptNode != null) {
			ConstField constField = mScriptNode.getEventOut(eventOutName);
			if (constField != null)
				return constField.createReferenceFieldObject();
			else
				return null;
		}
		throw new InvalidEventOutException(eventOutName + " is not found.");
//		return null;
	}

	// Get an EventIn by name.
	//   Throws an InvalidEventInException if eventInName isn't a valid
	//   eventIn name for a node of this type.
	protected final Field getEventIn(String eventInName) {
		if (mScriptNode != null) 
			return mScriptNode.getEventIn(eventInName);
		throw new InvalidEventInException(eventInName + " is not found.");
//		return null;
	}

	// processEvents() is called automatically when the script receives 
	//   some set of events. It shall not be called directly except by its subclass.
	//   count indicates the number of events delivered.
	public void processEvents(int count, Event events[]) {
	}

	// processEvent() is called automatically when the script receives 
	// an event.    
	
	public void processEvent(Event event) {
	}
	
	// eventsProcessed() is called after every invocation of processEvents().
	public void eventsProcessed() {
	}

	// shutdown() is called when this Script node is deleted.
	public void shutdown() {
	}

   // This overrides a method in Object}
	public String toString() {
		return "Script";
	} 
}
