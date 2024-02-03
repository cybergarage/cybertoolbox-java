/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : MField.java
*
******************************************************************/

package vrml;

import java.io.PrintWriter;
import java.util.Vector;

import vrml.field.StringValue;
import vrml.util.Debug;

public abstract class MField extends Field {

	private Vector	mFieldVector = new Vector();

	public MField() {
	}

	public MField(MField field) {
		setObject(field.getObject());
	}
	
	public MField(ConstMField constMField) {
		setObject(constMField.getObject());
	}

	public void setObject(Object object) {
		mFieldVector = (Vector)object;
	}

	public Object getObject() {
		return mFieldVector;
	}
	
	public int getSize() {
		return mFieldVector.size();
	}

	public void add(Object object) {
		mFieldVector.addElement(object);
	}
	
	public void add(Field field) {
		add((Object)field);
	}

	public abstract void addValue(String string);

	public void add(String string){
		addValue(string);
	}
	
	public abstract void insertValue(int index, String string);
	
	public void insert(int index, Object object) {
		mFieldVector.insertElementAt(object, index);
	}

	public void insert(int index, Field field) {
		insert(index, (Object)field);
	}

	public void insert(int index, String string){
		insertValue(index, string);
	}
	
	public void clear() {
		mFieldVector.removeAllElements();
	}

	public void delete(int index) {
		mFieldVector.removeElementAt(index);
	}

	public void remove(int index) {
		mFieldVector.removeElementAt(index);
	}

	public void removeValue(int index) {
		remove(index);
	}
	
	public void removeAllValues() {
		clear();
	}

	public Object get(int index) {
		return mFieldVector.elementAt(index);
	}

	public Field getField(int index) {
		return (Field)get(index);
	}

	public void set(int index, Object object) {
		mFieldVector.setElementAt(object, index);
	}
	
	public void setField(int index, Field field) {
		set(index, (Object)field);
	}

	public void setBaseNode(int index, BaseNode node) {
		set(index, (Object)node);
	}

	public void copy(MField srcMField) {
		clear();
		for (int n=0; n<srcMField.getSize(); n++) {
			add(srcMField.get(n));
		}
	}

	public void copy(ConstMField srcMField) {
		clear();
		for (int n=0; n<srcMField.getSize(); n++) {
			add(srcMField.get(n));
		}
	}

	abstract public void outputContext(PrintWriter printStream, String indentString);

	////////////////////////////////////////////////
	//	setValue 
	////////////////////////////////////////////////

	public void setValue(String string) {
		Debug.message("MField::setValue = " + string);
		StringValue value = new StringValue(string, ",", " ");
		String token[] = value.getTokens();
		setValue(token);
	}

	public void setValue(String value[]) {
		if (value == null)
			return;
		clear();
		int size = value.length;
		Debug.message("MField::setValue = " + size);
		for (int n=0; n<size; n++) {
			Debug.message("\t[" + n + "] = \"" + value[n] + "\"");
			add(value[n]);
		}
	}
};

