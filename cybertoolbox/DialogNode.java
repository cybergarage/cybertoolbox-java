/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogNode.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;

public class DialogNode extends Dialog implements vrml.Constants {
	
	private DialogTextFieldComponent mNameTextFieldComponent = null;
	private FieldTableComponent			mFieldTableComponent = null;
	private Node									mNode = null;
	private Frame								mParentFrame;
	
	public DialogNode(Frame parentFrame, Node node) {
		super(parentFrame, "Node");
		
		mParentFrame = parentFrame;
		mNode = node;
		
		mNameTextFieldComponent = new DialogTextFieldComponent("Name");
		String nodeName = node.getName();
		if (nodeName != null)
			mNameTextFieldComponent.setText(nodeName);
		mFieldTableComponent = new FieldTableComponent(node);
		mFieldTableComponent.setPreferredScrollableViewportSize(new Dimension(300, 200));
		JScrollPane fieldScrollPane = new JScrollPane(mFieldTableComponent);
		JComponent dialogComponent[] = new JComponent[2];
		dialogComponent[0] = mNameTextFieldComponent;
		dialogComponent[1] = fieldScrollPane;
		setComponents(dialogComponent);
	}
	
	public Dialog getParentDialog() {
		return this;
	}
	
	public Frame getDialogParentFrame() {
		return mParentFrame;
	}
	
	public String getNodeName() {
		return mNameTextFieldComponent.getText();
	}

	public String[] getFieldValues() {
		return mFieldTableComponent.getFieldValues();
	}
													
	private class FieldTableComponent extends JTable implements MouseListener {
		private FieldTableModel	mFieldTableModel = null;
		private Node				mNode = null;

		public FieldTableComponent(Node node) {
			mNode = node;
			mFieldTableModel = new FieldTableModel(node);
			setModel(mFieldTableModel);
			setBorder(new LineBorder(Color.black));

			setCellSelectionEnabled(false); ;
			setColumnSelectionAllowed(false); ;
			setRowSelectionAllowed(true);
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			addMouseListener(this);
		}

		public void setFieldText(int nRow, String text) {
			setValueAt(text, nRow, 1);
		}
		
		public Field getField(int nRow) {
			int nFields = mNode.getNFields();
			if (nRow < nFields)
				return mNode.getField(nRow);
			return mNode.getExposedField(nRow - nFields);
		}

		public String getFieldText(int nRow) {
			return (String)mFieldTableModel.getValueAt(nRow, 1);
		}
				
		public String[] getFieldValues() {
			int nFields = mFieldTableModel.getRowCount();
			if (nFields <= 0)
				return null;
			String fieldValues[] = new String[nFields];
			for (int n=0; n<nFields; n++) {
				String fieldValue = (String)mFieldTableModel.getValueAt(n, 1);
				if (fieldValue != null)
					fieldValues[n] = new String(fieldValue);
				else 
					fieldValues[n] = null;
			}
			return fieldValues;
		}

		public void setDialogPosition(Dialog dialog) {
			Dimension compDim = getParentDialog().getSize();
			Point compPos = getParentDialog().getLocation();
			Dimension dlgDim = dialog.getSize();
			int x = compPos.x + (compDim.width/2) - (dlgDim.width/2);
			int y = compPos.y + (compDim.height/2) - (dlgDim.height/2);
			dialog.setLocation(x, y);
		}
		
