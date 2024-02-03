/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File: DiagramFrame.java
*
******************************************************************/

import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import javax.swing.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.route.*;
import vrml.util.*;

/******************************************************************
*
*	DiagramPanel
*
******************************************************************/

public class DiagramPanel extends JPanel implements WorldConstants, ModuleConstants, MouseListener, MouseMotionListener, KeyListener {

	public static final int OPERATIONMODE_NONE									= 0x00;
	public static final int OPERATIONMODE_MOVING_MODULE					= 0x01;
	public static final int OPERATIONMODE_SELECTING_BOX					= 0x02;
	public static final int OPERATIONMODE_MOVING_SELECTING_BOX		= 0x04;
	public static final int OPERATIONMODE_SELECTING_MODULE				= ModuleConstants.MODULE_INSIDE;
	public static final int OPERATIONMODE_SELECTING_MODULE_INNODE	= ModuleConstants.MODULE_INSIDE_INNODE;
	public static final int OPERATIONMODE_SELECTING_MODULE_OUTNODE	= ModuleConstants.MODULE_INSIDE_OUTNODE;

	public static final int DRAWMODE_NORMAL										= 0x00;
	public static final int DRAWMODE_DRAGGING_MODULE						= 0x01;
	public static final int DRAWMODE_DRAGGING_MODULELINE					= 0x02;
	public static final int DRAWMODE_DRAGGING_SELECTINGBOX				= 0x04;
	public static final int DRAWMODE_MOVING_SELECTINGBOX					= 0x08;

	public static final int LINE_STYLE_STRAIGHT	= 0x00;
	public static final int LINE_STYLE_ZIGZAG		= 0x01;

	private boolean	mGridSnap;
	private boolean	mGridDisplay;
	private int			mLineStyle;
	private int			mGridSize;

	private DiagramFrame	mDiagramFrame				= null;
	private TransformNode	mDiagramNode					= null;
	private World				mWorld							= null;
	private ScriptNode		mSelectingModuleNode	= null;
	private int					mMode							= OPERATIONMODE_NONE;
	
	public DiagramPanel(DiagramFrame diagramFrame, World world, TransformNode dgmNode) {
		setDiagramFrame(diagramFrame);
		setWorld(world);
		setDiagramNode(dgmNode);
		setDrawMode(DRAWMODE_NORMAL);

		setLayout(new BorderLayout());
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		//addKeyListener(this);

		setGridDisplay(true);
		setGridSnap(true);
		setLineStyle(LINE_STYLE_ZIGZAG);
		setGridSize(MODULE_SIZE/4);
		
		resetComponentSize();
	}

	//////////////////////////////////////////////////
	// World
	//////////////////////////////////////////////////

	public void setWorld(World world) {
		mWorld = world;
	}

	public World getWorld() {
		return mWorld;
	}

	//////////////////////////////////////////////////
	// Diagram Node
	//////////////////////////////////////////////////

	public void	setDiagramNode(TransformNode node) {
		mDiagramNode = node;
	}
	
	public TransformNode getDiagramNode() {
		return mDiagramNode;
	}

	//////////////////////////////////////////////////
	// Diagram Frame
	//////////////////////////////////////////////////

	public void	setDiagramFrame(DiagramFrame diagramFrame) {
		mDiagramFrame = diagramFrame;
	}
	
	public DiagramFrame getDiagramFrame() {
		return mDiagramFrame;
	}

	public Frame getTopFrame() {
		return mDiagramFrame.getFrame();
	}

	//////////////////////////////////////////////////
	// Undo
	//////////////////////////////////////////////////
	
	private void addUndoAction(UndoObject undoObject) {
		getDiagramFrame().addUndoAction(undoObject);
	}
	
	private void undo() {
		getDiagramFrame().undo();
	}

	//////////////////////////////////////////////////
	// Line Style
	//////////////////////////////////////////////////

	public void setLineStyle(int style) {
		mLineStyle = style; 
	}
	
	public int getLineStyle() { 
		return mLineStyle; 
	}

	//////////////////////////////////////////////////
	// Grid
	//////////////////////////////////////////////////
	
	private void setGridSnap(boolean on) { 
		mGridSnap = on; 
	}
	
	public boolean getGridSnap() { 
		return mGridSnap; 
	}

	private void setGridDisplay(boolean on) { 
		mGridDisplay = on; 
	}
	
	public boolean getGridDisplay() { 
		return mGridDisplay; 
	}

	private void setGridSize(int size) {
		mGridSize = size; 
	}
	
	public int getGridSize() { 
		return mGridSize; 
	}

	public int toGridSnapPosition(int pos) {
		if (getGridSnap() == true) 
			pos -= pos % getGridSize();
		return pos;
	}

	//////////////////////////////////////////////////
	// Selecting Box
	//////////////////////////////////////////////////

	private boolean	mSelectingBox;
	private Rectangle	mSelectingBoxRect = new Rectangle();
	
	private void selectingBoxOn() {
		mSelectingBox = true;
	}
	
	private void selectingBoxOff() {
		mSelectingBox = false;
	}
	
	private boolean isSelectingBoxOn() {
		return mSelectingBox;
	}

	private void setSelectingBoxRect(int x0, int y0, int x1, int y1) {
		if (x1 < x0) {
			int tmp = x0;
			x0 = x1;
			x1 = tmp;
		}
		if (y1 < y0) {
			int tmp = y0;
			y0 = y1;
			y1 = tmp;
		}
		mSelectingBoxRect.setRect(x0, y0, x1-x0, y1-y0);
	}

	private void setSelectingBoxLocation(int x, int y) {
		mSelectingBoxRect.setLocation(new Point(x, y));
	}

	private Rectangle getSelectingBoxRectangle() {
		return mSelectingBoxRect;
	}

	private boolean isInsideSelectingBox(int x, int y) {
		Rectangle rect = getSelectingBoxRectangle();
		if (x < rect.x)
			return false;
		if (y < rect.y)
			return false;
		if ((rect.x + rect.width) < x)
			return false;
		if ((rect.y + rect.height) < y)
			return false;
		return true;
	}
	
	private void deleteModulesInSelectingBox() {
	
		LinkedList	routeList	= new LinkedList();
		LinkedList	nodeList	= new LinkedList();

		Diagram dgm = new Diagram(getWorld(), getDiagramNode());

		SceneGraph sg = getWorld().getSceneGraph();
		
		Route route = sg.getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (dgm.hasRoute(route) == true) {
				Module	moduleIn	= new Module(getWorld(), (ScriptNode)route.getEventInNode());
				Module	moduleOut	= new Module(getWorld(), (ScriptNode)route.getEventOutNode());
			
				int sx = moduleOut.getEventOutNodeXPosition(route.getEventOutField());
				int sy = moduleOut.getEventOutNodeYPosition(route.getEventOutField());
				
				int ex = moduleIn.getEventInNodeXPosition(route.getEventInField());
				int ey = moduleIn.getEventInNodeYPosition(route.getEventInField());
				
				int nodeSize = moduleOut.getNodeSize();
	
				if (isInsideSelectingBox(sx, sy) == false || isInsideSelectingBox(sx+nodeSize, sy+nodeSize) == false) 
					continue;

				if (isInsideSelectingBox(ex, ey) == false || isInsideSelectingBox(ex+nodeSize, ey+nodeSize) == false) 
					continue;
			
				sg.removeRoute(route);
			}
			route = nextRoute;
		}

