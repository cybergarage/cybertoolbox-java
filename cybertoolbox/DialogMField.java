/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogMField.java
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

public class DialogMField extends Dialog implements vrml.Constants {
	
	private FieldTableComponent		mFieldTableComponent = null;
	private MField							mMField = null;
	private Frame							mParentFrame;
	
	public DialogMField(Frame parentFrame, MField mfield) {
		super(parentFrame, mfield.getName());
		
		mParentFrame = parentFrame;
		mMField = mfield;
		
		mFieldTableComponent = new FieldTableComponent(mfield);
		mFieldTableComponent.setPreferredScrollableViewportSize(new Dimension(200, 200));
		JComponent dialogComponent[] = new JComponent[2];
		dialogComponent[0] = new JScrollPane(mFieldTableComponent);
		dialogComponent[1] = new AddDeleteComponent(mFieldTableComponent);
		setComponents(dialogComponent);
	}
	
	public Dialog getParentDialog() {
		return this;
	}
	
	public Frame getDialogParentFrame() {
		return mParentFrame;
	}
	
	public String[] getFieldValues() {
		return mFieldTableComponent.getFieldValues();
	}
													
	///////////////////////////////////////////////
	//	FieldTableComponent
	///////////////////////////////////////////////
	
	private class FieldTableComponent extends JTable implements MouseListener {
		
		private MField				mMField = null;

		public FieldTableComponent(MField mfield) {
			mMField = mfield;
			setModel(new FieldTableModel(mfield));
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
			return mMField.getField(nRow);
		}

		public FieldTableModel getFieldModel() {
			return (FieldTableModel)getModel();
		}
		
		public String getFieldText(int nRow) {
			return (String)getFieldModel().getValueAt(nRow, 1);
		}
				
		public String[] getFieldValues() {
			FieldTableModel fieldTableModel = getFieldModel();
			int nFields = fieldTableModel.getRowCount();
			if (nFields <= 0)
				return null;
			String fieldValues[] = new String[nFields];
			for (int n=0; n<nFields; n++) {
				String fieldValue = (String)fieldTableModel.getValueAt(n, 1);
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

		///////////////////////////////////////////////
		//	MouseListener
		///////////////////////////////////////////////
	
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() != 2)
				return;
			
			Point point = new Point(e.getX(), e.getY());
			int nColumn = columnAtPoint(point);

//			if (nColumn != 1)
//				return;
			
			int 		nRow = rowAtPoint(point);
			Field		rowField	= getField(nRow);
			
			if (rowField.isSingleField() == true) {
				String settingValue = openSingleFieldDialog(nRow);
				if (settingValue != null)
					setFieldText(nRow, settingValue);
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
	}

	///////////////////////////////////////////////
	//	FieldTableModel
	///////////////////////////////////////////////
		
	public class FieldTableModel extends DefaultTableModel implements TableModel { // extends AbstractTableModel {
			
		private EventListenerList listenerList = new EventListenerList();
		private String mColumnName[] = {"[n]", "Value"};
		private String mFieldValue[][] = null;
		private MField mMField = null;
			
		public FieldTableModel(MField mfield) {
			mMField = mfield;
			resetTableModeleData();
		}
			
		public MField getMField() {
			return mMField;
		}
			
		public void resetTableModeleData() {
			int mfieldSize = mMField.getSize();
			mFieldValue = new String[mfieldSize][2];
			for (int n=0; n<mfieldSize; n++) {
				Field field = mMField.getField(n);
				if (field != null) {
					mFieldValue[n][0] = "[" + Integer.toString(n) + "]";
					mFieldValue[n][1] = field.toString();
				}
			}
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
			if (mMField == null)
				return 0;
			return mMField.getSize();
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

	///////////////////////////////////////////////
	//	AddDeleteComponent
	///////////////////////////////////////////////
	
	private class AddDeleteComponent extends JPanel {
		
		private JTable	mTable = null;
		
		public AddDeleteComponent(JTable table) {
			mTable = table;
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
			//setBorder(new EmptyBorder(1, 1, 1, 1));
			//setBorder(new LineBorder(new Color(1, 1, 1)));
			setBorder(new EtchedBorder());
			add(new AddButton());
			add(new DeleteButton());
		}
		
		public FieldTableModel getFieldTableModel() {
			return (FieldTableModel)mTable.getModel();
		}
		
		public int getSelectedRow() {
			return mTable.getSelectedRow();
		} 
		
		private class AddButton extends JButton implements ActionListener {
			public AddButton() {
				super("add");
				addActionListener(this);
			}
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				
				FieldTableModel fieldTableModel = getFieldTableModel();
				MField mfield = fieldTableModel.getMField();
				
				mfield.insert(row+1, new String());
				fieldTableModel.resetTableModeleData();
				
				mTable.setModel(new FieldTableModel(mfield));
			}
		}

		private class DeleteButton extends JButton implements ActionListener {
			public DeleteButton() {
				super("delete");
				addActionListener(this);
			}
			public void actionPerformed(ActionEvent e) {
				int row = getSelectedRow();
				
				FieldTableModel fieldTableModel = getFieldTableModel();
				MField mfield = fieldTableModel.getMField();
				
				mfield.remove(row);
				fieldTableModel.resetTableModeleData();
				
				mTable.setModel(new FieldTableModel(mfield));
			}
		}
 	}
}
		