		public String openSingleFieldDialog(int nRow) {
			Field		rowField	= getField(nRow);
			String	rowText	= getFieldText(nRow);
	
			DialogSField dialog = null;
					
			switch (rowField.getType()) {
			case fieldTypeSFString:
				{
					SFString field = new SFString(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFString(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFBool:
				{
					SFBool field = new SFBool(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFBool(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFInt32:
				{
					SFInt32 field = new SFInt32(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFInt32(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFFloat:
				{
					SFFloat field = new SFFloat(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFFloat(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFTime:
				{
					SFTime field = new SFTime(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFTime(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFVec2f:
				{
					SFVec2f field = new SFVec2f(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFVec2f(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFVec3f:
				{
					SFVec3f field = new SFVec3f(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFVec3f(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFColor:
				{
					SFColor field = new SFColor(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFColor(getDialogParentFrame(), field);
				}
				break;
			case fieldTypeSFRotation:
				{
					SFRotation field = new SFRotation(rowText);
					field.setName(rowField.getName());
					dialog = new DialogSFRotation(getDialogParentFrame(), field);
				}
				break;
			}
			
			if (dialog == null)
				return null;
				
			setDialogPosition(dialog);
			if (dialog.doModal() == Dialog.OK_OPTION) 
				return dialog.getStringValue();
			
			return null;
		}

		public String openMultiFieldDialog(int nRow) {
			Field		field		= getField(nRow);
			String	rowText	= getFieldText(nRow);

			MField mfield = null;
			
			switch (field.getType()) {
			case fieldTypeMFString:
				{
					mfield = new MFString();
				}
				break;
			case fieldTypeMFInt32:
				{
					mfield = new MFInt32();
				}
				break;
			case fieldTypeMFFloat:
				{
					mfield = new MFFloat();
				}
				break;
			case fieldTypeMFVec2f:
				{
					mfield = new MFVec2f();
				}
				break;
			case fieldTypeMFVec3f:
				{
					mfield = new MFVec3f();
				}
				break;
			case fieldTypeMFColor:
				{
					mfield = new MFColor();
				}
				break;
			case fieldTypeMFRotation:
				{
					mfield = new MFRotation();
				}
				break;
			}
	
			if (mfield == null)
				return null;
			
			Debug.message("rowText = " + rowText);
			
			mfield.setValue(rowText);
			mfield.setName(field.getName());
			
			DialogMField dialog = new DialogMField(getDialogParentFrame(), mfield);
			setDialogPosition(dialog);
			if (dialog.doModal() == Dialog.OK_OPTION) {
				String fieldValues[] = dialog.getFieldValues();
				StringBuffer settingValue = new StringBuffer();
				int nFieldValues = fieldValues.length;
				for (int n=0; n<nFieldValues; n++) {
					settingValue.append(fieldValues[n]);
					if (n < (nFieldValues - 1))
						settingValue.append(", ");
				}
				return settingValue.toString();
			}
			return null;
		}

		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() != 2)
				return;
			
			Point point = new Point(e.getX(), e.getY());
			int nColumn = columnAtPoint(point);

//			if (nColumn != 1)
//				return;
			
			int 		nRow = rowAtPoint(point);
			Field		rowField	= getField(nRow);
			
			Debug.message("Selected field = " + rowField.getName());
			
			String settingValue = null;
			if (rowField.isSingleField() == true)
				settingValue = openSingleFieldDialog(nRow);
			else
				settingValue = openMultiFieldDialog(nRow);
			
			if (settingValue != null)
				setFieldText(nRow, settingValue);
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public class FieldTableModel extends DefaultTableModel implements TableModel { // extends AbstractTableModel {
			private EventListenerList listenerList = new EventListenerList();
			private String mColumnName[] = {"Field", "Value"};
			private String mFieldValue[][] = null;
			private Node mNode = null;
			
			private void setFieldValue(int nField, Field field) {
				String fieldName = field.getName();
				mFieldValue[nField][0] = new String(fieldName);
					if (field.isSingleField() == true) {
						String fieldValue = field.toString();
						if (fieldValue != null)
							mFieldValue[nField][1] = new String(fieldValue);
						else
							mFieldValue[nField][1] = null;
					}
					else {
						MField mfield = (MField)field;
						StringBuffer fieldValues = new StringBuffer();
						int mfieldSize = mfield.getSize();
						for (int n=0; n<mfieldSize; n++) {
							String fieldValue = mfield.getField(n).toString();
							fieldValues.append(fieldValue);
							if (n < (mfieldSize-1))
								fieldValues.append(", ");
						}
						mFieldValue[nField][1] = fieldValues.toString();
					}
			}
			
			public FieldTableModel(Node node) {
				mNode = node;
				int nFields = node.getNFields();
				int nExposedFields = node.getNExposedFields();
				mFieldValue = new String[nExposedFields + nFields][2];
				for (int n=0; n<nFields; n++) 
					setFieldValue(n, node.getField(n)); 
				for (int n=0; n<nExposedFields; n++) 
					setFieldValue(n+nFields, node.getExposedField(n)); 
			}
			
			public void addTableModelListener(TableModelListener l) {
				listenerList.add(TableModelListener.class, l);
			}

			public Class getColumnClass(int columnIndex)  {
				return Object.class;
			}
		
			public int getColumnCount() {
				return mColumnName.length;
			}
		
			public String getColumnName(int columnIndex) {
				return mColumnName[columnIndex];
			}
		
			public int getRowCount() {
				if (mNode != null)
					return mNode.getNFields() + mNode.getNExposedFields();
				return 0;
			}
		
			public Object getValueAt(int rowIndex, int columnIndex) {
				if (mFieldValue != null)
					return mFieldValue[rowIndex][columnIndex];
				return null;
			}
			
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		
			public void removeTableModelListener(TableModelListener l) {
				listenerList.remove(TableModelListener.class, l);
			}
		
			public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex) {
				mFieldValue[rowIndex][columnIndex] = new String((String)aValue);
			}
		}

	}
}
		
