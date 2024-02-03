/******************************************************************
*
*	Simple VRML Viewer for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File:	OrthoViewJava3D.java
*
******************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import vrml.*;
import vrml.node.*;
import vrml.j3d.*;

public class OrthoViewJava3D extends JFrame implements MouseListener, MouseMotionListener {

	private World		mWorld;					
	private	Canvas3D	mCanvas3D;

	public void setWorld(World world) {
		mWorld = world;
	}

	public World getWorld() {
		return mWorld;
	}

	public void setCanvas3D(Canvas3D canvas3D) {
		mCanvas3D = canvas3D;
	}

	public Canvas3D getCanvas3D() {
		return mCanvas3D;
	}

	public SceneGraph getSceneGraph() {
		return getWorld().getSceneGraph();
	}
	
	public SceneGraphJ3dObject getSceneGraphObject() {
		return (SceneGraphJ3dObject)getSceneGraph().getObject();
	}
	
	public OrthoViewJava3D(World world){
		super("OrthoView Viewer");
		
		setWorld(world);

		getContentPane().setLayout(new BorderLayout());
//		getContentPane().add("North", new PerspectiveViewToolBar(this, world));

		Canvas3D c = new Canvas3D(null);
		getContentPane().add("Center", c);

		c.addMouseListener(this);
		c.addMouseMotionListener(this);

		createView(c);
		
		setSize(320,320);
		show();
	}

	private View			mView				= null;
	private	TransformGroup	mViewTransformGroup	= null;
						
	public void createView(Canvas3D canvas3D) {

		// Create a root node
		RootNode rootNode = getSceneGraph().getRootNode();
		RootNodeObject rootNodeObject = (RootNodeObject)rootNode.getObject();
		
		// Create the view platform
		Transform3D t = new Transform3D();
		//t.set(new Vector3f(0.0f, 0.0f, 0.0f));
		//t.ortho(10, -10, 10, -10, 1000, 0);
		ViewPlatform vp = new ViewPlatform();
		vp.setActivationRadius(1000.0f);
		mViewTransformGroup = new TransformGroup(t);
		mViewTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		mViewTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		mViewTransformGroup.addChild(vp);
		getSceneGraphObject().removeBranchGroup();
		rootNodeObject.addChild(mViewTransformGroup);
		getSceneGraphObject().addBranchGroup();

		// Create the physical body and environment
		PhysicalBody body = new PhysicalBody();
		PhysicalEnvironment environment = new PhysicalEnvironment();

		// Create the view
		mView = new View();
		mView.setProjectionPolicy(View.PARALLEL_PROJECTION);

		// Attach the canvas and the physical body and environment to the view
		mView.addCanvas3D(canvas3D);
		mView.setPhysicalBody(body);
		mView.setPhysicalEnvironment(environment);
		mView.attachViewPlatform(vp);
	}

	////////////////////////////////////////////////
	//	mouse
	////////////////////////////////////////////////

	private int	mMouseX = 0;
	private int	mMouseY = 0;
	private int	mMouseButton = 0;

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mMouseButton = 1;
	}

	public void mouseReleased(MouseEvent e) {
		mMouseButton = 0;
	}

	public void mouseDragged(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mMouseX = e.getX();
		mMouseY = e.getY();
	}

	public int getMouseX() {
		return mMouseX;
	}

	public int getMouseY() {
		return mMouseY;
	}

	public int getMouseButton() {
		return mMouseButton;
	}
}