		ScriptNode moduleNode = dgm.getModules();
		while (moduleNode != null) {
			ScriptNode nextModuleNode = (ScriptNode)moduleNode.nextSameType();
			if (dgm.hasModule(moduleNode) == true) {
				Module module = new Module(getWorld(), moduleNode);
				int mx = module.getXPosition();
				int my = module.getYPosition();
				int msize = module.getSize();
				if (isInsideSelectingBox(mx, my) == true && isInsideSelectingBox(mx+msize, my+msize) == true) 
					dgm.removeModule(module);
			}
			moduleNode = nextModuleNode;
		}
	}

	void copyModulesInSelectingBox(DiagramClipboard clipboard) {

		clipboard.clear();

		Diagram dgm = new Diagram(getWorld(), getDiagramNode());

		int nModule = dgm.getNModules();
		for (int n=0; n<nModule; n++) {
			ScriptNode moduleNode = dgm.getModule(n);
			Module module = new Module(getWorld(), moduleNode);
			int mx = module.getXPosition();
			int my = module.getYPosition();
			int msize = module.getSize();
			if (isInsideSelectingBox(mx, my) == true && isInsideSelectingBox(mx+msize, my+msize) == true) 
				clipboard.addModuleNode(getWorld(), moduleNode);
		}

		for (Route route = getWorld().getSceneGraph().getRoutes(); route != null; route = route.next()) {
			if (dgm.hasRoute(route) == true) {
				Module	moduleIn	= new Module(getWorld(), (ScriptNode)route.getEventInNode());
				Module	moduleOut = new Module(getWorld(), (ScriptNode)route.getEventOutNode());
			
				int sx = moduleOut.getEventOutNodeXPosition(route.getEventOutField());
				int sy = moduleOut.getEventOutNodeYPosition(route.getEventOutField());
				
				int ex = moduleIn.getEventInNodeXPosition(route.getEventInField());
				int ey = moduleIn.getEventInNodeYPosition(route.getEventInField());
				
				int nodeSize = moduleOut.getNodeSize();

				if (isInsideSelectingBox(sx, sy) == false || isInsideSelectingBox(sx+nodeSize, sy+nodeSize) == false) 
					continue;

				if (isInsideSelectingBox(ex, ey) == false || isInsideSelectingBox(ex+nodeSize, ey+nodeSize) == false) 
					continue;
			
				clipboard.addRoute(route);
			}
		}
	}

	//////////////////////////////////////////////////
	// Clipboard
	//////////////////////////////////////////////////

	private static DiagramClipboard mDiagramClipboard = new DiagramClipboard();
	
	public DiagramClipboard getClipboard() {
		return mDiagramClipboard;
	}

	//////////////////////////////////////////////////
	// Cut / Paste
	//////////////////////////////////////////////////
	
	public void copy() {
		copyModulesInSelectingBox(getClipboard());
	}

	public void cut() {
		copy();

		deleteModulesInSelectingBox();

		// Undo Action
		DiagramClipboard copyClipboard = new DiagramClipboard(getWorld(), getClipboard());
		addUndoAction(new DiagramCutUndoAction(copyClipboard));
		
		repaint();
	}

	public void paste() {
		DiagramClipboard clipboard = getClipboard();	

		Diagram dgm = new Diagram(getWorld(), getDiagramNode());

		int nModule = clipboard.getNModuleNodes();
	
		ScriptNode moduleNode[][]	= new ScriptNode[nModule][2];
		ScriptNode undoModuleNode[]	= new ScriptNode[nModule];
	
		for (int n=0; n<nModule; n++) {
			Module		orgModule		= new Module(getWorld(), clipboard.getModuleNode(n));
			Module		copyModule	= new Module(getWorld(), orgModule.getModuleType());
			copyModule.setXPosition(orgModule.getXPosition());
			copyModule.setYPosition(orgModule.getYPosition());
			dgm.addModule(copyModule);
			undoModuleNode[n] = copyModule.getScriptNode();
			moduleNode[n][0] = orgModule.getScriptNode();	
			moduleNode[n][1] = copyModule.getScriptNode();
		}

		int nRoute = clipboard.getNRoutes();

		Route undoRoute[] = new Route[nRoute];

		for (int n=0; n<nRoute; n++) {
			Route route = clipboard.getRoute(n);
			ScriptNode outModule = (ScriptNode)route.getEventOutNode();
			for (int i=0; i<nModule; i++) {
				if (moduleNode[i][0] == outModule) {
					outModule = moduleNode[i][1];
					break;
				}
			}
			ScriptNode inModule = (ScriptNode)route.getEventInNode();
			for (int i=0; i<nModule; i++) {
				if (moduleNode[i][0] == inModule) {
					inModule = moduleNode[i][1];
					break;
				}
			}
			undoRoute[n] = getWorld().getSceneGraph().addRoute(outModule.getName(), route.getEventOutField().getName(), inModule.getName(), route.getEventInField().getName());
		}

		// Undo Action
		addUndoAction(new DiagramPasteUndoAction(undoModuleNode, undoRoute));

		repaint();
	}
		
	//////////////////////////////////////////////////
	// Draw
	//////////////////////////////////////////////////

	private void drawGridLines(Graphics2D g, int width, int height) {
		g.setPaintMode();
		int x, y;

		g.setColor(new Color(0xc0, 0xc0, 0xff));
		for (x=0; x<width; x+=getGridSize())
			g.drawLine(x, 0, x, height); 
		for (y=0; y<height; y+=getGridSize())
			g.drawLine(0, y, width, y); 

		g.setColor(new Color(0xa0, 0xa0, 0xff));
		for (x=0; x<width; x+=getGridSize()*4)
			g.drawLine(x, 0, x, height); 
		for (y=0; y<height; y+=getGridSize()*4)
			g.drawLine(0, y, width, y); 
	}

	private void drawDraggingModule(Graphics2D g, boolean xorDrawing) {
		Module	module = new Module(getWorld(), getSelectingModuleNode());
		int		moduleSize = module.getSize();
				
		g.setColor(Color.black);
		if (xorDrawing == true) {
			g.setXORMode(Color.gray);
			int xpre = getDraggingModulePrevXPostion();
			int ypre = getDraggingModulePrevYPostion();
			if (xpre != -1 && ypre != -1)
				g.drawRect(xpre, ypre, moduleSize, moduleSize);
		}
		int x = getDraggingModuleXPostion();
		int y = getDraggingModuleYPostion();
		g.drawRect(x, y, moduleSize, moduleSize);
	}

	private void drawDraggingModuleLine(Graphics2D g, boolean xorDrawing) {
		g.setColor(Color.black);
		if (xorDrawing == true) {
			g.setXORMode(Color.gray);
			int sxpre = getDraggingMousePrevPostionStartX();
			int sypre = getDraggingMousePrevPostionStartY();
			int expre = getDraggingMousePrevPostionEndX();
			int eypre = getDraggingMousePrevPostionEndY();
			if (sxpre != -1 && sypre != -1 && expre != -1 && eypre != -1) 
				g.drawLine(sxpre, sypre, expre, eypre);
		}
		int sx = getDraggingMousePostionStartX();
		int sy = getDraggingMousePostionStartY();
		int ex = getDraggingMousePostionEndX();
		int ey = getDraggingMousePostionEndY();
		g.drawLine(sx, sy, ex, ey);
	}

	private void drawRect(Graphics2D g, int sx, int sy, int ex, int ey) {
		if (ex < sx) {
			int tmp = sx;
			sx = ex;
			ex = tmp;
		}
		if (ey < sy) {
			int tmp = sy;
			sy = ey;
			ey = tmp;
		}
		g.drawRect(sx, sy, ex-sx, ey-sy);
	}
	
	private void drawDraggingSelectingBox(Graphics2D g, boolean xorDrawing) {
		g.setColor(Color.black);
		int sx = getMouseStartXPosition();
		int sy = getMouseStartYPosition();
		if (xorDrawing == true) {
			g.setXORMode(Color.gray);
			int expre = getDraggingMousePrevXPostion();
			int eypre = getDraggingMousePrevYPostion();
			if (expre != -1 && eypre != -1) 
				drawRect(g, sx, sy, expre, eypre);
		}
		int ex = getDraggingMouseXPostion();
		int ey = getDraggingMouseYPostion();
		drawRect(g, sx, sy, ex, ey);
	}

	private void drawMovingSelectingBox(Graphics2D g, boolean xorDrawing) {
		Rectangle rect = getSelectingBoxRectangle();
		int offsetx = rect.x - getMouseStartXPosition();
		int offsety = rect.y - getMouseStartYPosition();
		g.setColor(Color.black);
		if (xorDrawing == true) {
			g.setXORMode(Color.gray);
			int xpre = getDraggingMousePrevXPostion();
			int ypre = getDraggingMousePrevYPostion();
			if (xpre != -1 && ypre != -1) 
				g.drawRect(xpre + offsetx, ypre + offsety, rect.width, rect.height);
		}
		int x = getDraggingMouseXPostion();
		int y = getDraggingMouseYPostion();
		g.drawRect(x + offsetx, y + offsety, rect.width, rect.height);
	}

	private void clear(Graphics2D g) {
		g.setPaintMode();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void drawLine(Graphics2D g, int sx, int sy, int ex, int ey, boolean isXBold, boolean isYBold) {
		g.drawLine(sx, sy, ex, ey);
	
		if (isXBold == true)
			g.drawLine(sx+1, sy, ex+1, ey);

		if (isYBold == true)
			g.drawLine(sx+1, sy+1, ex, ey+1);
	}

	private void drawZigzagLine(Graphics2D g, int outModuleX, int outModuleY, int outNodeX, int outNodeY, int inModuleX, int inModuleY, int inNodeX, int inNodeY, boolean isExecutionNode) {
		int NODE_LINE_MARGIN = MODULE_SIZE/2;
		
		if (inNodeX < outNodeX) {
			drawLine(g, outNodeX, outNodeY, outNodeX+NODE_LINE_MARGIN, outNodeY, false, true);
			if (inNodeY < outNodeY) {
				drawLine(g, outNodeX+NODE_LINE_MARGIN, outNodeY, outNodeX+NODE_LINE_MARGIN, outModuleY-NODE_LINE_MARGIN, true, false);
				outNodeX = outNodeX+NODE_LINE_MARGIN;
				outNodeY = outModuleY-NODE_LINE_MARGIN;
			}
			else {
				drawLine(g, outNodeX+NODE_LINE_MARGIN, outNodeY, outNodeX+NODE_LINE_MARGIN, outModuleY+MODULE_SIZE+NODE_LINE_MARGIN, true, false);
				outNodeX = outNodeX+NODE_LINE_MARGIN;
				outNodeY = outModuleY+MODULE_SIZE+NODE_LINE_MARGIN;
			}
		}

		if (isExecutionNode == false) {
			int midx = (outNodeX + inNodeX)/2;
			drawLine(g, outNodeX, outNodeY, midx, outNodeY, false, true);
			if (inNodeX < outNodeX) {
				if (inNodeY < outNodeY) {
					drawLine(g, midx, outNodeY, midx, inModuleY + MODULE_SIZE + NODE_LINE_MARGIN, true, false);
					drawLine(g, midx, inModuleY + MODULE_SIZE + NODE_LINE_MARGIN, inModuleX - NODE_LINE_MARGIN, inModuleY + MODULE_SIZE + NODE_LINE_MARGIN, false, true);
					drawLine(g, inModuleX - NODE_LINE_MARGIN, inModuleY + MODULE_SIZE + NODE_LINE_MARGIN, inModuleX - NODE_LINE_MARGIN, inNodeY, true, false);
					drawLine(g, inModuleX - NODE_LINE_MARGIN, inNodeY, inNodeX, inNodeY, false, true);
				}
				else {
					drawLine(g, midx, outNodeY, midx, inModuleY - NODE_LINE_MARGIN, true, false);
					drawLine(g, midx, inModuleY - NODE_LINE_MARGIN, inModuleX - NODE_LINE_MARGIN, inModuleY - NODE_LINE_MARGIN, false, true);
					drawLine(g, inModuleX - NODE_LINE_MARGIN, inModuleY - NODE_LINE_MARGIN, inModuleX - NODE_LINE_MARGIN, inNodeY, true, false);
					drawLine(g, inModuleX - NODE_LINE_MARGIN, inNodeY, inNodeX, inNodeY, false, true);
				}
			}
			else {
				drawLine(g, midx, outNodeY, midx, inNodeY, true, false);
				drawLine(g, midx, inNodeY, inNodeX, inNodeY, false, true);
			}
		}
		else {
			if (inNodeY < outNodeY) {
				int midx = (outNodeX + inNodeX)/2;
				drawLine(g, outNodeX, outNodeY, midx, outNodeY, false, true);
				drawLine(g, midx, outNodeY, midx, inNodeY - NODE_LINE_MARGIN, true, false);
				drawLine(g, midx, inNodeY - NODE_LINE_MARGIN, inNodeX, inNodeY - NODE_LINE_MARGIN, false, true);
				drawLine(g, inNodeX, inNodeY - NODE_LINE_MARGIN, inNodeX, inNodeY, true, false);
			}
			else {
				drawLine(g, outNodeX, outNodeY, inNodeX, outNodeY, false, true);
				drawLine(g, inNodeX, outNodeY, inNodeX, inNodeY, true, false);
			}
		}
	}

	private void drawStraightLine(Graphics2D g, int sx, int sy, int ex, int ey) {
		drawLine(g, sx, sy, ex, ey, false, true);
	}

	private void drawModuleValue(Graphics2D g, String valueSting, int mx, int my) {
		TextLayout textLayout = new TextLayout(valueSting, g.getFont(), g.getFontRenderContext());
		int textWidth = (int)textLayout.getBounds().getWidth();
		int textHeight = (int)textLayout.getBounds().getHeight();
		g.drawString(valueSting, mx + MODULE_SIZE/2 - (textWidth/2), my + MODULE_SIZE + textHeight);
	}

	private void drawModuleValue(Graphics2D g, ScriptNode moduleNode) {

		Module			module = new Module(getWorld(), moduleNode);
		ModuleType	moduleType = module.getModuleType();
	
		int mx = module.getXPosition();
		int my = module.getYPosition();
		
		String moduleClassName = moduleType.getClassName();
		String moduleTypeName = moduleType.getName();
		
		if (moduleClassName.equalsIgnoreCase("OBJECT") == true || moduleClassName.equalsIgnoreCase("VIEW") == true ||
			moduleClassName.equalsIgnoreCase("LIGHT") == true  || moduleClassName.equalsIgnoreCase("MATERIAL") == true ) {
			Node node = null;
			String headString = new String(moduleType.getName().getBytes(), 0, 3);
			if (headString.equalsIgnoreCase("SET") == true)
				node = module.getTargetNode();
			else if (headString.equalsIgnoreCase("GET") == true)
				node = module.getSourceNode();
			if (node != null) {
				drawModuleValue(g, node.getName(), mx, my);
			}
		}
		if (moduleClassName.equalsIgnoreCase("INTERP") == true) {
			Node node = module.getTargetNode();
			if (node != null) 
				drawModuleValue(g, node.getName(), mx, my);
		}
		else if (moduleClassName.equalsIgnoreCase("STRING") == true) {
			if (moduleTypeName.equalsIgnoreCase("VALUE")		== true	||
				moduleTypeName.equalsIgnoreCase("POSITION")	== true	||
				moduleTypeName.equalsIgnoreCase("DIRECTION")	== true	||
				moduleTypeName.equalsIgnoreCase("BOOL")		== true	||
				moduleTypeName.equalsIgnoreCase("ROTATION")	== true	||
				moduleTypeName.equalsIgnoreCase("COLOR")		== true	)
				drawModuleValue(g, module.getValue(), mx, my);
		}
		else if (moduleClassName.equalsIgnoreCase("FILTER") == true) {
			if (moduleTypeName.equalsIgnoreCase("HIGH")	== true	|| 
				moduleTypeName.equalsIgnoreCase("LOW")		== true	||
				moduleTypeName.equalsIgnoreCase("SCALE")	== true	||
				moduleTypeName.equalsIgnoreCase("OFFSET")	== true	||
				moduleTypeName.equalsIgnoreCase("RANGE")	== true )
				drawModuleValue(g, module.getValue(), mx, my);
		}
/*
		else if (!StringCompareIgnoreCase(moduleType->getClassName(), "MISC")) {
			Script *node = NULL;
			if (!StringCompareIgnoreCase(moduleType->getName(), "SETVALUE"))
				node = (Script *)module.getTargetNode();
			else if (!StringCompareIgnoreCase(moduleType->getName(), "GETVALUE")) 
				node = (Script *)module.getSourceNode();
			if (node) {
				CGlobalData	gData(node);
				char *nodeName = gData.getName();
				CSize textSize = pMemDC->GetTextExtent(nodeName, StringLength(nodeName));
				pMemDC->TextOut(mx + 32/2 - (textSize.cx/2), my + 32, nodeName, StringLength(nodeName));
			}
		}
*/
	}

	private void drawSelectingBox(Graphics2D g) {
		float dash[] = { 3.0f };
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
		Rectangle r = getSelectingBoxRectangle();
		g.setColor(Color.blue);
		g.drawRect(r.x , r.y, r.width, r.height);
		g.setStroke(new BasicStroke());
	}
									
	private void draw(Graphics2D g) {
		g.setPaintMode();

		Diagram dgm = new Diagram(getWorld(), getDiagramNode());

		// Draw Grid Line
		if (getGridDisplay() == true) {
			Dimension size = getComponentSize();
			drawGridLines(g, size.width, size.height);
		}
		
		// Draw Module Connected Line
		g.setColor(Color.black);
		for (Route route = getWorld().getSceneGraph().getRoutes(); route != null; route = route.next()) {
			if (dgm.hasModule(route.getEventInNode()) && dgm.hasModule(route.getEventOutNode())) {
				Module	moduleIn	= new Module(getWorld(), (ScriptNode)route.getEventInNode());
				Module	moduleOut	= new Module(getWorld(), (ScriptNode)route.getEventOutNode());

				int	nodeHalfSize = moduleIn.getNodeSize() / 2;

				int	moduleOutNodeNumber = moduleOut.getEventOutNodeNumber(route.getEventOutField());
				int sx = moduleOut.getEventOutNodeXPosition(moduleOutNodeNumber) + nodeHalfSize;
				int sy = moduleOut.getEventOutNodeYPosition(moduleOutNodeNumber) + nodeHalfSize;

				int		ex, ey;
				boolean	isExecutionNode;
				if (route.getEventInField() != moduleIn.getExecutionField()) {
					int	moduleInNodeNumber = moduleIn.getEventInNodeNumber(route.getEventInField());
					ex = moduleIn.getEventInNodeXPosition(moduleInNodeNumber) + nodeHalfSize;
					ey = moduleIn.getEventInNodeYPosition(moduleInNodeNumber) + nodeHalfSize;
					isExecutionNode = false;
				}
				else {
					ex = moduleIn.getExecutionNodeXPosition() + nodeHalfSize;
					ey = moduleIn.getExecutionNodeYPosition() + nodeHalfSize;
					isExecutionNode = true;
				}
	
				if (getLineStyle() == LINE_STYLE_STRAIGHT)
					drawStraightLine(g, sx, sy, ex, ey);
				else {
					int ix = moduleIn.getXPosition();
					int iy = moduleIn.getYPosition();
					int ox = moduleOut.getXPosition();
					int oy = moduleOut.getYPosition();
					drawZigzagLine(g, ox, oy, sx, sy, ix, iy, ex, ey, isExecutionNode);
				}
			}
		}

		// Draw Module
		g.setColor(Color.black);
		for (int n=0; n<dgm.getNModules(); n++) {

			ScriptNode	moduleNode = dgm.getModule(n);
			Module		module = new Module(getWorld(), moduleNode);
			ModuleType	moduleType = module.getModuleType();
			Image		moduleIcon = moduleType.getIcon();

			int	nodeSize = module.getNodeSize();
			
			int mx = module.getXPosition();
			int my = module.getYPosition();
			g.drawImage(moduleIcon, mx, my, null);

			int nEventInNodes = module.getNEventInNodes();
			for (int nNode=0; nNode<nEventInNodes; nNode++) {
				int nodex = module.getEventInNodeXPosition(nNode);
				int nodey = module.getEventInNodeYPosition(nNode);
				g.fillRect(nodex, nodey, nodeSize, nodeSize);
			}

			int nEventOutNodes = module.getNEventOutNodes();
			for (int nNode=0; nNode<nEventOutNodes; nNode++) {
				int nodex = module.getEventOutNodeXPosition(nNode);
				int nodey = module.getEventOutNodeYPosition(nNode);
				g.fillRect(nodex, nodey, nodeSize, nodeSize);
				String string = ((ConstSFString)module.getEventOutField(nNode)).getValue();
				if (string != null)
					g.drawString(string, nodex + nodeSize, nodey - nodeSize);
			}
			
			drawModuleValue(g, moduleNode);
			
			if (module.hasExecutionNode() == true) {
				int x = module.getExecutionNodeXPosition();
				int y = module.getExecutionNodeYPosition();
				g.fillRect(x, y, nodeSize, nodeSize);
			}

			if (getSelectingModuleNode() == moduleNode) {
				if (isOperationMode(OPERATIONMODE_SELECTING_MODULE) == true) {
					int moduleSize = module.getSize();
					g.drawRect(mx, my, moduleSize, moduleSize);
				}
				else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) == true) {
					int nodex = module.getEventOutNodeXPosition(getSelectingEventOutNode());
					int nodey = module.getEventOutNodeYPosition(getSelectingEventOutNode());
					g.drawRect(nodex-1, nodey-1, nodeSize + 1, nodeSize + 1);
				}
				else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE) == true) {
					int nodex = module.getEventInNodeXPosition(getSelectingEventInNode());
					int nodey = module.getEventInNodeYPosition(getSelectingEventInNode());
					g.drawRect(nodex-1, nodey-1, nodeSize + 1, nodeSize + 1);
				}
				else if (isOperationMode(MODULE_INSIDE_EXECUTIONNODE) == true) {
					int nodex = module.getExecutionNodeXPosition();
					int nodey = module.getExecutionNodeYPosition();
					g.drawRect(nodex-1, nodey-1, nodeSize + 1, nodeSize + 1);
				}
			}
		}
		
		if (isSelectingBoxOn() == true)
			drawSelectingBox(g);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		boolean	xorMode = true;
		
		switch (getDrawMode()) {
		case DRAWMODE_NORMAL:
			clear(g2d);
			draw(g2d);
			break;			
		case DRAWMODE_DRAGGING_MODULE:
			drawDraggingModule(g2d, xorMode);
			break;
		case DRAWMODE_DRAGGING_MODULELINE:
			drawDraggingModuleLine(g2d, xorMode);
			break;
		case DRAWMODE_DRAGGING_SELECTINGBOX:
			drawDraggingSelectingBox(g2d, xorMode);
			break;
		case DRAWMODE_MOVING_SELECTINGBOX:
			drawMovingSelectingBox(g2d, xorMode);
			break;
		}
		
		//updateComponentSize();
	}

	//////////////////////////////////////////////////
	// Selecting Module
	//////////////////////////////////////////////////

	private void setSelectingModuleNode(ScriptNode node) {
		mSelectingModuleNode = node;
	}
	
	private ScriptNode getSelectingModuleNode() {
		return mSelectingModuleNode;
	}

	//////////////////////////////////////////////////
	// Selecting Module
	//////////////////////////////////////////////////

	private void setOperationMode(int mode) {
		Debug.message("DiagramFrame::setOperationMode = " + mode);
		mMode = mode;
	}
	
	private int getOperationMode() {
		return mMode;
	}

	private boolean isOperationMode(int mode) {
		return ((mMode & mode) != 0 ? true : false);
	}

	private int getSelectingEventInNode() {
		return (getOperationMode() & MODULE_NODE_NUMBER_MASK); 
	}
	
	private int getSelectingEventOutNode() { 
		return (getOperationMode() & MODULE_NODE_NUMBER_MASK); 
	}

	//////////////////////////////////////////////////
	// Draw Mode
	//////////////////////////////////////////////////
	
	private int mDrawMode;
	
	private int mDraggingModuleXPosition;
	private int mDraggingModuleYPosition;
	private int mDraggingModulePrevXPosition;
	private int mDraggingModulePrevYPosition;
	
	private int mDraggingMouseXPosition;
	private int mDraggingMouseYPosition;
	private int mDraggingMousePrevXPosition;
	private int mDraggingMousePrevYPosition;
	
	private int mDraggingMousePositionStartX;
	private int mDraggingMousePositionStartY;
	private int mDraggingMousePositionEndX;
	private int mDraggingMousePositionEndY;

	private int mDraggingMousePrevPositionStartX;
	private int mDraggingMousePrevPositionStartY;
	private int mDraggingMousePrevPositionEndX;
	private int mDraggingMousePrevPositionEndY;
		
	private void setDrawMode(int mode) {
		mDrawMode = mode;
	}
	
	private int getDrawMode() {
		return mDrawMode;
	}

	private void setDraggingModulePostion(int x, int y) {
		mDraggingModulePrevXPosition = mDraggingModuleXPosition;
		mDraggingModulePrevYPosition = mDraggingModuleYPosition;
		
		mDraggingModuleXPosition = x;
		mDraggingModuleYPosition = y;
	}

	private int getDraggingModuleXPostion() {
		return mDraggingModuleXPosition;
	}

	private int getDraggingModuleYPostion() {
		return mDraggingModuleYPosition;
	}

	private int getDraggingModulePrevXPostion() {
		return mDraggingModulePrevXPosition;
	}

	private int getDraggingModulePrevYPostion() {
		return mDraggingModulePrevYPosition;
	}

	private void setDraggingMousePostion(int x, int y) {
		mDraggingMousePrevXPosition = mDraggingMouseXPosition;
		mDraggingMousePrevYPosition = mDraggingMouseYPosition;
		
		mDraggingMouseXPosition = x;
		mDraggingMouseYPosition = y;
	}

	private int getDraggingMouseXPostion() {
		return mDraggingMouseXPosition;
	}

	private int getDraggingMouseYPostion() {
		return mDraggingMouseYPosition;
	}

	private int getDraggingMousePrevXPostion() {
		return mDraggingMousePrevXPosition;
	}

	private int getDraggingMousePrevYPostion() {
		return mDraggingMousePrevYPosition;
	}
		
	private void setDraggingMousePostion(int sx, int sy, int ex, int ey) {
		mDraggingMousePrevPositionStartX = mDraggingMousePositionStartX;
		mDraggingMousePrevPositionStartY = mDraggingMousePositionStartY;
		mDraggingMousePrevPositionEndX = mDraggingMousePositionEndX;
		mDraggingMousePrevPositionEndY = mDraggingMousePositionEndY;
		
		mDraggingMousePositionStartX = sx;
		mDraggingMousePositionStartY = sy;
		mDraggingMousePositionEndX = ex;
		mDraggingMousePositionEndY = ey;
	}

	private int getDraggingMousePostionStartX() {
		return mDraggingMousePositionStartX;
	}

	private int getDraggingMousePostionStartY() {
		return mDraggingMousePositionStartY;
	}

	private int getDraggingMousePostionEndX() {
		return mDraggingMousePositionEndX;
	}

	private int getDraggingMousePostionEndY() {
		return mDraggingMousePositionEndY;
	}

	private int getDraggingMousePrevPostionStartX() {
		return mDraggingMousePrevPositionStartX;
	}

	private int getDraggingMousePrevPostionStartY() {
		return mDraggingMousePrevPositionStartY;
	}

	private int getDraggingMousePrevPostionEndX() {
		return mDraggingMousePrevPositionEndX;
	}

	private int getDraggingMousePrevPostionEndY() {
		return mDraggingMousePrevPositionEndY;
	}
																																
	//////////////////////////////////////////////////
	// Extents
	//////////////////////////////////////////////////

	public Dimension getComponentSize() {			
		Diagram dgm = new Diagram(getWorld(), getDiagramNode());
		int extents[][] = new int[2][2];
		dgm.getExtents(extents);
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		if (extents[Diagram.EXTENTS_MAX][0] < panelWidth)
			extents[Diagram.EXTENTS_MAX][0] = panelWidth; 
		if (extents[Diagram.EXTENTS_MAX][1] < panelHeight)
			extents[Diagram.EXTENTS_MAX][1] = panelHeight; 
		return new Dimension(extents[Diagram.EXTENTS_MAX][0], extents[Diagram.EXTENTS_MAX][1]);
	}
		
	public void updateComponentSize() {			
		Dimension size = getComponentSize();
		if (size.width != getWidth() || size.height != getHeight()) {
			Debug.message("Update component size (" + size + ") in DiagramFrame::updateComponentSize()");
			setPreferredSize(size);
		}
	}

	public void resetComponentSize() {			
		Diagram dgm = new Diagram(getWorld(), getDiagramNode());
		dgm.updateExtents();
		updateComponentSize();
	}

	//////////////////////////////////////////////////
	// UndoAction
	//////////////////////////////////////////////////
	
	private class DiagramMoveModuleUndoAction implements UndoObject {
		ScriptNode	mModuleNode = null;
		int			mx = 0;
		int			my = 0;	
		public DiagramMoveModuleUndoAction(ScriptNode moduleNode, int x, int y) {
			mModuleNode = moduleNode;
			mx = x; my = y;
		}
		public void undo() {
			Module module = new Module(getWorld(), mModuleNode);
			module.setPosition(mx, my);
		}
	}

	private class DiagramDeleteModuleUndoAction implements UndoObject {
		ScriptNode	mModuleNode = null;
		int			mnRouteInfo = 0;
		Route		mRouteInfo[] = null;	
		public DiagramDeleteModuleUndoAction(ScriptNode moduleNode) {
			mModuleNode = moduleNode;
			Module module = new Module(getWorld(), moduleNode);
			mnRouteInfo = module.getNRoutes();
			if (mnRouteInfo > 0) {
				mRouteInfo = new Route[mnRouteInfo];
				int nRoute = 0;
				for (Route route=getWorld().getSceneGraph().getRoutes(); route != null; route=route.next()) {
					if (moduleNode == route.getEventOutNode() || moduleNode == route.getEventInNode()) {
						mRouteInfo[nRoute] = new Route(route.getEventInNode(), route.getEventInField(), route.getEventOutNode(), route.getEventOutField());
						nRoute++;
					}
				}
			}
		}
		public void undo() {
			for (int n=0; n<mnRouteInfo; n++)
				getWorld().getSceneGraph().addRoute(mRouteInfo[n]);
		}
	}

	private class DiagramCreateModuleLineUndoAction implements UndoObject {
		private Node	mInModuleNode	= null;
		private Field	mInField		= null;	
		private Node	mOutModuleNode	= null;
		private Field	mOutField		= null;
		public DiagramCreateModuleLineUndoAction(Node outModuleNode, Field outField, Node inModuleNode, Field inField) {
			mInModuleNode	= inModuleNode;
			mInField		= inField;
			mOutModuleNode	= outModuleNode;
			mOutField		= outField;
		}
		public void undo() {
			getWorld().getSceneGraph().removeRoute(mOutModuleNode, mOutField, mInModuleNode, mInField);
		}
	}

	private class DiagramDeleteModuleLineUndoAction implements UndoObject {
		private Node	mInModuleNode	= null;
		private Field	mInField		= null;	
		private Node	mOutModuleNode	= null;
		private Field	mOutField		= null;
		public DiagramDeleteModuleLineUndoAction(Node outModuleNode, Field outField, Node inModuleNode, Field inField) {
			mInModuleNode	= inModuleNode;
			mInField		= inField;
			mOutModuleNode	= outModuleNode;
			mOutField		= outField;
		}
		public void undo() {
			getWorld().getSceneGraph().addRoute(mOutModuleNode, mOutField, mInModuleNode, mInField);
		}
	}

	private class DiagramMoveSelectingBoxUndoAction implements UndoObject {
	
		private ScriptNode	mScriptNode[];
		private Point			mPos[];
				
		public DiagramMoveSelectingBoxUndoAction(ScriptNode scriptNode[], Point pos[]) {
			mScriptNode = scriptNode;
			mPos = pos;
		}
	
		public void undo() {
			for (int n=0; n<mScriptNode.length; n++) {
				Module module = new Module(getWorld(), mScriptNode[n]);
				module.setPosition(mPos[n].x, mPos[n].y);
			}
		}

	}

	private class DiagramCutUndoAction implements UndoObject {
		
		private DiagramClipboard	mDiagramClipboard;
		
		public DiagramCutUndoAction(DiagramClipboard clipboard) {
			mDiagramClipboard = clipboard;
		}
		
		public void undo() {
			TransformNode		dgmNode = getDiagramNode();
			DiagramClipboard	clipboard = mDiagramClipboard;

			Diagram dgm = new Diagram(getWorld(), dgmNode);

			int nModule = clipboard.getNModuleNodes();
			ScriptNode moduleNode[][] = new ScriptNode[nModule][2];
			for (int n=0; n<nModule; n++) {
				Module orgModule		= new Module(getWorld(), clipboard.getModuleNode(n));
				Module copyModule		= new Module(getWorld(), orgModule.getModuleType());
				copyModule.setXPosition(orgModule.getXPosition());
				copyModule.setYPosition(orgModule.getYPosition());
				dgm.addModule(copyModule);
				moduleNode[n][0] = orgModule.getScriptNode();
				moduleNode[n][1] = copyModule.getScriptNode();
			}

			int nRoute = clipboard.getNRoutes();
			for (int n=0; n<nRoute; n++) {
				Route route = clipboard.getRoute(n);
				ScriptNode outModule = (ScriptNode)route.getEventOutNode();
				for (int i=0; i<nModule; i++) {
					if (moduleNode[i][0] == outModule) {
						outModule = moduleNode[i][1];
						break;
					}
				}
				ScriptNode inModule = (ScriptNode)route.getEventInNode();
				for (int i=0; i<nModule; i++) {
					if (moduleNode[i][0] == inModule) {
						inModule = moduleNode[i][1];
						break;
					}
				}
				getWorld().getSceneGraph().addRoute(outModule.getName(), route.getEventOutField().getName(), inModule.getName(), route.getEventInField().getName());
			}
		}
	}

	private class DiagramPasteUndoAction implements UndoObject {
		
		private ScriptNode	mScriptNode[];
		private Route			mRoute[];
		
		public DiagramPasteUndoAction(ScriptNode scriptNode[], Route route[]) {
			mScriptNode = scriptNode;
			mRoute = route;
		}

		public void undo() {
			
			for (int n=0; n<mRoute.length; n++) {
				getWorld().getSceneGraph().removeRoute(mRoute[n]);
			}

			Diagram dgm = new Diagram(getWorld(), getDiagramNode());
			for (int n=0; n<mScriptNode.length; n++) {
				Module module = new Module(getWorld(), mScriptNode[n]);
				dgm.removeModule(module);
			}
		}

	}		

	//////////////////////////////////////////////////
	// MouseListener
	//////////////////////////////////////////////////

	private int mMouseXBeforeOneFrame = 0;
	private int mMouseYBeforeOneFrame = 0;
	private int mMouseStartX = 0;
	private int mMouseStartY = 0;
	private int mMouseButton = 0;
	
	private void setMousePositionBeforeOneFrame(int x, int y) {
		mMouseXBeforeOneFrame = x;
		mMouseYBeforeOneFrame = y;
	}
	
	private int getMouseXPositionBeforeOneFrame() {
		return mMouseXBeforeOneFrame;
	}
	
	private int getMouseYPositionBeforeOneFrame() {
		return mMouseYBeforeOneFrame;
	}

	private void setMouseStartPosition(int x, int y) {
		mMouseStartX = x;
		mMouseStartY = y;
	}
	
	private int getMouseStartXPosition() {
		return mMouseStartX;
	}
	
	private int getMouseStartYPosition() {
		return mMouseStartY;
	}

	private void setMouseButton(int button) {
		mMouseButton = button;
	}
	
	private int getMouseButton() {
		return mMouseButton;
	}
		
	private int setOperationModeAndSelectedModuleFromMousePosition(int mx, int my) {
		Diagram dgm = new Diagram(getWorld(), getDiagramNode());
	
		ModuleSelectedData data = dgm.getModule(mx, my);
		
		int			mode = data.getParts();
		ScriptNode	node = data.getScriptNode();
		
		setOperationMode(mode);
		setSelectingModuleNode(node);
		
		Debug.message("DiagramFrame::setSelectedModuleAtMousePosition = " + mode + ", " + node);
		
		return mode;
	}

	public void openModuleDialog(ScriptNode moduleNode) {
		Module		module = new Module(getWorld(), moduleNode);
		ModuleType	moduleType = module.getModuleType();
		String		moduleClassName = moduleType.getClassName();
		String		moduleTypeName = moduleType.getName();
		
		//// STRING /////////////////////////////////////////////////////////
		if (moduleClassName.equalsIgnoreCase("STRING") == true) {
			if (moduleTypeName.equalsIgnoreCase("VALUE") == true) {
				DialogModuleStringValue dialog = new DialogModuleStringValue(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getConstantValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("POSITION") == true) {
				DialogModuleStringPosition dialog = new DialogModuleStringPosition(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getXValue() + "," + dialog.getYValue() + "," + dialog.getZValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("DIRECTION") == true) {
				DialogModuleStringDirection dialog = new DialogModuleStringDirection(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getXValue() + "," + dialog.getYValue() + "," + dialog.getZValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("ROTATION") == true) {
				DialogModuleStringRotation dialog = new DialogModuleStringRotation(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getXValue() + "," + dialog.getYValue() + "," + dialog.getZValue() + "," + dialog.getAngleValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("COLOR") == true) {
				DialogModuleStringColor dialog = new DialogModuleStringColor(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getRed() + "," + dialog.getGreen() + "," + dialog.getBlue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("BOOL") == true) {
				DialogModuleStringBool dialog = new DialogModuleStringBool(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getBooleanValue());
					repaint();
				}
			}
		}
			
		//// OBJECT / VIEWPOINT / MATERIAL / LIGHT /////////////////////////////////////////////////////////
		if (moduleClassName.equalsIgnoreCase("OBJECT") == true || moduleClassName.equalsIgnoreCase("VIEW") == true ||
			moduleClassName.equalsIgnoreCase("LIGHT") == true  || moduleClassName.equalsIgnoreCase("MATERIAL") == true ) {
			Node node = null;
			String headString = new String(moduleType.getName().getBytes(), 0, 3);
			if (headString.equalsIgnoreCase("SET") == true)
				node = module.getTargetNode();
			else if (headString.equalsIgnoreCase("GET") == true)
				node = module.getSourceNode();
			
			DialogModuleNode dialog = null;
			if (moduleClassName.equalsIgnoreCase("OBJECT") == true)
				dialog = new DialogModuleObjectNode(getTopFrame(), getWorld(), (TransformNode)node);
			else if (moduleClassName.equalsIgnoreCase("VIEW") == true)
				dialog = new DialogModuleViewpointNode(getTopFrame(), getWorld(), (ViewpointNode)node);
			else if (moduleClassName.equalsIgnoreCase("LIGHT") == true)
				dialog = new DialogModuleLightNode(getTopFrame(), getWorld(), (LightNode)node);
			else if (moduleClassName.equalsIgnoreCase("MATERIAL") == true)
				dialog = new DialogModuleMaterialNode(getTopFrame(), getWorld(), (MaterialNode)node);
			
			if (dialog.doModal() == Dialog.OK_OPTION) {
				node = dialog.getNode();
				Debug.message("Selected node = " + node.getName());
				if (headString.equalsIgnoreCase("SET") == true)
					module.setTargetNode(node);
				else if (headString.equalsIgnoreCase("GET") == true)
					module.setSourceNode(node);
				repaint();
			}
		}
		
		//// FILTER /////////////////////////////////////////////////////////
		if (moduleClassName.equalsIgnoreCase("FILTER") == true) {
			if (moduleTypeName.equalsIgnoreCase("HIGH") == true) {
				DialogModuleFilterHigh dialog = new DialogModuleFilterHigh(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getHighValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("LOW") == true) {
				DialogModuleFilterLow dialog = new DialogModuleFilterLow(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getLowValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("SCALE") == true) {
				DialogModuleFilterScale dialog = new DialogModuleFilterScale(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getScaleValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("OFFSET") == true) {
				DialogModuleFilterOffset dialog = new DialogModuleFilterOffset(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getOffsetValue());
					repaint();
				}
			}
			else if (moduleTypeName.equalsIgnoreCase("RANGE") == true) {
				DialogModuleFilterRange dialog = new DialogModuleFilterRange(getTopFrame(), module);
				if (dialog.doModal() == Dialog.OK_OPTION) {
					module.setValue(dialog.getHighValue() + "," + dialog.getLowValue());
					repaint();
				}
			}
		}
		//// INTERP /////////////////////////////////////////////////////////
		if (moduleClassName.equalsIgnoreCase("INTERP") == true) {
			DialogModuleNode dialog = null;
			Node node = module.getTargetNode();
			
			if (moduleTypeName.equalsIgnoreCase("COLOR") == true) 
				dialog = new DialogModuleColorInterpolatorNode(getTopFrame(), getWorld(), (ColorInterpolatorNode)node);
			else if (moduleTypeName.equalsIgnoreCase("SCALAR") == true) 
				dialog = new DialogModuleScalarInterpolatorNode(getTopFrame(), getWorld(), (ScalarInterpolatorNode)node);
			else if (moduleTypeName.equalsIgnoreCase("POSITION") == true) 
				dialog = new DialogModulePositionInterpolatorNode(getTopFrame(), getWorld(), (PositionInterpolatorNode)node);
			else if (moduleTypeName.equalsIgnoreCase("ORIENTATION") == true) 
				dialog = new DialogModuleOrientationInterpolatorNode(getTopFrame(), getWorld(), (OrientationInterpolatorNode)node);
			else if (moduleTypeName.equalsIgnoreCase("NORMAL") == true) 
				dialog = new DialogModuleNormalInterpolatorNode(getTopFrame(), getWorld(), (NormalInterpolatorNode)node);
			
			if (dialog.doModal() == Dialog.OK_OPTION) {
				node = dialog.getNode();
				Debug.message("Selected node = " + node.getName());
				module.setTargetNode(node);
				repaint();
			}
		}

		//// MISC(NODE) /////////////////////////////////////////////////////////
		if (moduleClassName.equalsIgnoreCase("MISC") == true) {
			DialogModuleNode dialog = null;
			Node node = module.getTargetNode();
			
			if (moduleTypeName.equalsIgnoreCase("SetSkyColor") == true) 
				dialog = new DialogModuleBackgroundNode(getTopFrame(), getWorld(), (BackgroundNode)node);
			else if (moduleTypeName.equalsIgnoreCase("SetImageTexture") == true) 
				dialog = new DialogModuleImageTextureNode(getTopFrame(), getWorld(), (ImageTextureNode)node);
			else if (moduleTypeName.equalsIgnoreCase("SetAudioClip") == true) 
				dialog = new DialogModuleAudioClipNode(getTopFrame(), getWorld(), (AudioClipNode)node);
			else if (moduleTypeName.equalsIgnoreCase("SetText") == true) 
				dialog = new DialogModuleTextNode(getTopFrame(), getWorld(), (TextNode)node);
			else if (moduleTypeName.equalsIgnoreCase("SetSwitch") == true) 
				dialog = new DialogModuleSwitchNode(getTopFrame(), getWorld(), (SwitchNode)node);
			
			if (dialog != null) {
				if (dialog.doModal() == Dialog.OK_OPTION) {
					node = dialog.getNode();
					Debug.message("Selected node = " + node.getName());
					module.setTargetNode(node);
					repaint();
				}
			}
		}

	}	
	
	public void mouseClicked(MouseEvent e) {
	
		if (getWorld().isSimulationActive()) {
			//MessageBeep(0);
			return;
		}

		int mx = e.getX();
		int my = e.getY();
		
		int mode = setOperationModeAndSelectedModuleFromMousePosition(mx, my);
		
		if (e.getClickCount() == 2 &&  mode != OPERATIONMODE_NONE) {
			ScriptNode	moduleNode = getSelectingModuleNode();
			openModuleDialog(moduleNode);
		}
		
		setMouseButton(e.getModifiers());
		setMouseStartPosition(mx, my);
	}
	
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	} 

	public void mousePressed(MouseEvent e) {
	
		setDraggingModulePostion(-1, -1);
		setDraggingMousePostion(-1, -1);
		setDraggingMousePostion(-1, -1, -1, -1);
		
		if (getWorld().isSimulationActive()) {
			//MessageBeep(0);
			return;
		}

		int mx = e.getX();
		int my = e.getY();
		
		int mode = setOperationModeAndSelectedModuleFromMousePosition(e.getX(), e.getY());
		
		if (mode != OPERATIONMODE_NONE) {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			repaint();	
		}
		else {
			if (isSelectingBoxOn() == true) {
				if (isInsideSelectingBox(mx, my) == true)
					setOperationMode(OPERATIONMODE_MOVING_SELECTING_BOX);
				else 
					selectingBoxOff();
			}
			else
				setOperationMode(OPERATIONMODE_SELECTING_BOX);			
		}		

		setMouseButton(e.getModifiers());
		setMouseStartPosition(mx, my);
	}

	public void mouseReleased(MouseEvent e) {
		setDrawMode(DRAWMODE_NORMAL);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		if (getWorld().isSimulationActive()) {
			//MessageBeep(0);
			return;
		}
		
		int mx = e.getX();
		int my = e.getY();
		
		Diagram dgm = new Diagram(getWorld(), getDiagramNode());

		//////////////////////////////////////////////////////
		//	Module Operation
		//////////////////////////////////////////////////////
		
		if (isOperationMode(OPERATIONMODE_SELECTING_MODULE) == true) {
			ScriptNode moduleNode = getSelectingModuleNode();
			Module module = new Module(getWorld(), moduleNode);

			if (mx == getMouseStartXPosition() && my == getMouseStartYPosition())
				return;
			
			int x = module.getXPosition();
			int y = module.getYPosition();
			
			if (getGridSnap() == true) {
				mx = toGridSnapPosition(mx);
				my = toGridSnapPosition(my);
			}
			
			if (x != mx || y != my) {
				// Undo Action
				addUndoAction(new DiagramMoveModuleUndoAction(moduleNode, x, y));
				module.setPosition(mx, my);
				// Update Screen Size
				resetComponentSize();		
			}
		}
		else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) == true) {
			ScriptNode	eventOutNode	= getSelectingModuleNode();
			Field		eventOutField	= eventOutNode.getEventOut(getSelectingEventOutNode());

			ModuleSelectedData dgmModuleData = dgm.getModule(mx, my);
			setOperationMode(dgmModuleData.getParts());
			setSelectingModuleNode(dgmModuleData.getScriptNode());

			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE) == true) {
				ScriptNode eventInNode = getSelectingModuleNode();
				Module inModule = new Module(getWorld(), eventInNode);
				Field eventInField = inModule.getEventInField(getSelectingEventInNode());

				Module outModule = new Module(getWorld(), eventOutNode);
				outModule.setOutputRoute(eventOutField, eventInNode, eventInField);

				// Undo Action
				addUndoAction(new DiagramCreateModuleLineUndoAction(eventOutNode, eventOutField, eventInNode, eventInField));
			}
			else if (isOperationMode(MODULE_INSIDE_EXECUTIONNODE) == true) {
				ScriptNode eventInNode = getSelectingModuleNode();
				Module inModule = new Module(getWorld(), eventInNode);
				Field eventInField = inModule.getExecutionField();

				Module outModule = new Module(getWorld(), eventOutNode);
				outModule.setOutputRoute(eventOutField, eventInNode, eventInField);

				// Undo Action
				addUndoAction(new DiagramCreateModuleLineUndoAction(eventOutNode, eventOutField, eventInNode, eventInField));
			}
		}
		else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE) == true) {
			ScriptNode eventInNode = getSelectingModuleNode();
			Module inModule = new Module(getWorld(), eventInNode);
			Field eventInField = inModule.getEventInField(getSelectingEventInNode());

			ModuleSelectedData dgmModuleData = dgm.getModule(mx, my);
			setOperationMode(dgmModuleData.getParts());
			setSelectingModuleNode(dgmModuleData.getScriptNode());

			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) == true) {
				ScriptNode eventOutNode = getSelectingModuleNode();
				Module outModule = new Module(getWorld(), eventOutNode);
				Field eventOutField = outModule.getEventOutField(getSelectingEventOutNode());

				outModule.setOutputRoute(eventOutField, eventInNode, eventInField);

				// Undo Action
				addUndoAction(new DiagramCreateModuleLineUndoAction(eventOutNode, eventOutField, eventInNode, eventInField));
			}
		}
		else if (isOperationMode(MODULE_INSIDE_EXECUTIONNODE) == true) {
			ScriptNode eventInNode = getSelectingModuleNode();
			Module inModule = new Module(getWorld(), eventInNode);
			Field eventInField = inModule.getExecutionField();

			ModuleSelectedData dgmModuleData = dgm.getModule(mx, my);
			setOperationMode(dgmModuleData.getParts());
			setSelectingModuleNode(dgmModuleData.getScriptNode());

			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) == true) {
				ScriptNode eventOutNode	= getSelectingModuleNode();
				Module outModule = new Module(getWorld(), eventOutNode);
				Field eventOutField = outModule.getEventOutField(getSelectingEventOutNode());

				outModule.setOutputRoute(eventOutField, eventInNode, eventInField);

				// Undo Action
				addUndoAction(new DiagramCreateModuleLineUndoAction(eventOutNode, eventOutField, eventInNode, eventInField));
			}
		}
		
		//////////////////////////////////////////////////////
		// SelectingBox Operation
		//////////////////////////////////////////////////////
		
		switch (getOperationMode()) {
		case OPERATIONMODE_SELECTING_BOX:
			{		
				int mxStart = getMouseStartXPosition();
				int myStart = getMouseStartYPosition();
			
				if (mxStart != mx && myStart != my) {

					if (getGridSnap()) {
						mxStart = toGridSnapPosition(mxStart);
						myStart = toGridSnapPosition(myStart);
						mx = toGridSnapPosition(mx);
						my = toGridSnapPosition(my);
					}

					setSelectingBoxRect(mxStart, myStart, mx, my);
					selectingBoxOn();
				}
				else
					selectingBoxOff();
			}
			break;
		case OPERATIONMODE_MOVING_SELECTING_BOX:
			{
				Rectangle selBoxRect = getSelectingBoxRectangle();
				
				int offsetx = (mx - getMouseStartXPosition());
				int offsety = (my - getMouseStartYPosition());
				
				int nSelBoxModule = dgm.getNModules(selBoxRect);

				ScriptNode	undoModuleNode[]	= new ScriptNode[nSelBoxModule];
				Point			undoModulePos[]	= new Point[nSelBoxModule];

				int moduleNum = dgm.getNModules();
				int nModule = 0;
				for (int n=0; n<moduleNum; n++) {
					Module module = new Module(getWorld(), dgm.getModule(n));
					int modulex = module.getXPosition();
					int moduley = module.getYPosition();
					int moduleSize = module.getSize();
					if (selBoxRect.contains(modulex, moduley) == true && selBoxRect.contains(modulex+moduleSize, moduley+moduleSize) == true) {
						undoModuleNode[nModule] = dgm.getModule(n);
						undoModulePos[nModule] = new Point(modulex, moduley);
						module.setPosition(modulex + offsetx, moduley + offsety);
						nModule++;
					}
				}

				addUndoAction(new DiagramMoveSelectingBoxUndoAction(undoModuleNode, undoModulePos));

				setSelectingBoxLocation(selBoxRect.x + offsetx, selBoxRect.y + offsety);
			}
			break;
		}
				
		setMouseButton(0);
		setDrawMode(DRAWMODE_NORMAL);
		
		repaint();
	} 

	public void mouseDragged(MouseEvent e) {
		if (getMouseButton() == 0)
			return;
		
		int mx = e.getX();
		int my = e.getY();
		
		if (mx == getMouseStartXPosition() && my == getMouseStartYPosition())
			return;
					
		ScriptNode selectedModuleNode = getSelectingModuleNode();
		if (selectedModuleNode != null) {
			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE) == true) { 
//				Module module = new Module(getWorld(), selectedModuleNode);
//				module.setPosition(e.getX(), e.getY());
				if (getGridSnap() == true) {
					mx = toGridSnapPosition(mx);
					my = toGridSnapPosition(my);
				}
				setDraggingModulePostion(mx, my);
				setDrawMode(DRAWMODE_DRAGGING_MODULE);
				repaint();
			}
			
			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) || isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE) || isOperationMode(MODULE_INSIDE_EXECUTIONNODE)) {
				Module module = new Module(getWorld(), selectedModuleNode);
				int nodeHalfSize = module.getNodeSize() / 2;
				int	sx, sy;
			
				if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE)) {
					sx = module.getEventOutNodeXPosition(getSelectingEventOutNode());
					sy = module.getEventOutNodeYPosition(getSelectingEventOutNode());
				}
				else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE)) {
					sx = module.getEventInNodeXPosition(getSelectingEventInNode());
					sy = module.getEventInNodeYPosition(getSelectingEventInNode());
				}
				else { //(isOperationMode(MODULE_INSIDE_EXECUTIONNODE)) {
					sx = module.getExecutionNodeXPosition();
					sy = module.getExecutionNodeYPosition();
				}
				setDraggingMousePostion(sx, sy, mx, my);
				setDrawMode(DRAWMODE_DRAGGING_MODULELINE);
				repaint();
			}
		}
		else {
			switch (getOperationMode()) {
			case OPERATIONMODE_SELECTING_BOX:
				{	
					if (getGridSnap() == true) {
						mx = toGridSnapPosition(mx);
						my = toGridSnapPosition(my);
					}
					setDraggingMousePostion(mx, my);
					setDrawMode(DRAWMODE_DRAGGING_SELECTINGBOX);
					repaint();
				}
				break;
			case OPERATIONMODE_MOVING_SELECTING_BOX:
				{	
					if (getGridSnap() == true) {
						mx = toGridSnapPosition(mx);
						my = toGridSnapPosition(my);
					}
					setDraggingMousePostion(mx, my);
					setDrawMode(DRAWMODE_MOVING_SELECTINGBOX);
					repaint();
				}
				break;
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	//////////////////////////////////////////////////
	// KeyListener
	//////////////////////////////////////////////////

	public void deleteObject() {
		Debug.message("DiagramFrame::deleteObject");
		
		int				operationMode = getOperationMode();
		ScriptNode	selectedNode = getSelectingModuleNode();
		
		Debug.message("\tmode   = " + operationMode);
		Debug.message("\tmodule = " + selectedNode);
		
		if (selectedNode != null) {
			if (isOperationMode(OPERATIONMODE_SELECTING_MODULE) == true) {
				Debug.message("\tmode = OPERATIONMODE_SELECTING_MODULE");
				Diagram		dgm = new Diagram(getWorld(), getDiagramNode());
				Module			module = new Module(getWorld(), selectedNode);
				addUndoAction(new DiagramDeleteModuleUndoAction(selectedNode));
				dgm.removeModule(module);
			}
			else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_OUTNODE) == true) {
				Debug.message("\tmode = OPERATIONMODE_SELECTING_MODULE_OUTNODE");
				ScriptNode	eventOutNode = selectedNode;
				Module		outModule = new Module(getWorld(), eventOutNode);
				Field		eventOutField = outModule.getEventOutField(getSelectingEventOutNode());
				Route		route = getWorld().getSceneGraph().getRoutes();
				while (route != null) {
					Route nextRoute = route.next();
					if (route.getEventOutNode() == eventOutNode && route.getEventOutField() == eventOutField) {
						addUndoAction(new DiagramDeleteModuleLineUndoAction(route.getEventOutNode(), route.getEventOutField(), route.getEventInNode(), route.getEventInField()));
						getWorld().getSceneGraph().removeRoute(route);
					}
					route = nextRoute;
				}
			}
			else if (isOperationMode(OPERATIONMODE_SELECTING_MODULE_INNODE) == true) {
				Debug.message("\tmode = OPERATIONMODE_SELECTING_MODULE_INNODE");
				ScriptNode	eventInNode	= selectedNode;
				Module		inModule = new Module(getWorld(), eventInNode);
				Field		eventInField = inModule.getEventInField(getSelectingEventInNode());
				Route		route = getWorld().getSceneGraph().getRoutes();
				while (route != null) {
					Route nextRoute = route.next();
					if (route.getEventInNode() == eventInNode && route.getEventInField() == eventInField) {
						addUndoAction(new DiagramDeleteModuleLineUndoAction(route.getEventOutNode(), route.getEventOutField(), route.getEventInNode(), route.getEventInField()));
						getWorld().getSceneGraph().removeRoute(route);
					}
					route = nextRoute;
				}
			}
			else if (isOperationMode(MODULE_INSIDE_EXECUTIONNODE) == true) {
				Debug.message("\tmode = MODULE_INSIDE_EXECUTIONNODE");
				ScriptNode	eventInNode	= selectedNode;
				Module		inModule = new Module(getWorld(), eventInNode);
				Field		eventInField = inModule.getExecutionField();
				Route		route = getWorld().getSceneGraph().getRoutes();
				while (route != null) {
					Route nextRoute = route.next();
					if (route.getEventInNode() == eventInNode && route.getEventInField() == eventInField) {
						addUndoAction(new DiagramDeleteModuleLineUndoAction(route.getEventOutNode(), route.getEventOutField(), route.getEventInNode(), route.getEventInField()));
						getWorld().getSceneGraph().removeRoute(route);
					}
					route = nextRoute;
				}
			}
			else 
				Debug.message("\tmode = NONE");
				
			setSelectingModuleNode(null);
			setOperationMode(OPERATIONMODE_NONE);			
			
			repaint();
		}
	}
		
	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		Debug.message("DiagramFrame::keyPressed = " + e.getKeyText(e.getKeyCode()));
		if (e.getKeyCode() == e.VK_DELETE)
			deleteObject();
	}

	public void keyReleased(KeyEvent e) {
	}
}
