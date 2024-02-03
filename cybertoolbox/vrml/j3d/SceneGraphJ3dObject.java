/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraphJ3dObject.java
*
******************************************************************/

package vrml.j3d;

import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.picking.*;

import vrml.*;
import vrml.node.*;
import vrml.util.Debug;

public class SceneGraphJ3dObject extends Object implements vrml.SceneGraphObject {

	private VirtualUniverse	mUniverse3D				= null;
	private Locale				mLocale					= null;
	private RootNodeObject	mRootNode				= null;	
	private View				mView						= null;
	private Background		mBackground				= null;
	private TransformGroup	mViewTransformGroup	= null;
	private boolean			mIsBranchGroupAdded	= false;
	private Canvas3D			mCanvas3D				= null;
	private PickObject		mPickObject				= null;
	private AmbientLight		mAmbientLight			= null;
	private PointLight		mPointLight				= null;

	public SceneGraphJ3dObject(SceneGraph sg) {
		// Create a root node
		RootNode rootNode = sg.getRootNode();
		mRootNode = new RootNodeObject(rootNode);	
		rootNode.setObject(mRootNode); 
	}

							
	public SceneGraphJ3dObject(Canvas3D canvas3D, SceneGraph sg) {

		mCanvas3D	= canvas3D;
		mUniverse3D = new VirtualUniverse();
		mLocale		= new Locale(mUniverse3D);
		
		// Create a root node
		RootNode rootNode = sg.getRootNode();
		mRootNode = new RootNodeObject(rootNode);	
		rootNode.setObject(mRootNode); 
		
		// Create the view platform
		Transform3D t = new Transform3D();
		t.set(new Vector3f(0.0f, 0.0f, 0.0f));
		ViewPlatform vp = new ViewPlatform();
		vp.setActivationRadius(1000.0f);
		mViewTransformGroup = new TransformGroup(t);
		mViewTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		mViewTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		mViewTransformGroup.addChild(vp);
		mRootNode.addChild(mViewTransformGroup);

		// Create the physical body and environment
		PhysicalBody body = new PhysicalBody();
		PhysicalEnvironment environment = new PhysicalEnvironment();

		// Create the view
		mView = new View();
		//mView.setProjectionPolicy(View.PARALLEL_PROJECTION);
		//mView.setMonoscopicViewPolicy(View.LEFT_EYE_VIEW);
		//mView.setViewPolicy(View.HMD_VIEW);

		// Attach the canvas and the physical body and environment to the view
		mView.addCanvas3D(canvas3D);
		mView.setPhysicalBody(body);
		mView.setPhysicalEnvironment(environment);
		mView.attachViewPlatform(vp);
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);
		// Add a background color
		Color3f bgColor = new Color3f(0.0f, 0.0f, 0.0f);
		mBackground = new Background(bgColor);
		mBackground.setApplicationBounds(bounds);
		mBackground.setCapability(Background.ALLOW_APPLICATION_BOUNDS_READ);
		mBackground.setCapability(Background.ALLOW_APPLICATION_BOUNDS_WRITE);
		mBackground.setCapability(Background.ALLOW_COLOR_READ);
		mBackground.setCapability(Background.ALLOW_COLOR_WRITE);
		mRootNode.addChild(mBackground);
	
		// Create an ambient Light
		mAmbientLight = new AmbientLight(new Color3f(0.0f, 0.0f, 0.0f));
		mAmbientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_READ);
		mAmbientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
		mAmbientLight.setCapability(AmbientLight.ALLOW_COLOR_READ);
		mAmbientLight.setCapability(AmbientLight.ALLOW_COLOR_WRITE);
		mAmbientLight.setEnable(false);
		mRootNode.addChild(mAmbientLight);

		// Create a headlight
		mPointLight = new PointLight();
		mPointLight.setColor(new Color3f(0.8f, 0.8f, 0.8f));
		mPointLight.setCapability(PointLight.ALLOW_INFLUENCING_BOUNDS_READ);
		mPointLight.setCapability(PointLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_STATE_READ);
		mPointLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_ATTENUATION_READ);
		mPointLight.setCapability(PointLight.ALLOW_ATTENUATION_WRITE);
		mPointLight.setCapability(PointLight.ALLOW_POSITION_READ);
		mPointLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
		mPointLight.setAttenuation(1, 0, 0);
		mPointLight.setEnable(false);
		mRootNode.addChild(mPointLight);
	
		// Create Pick Object
		mPickObject = new PickObject(canvas3D, mRootNode);
		
		addBranchGroup();
	}

	///////////////////////////////////////////////
	// Access Members
	///////////////////////////////////////////////
	
	public RootNodeObject getRootNode() {
		return mRootNode;
	}

	public BranchGroup getBranchGroup() {
		return mRootNode;
	}

	public Locale getLocale() {
		return mLocale;
	}

	public Canvas3D getCanvas3D() {
		return mCanvas3D;
	}

	public View getView() {
		return mView;
	}

	public Background getBackground() {
		return mBackground;
	}
	
	public TransformGroup getViewTransformGroup() {
		return mViewTransformGroup;
	}

	public AmbientLight getAmbientLight() {
		return mAmbientLight;
	}

	public PointLight getHeadlight() {
		return mPointLight;
	}

	///////////////////////////////////////////////
	// Headlight
	///////////////////////////////////////////////
	
	public void setHeadlightState(boolean state) {
		if (mPointLight == null)
			return;
		mPointLight.setEnable(state);
	}

	public boolean isHeadlightOn() {
		if (mPointLight == null)
			return false;
		return mPointLight.getEnable();
	}

	///////////////////////////////////////////////
	// Add/Remove BranchGroup
	///////////////////////////////////////////////
	
	public void addBranchGroup() {
		if (mLocale == null)
			return;
		if (mIsBranchGroupAdded == false) {
			mLocale.addBranchGraph(mRootNode);
			mIsBranchGroupAdded = true;
		}
	}

	public void removeBranchGroup() {
		if (mLocale == null)
			return;
		if (mIsBranchGroupAdded == true) {
			mLocale.removeBranchGraph(mRootNode);
			mIsBranchGroupAdded = false;
		}
	}

	///////////////////////////////////////////////
	// Add/Remove Node
	///////////////////////////////////////////////
	
	public boolean addNode(SceneGraph sg, vrml.node.Node node) {
		Debug.message("SceneGraphJ3dObject::addNode = " + sg + ", " + node);
		if (node.isRootNode() == true) {
			Debug.warning("\tCouldn't add a root node !!");
			return false;
		}
		removeBranchGroup();
		boolean ret = node.addObject();
		addBranchGroup();
		return ret;
	}
	
	public boolean removeNode(SceneGraph sg, vrml.node.Node node) {
		Debug.message("SceneGraphJ3dObject::removeNode = " + sg + ", " + node);
		if (node.isRootNode() == true) {
			Debug.warning("\tCouldn't remove a root node !!");
			return false;
		}
		removeBranchGroup();
		boolean ret = node.removeObject();
		addBranchGroup();
		return ret;
	}
								
	///////////////////////////////////////////////
	// Initialize / Uninitialize
	///////////////////////////////////////////////
	
	public boolean initialize(SceneGraph sg) {

		removeBranchGroup();
		
		for (vrml.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal()) {
			if (node.hasObject() == false)
				node.recreateNodeObject();
		}

		update(sg);
		
		float	sceneGraphRadius = sg.getRadius() * 10.0f;
		
		View view = getView();
		if (view != null)
			view.setBackClipDistance(sceneGraphRadius);

		Background bg = getBackground();
		if (bg != null) {
			float bboxCenter[] = sg.getBoundingBoxCenter();
			Point3d center = new Point3d(bboxCenter[0], bboxCenter[1], bboxCenter[2]);
			BoundingSphere bounds = new BoundingSphere(center, sceneGraphRadius);
			bg.setApplicationBounds(bounds);
		}
			
		addBranchGroup();
		
		return true;
	}

	public boolean uninitialize(SceneGraph sg) {
		return true;
	}
	
	///////////////////////////////////////////////
	// Update
	///////////////////////////////////////////////

	public Bounds getSceneGraphBounds(SceneGraph sg, ViewpointNode viewNode) {
		float viewPos[] = new float[3];
		viewNode.getPosition(viewPos);
		float sgRadius = sg.getRadius() * 1000.0f;
		if (sgRadius == 0.0) 
			sgRadius = 1000.0f;
		return new BoundingSphere(new Point3d(viewPos[0], viewPos[1], viewPos[2]), sgRadius);
	}	
	
	public void updateHeadlight(SceneGraph sg, ViewpointNode viewNode) {
	
		PointLight light = getHeadlight();
		
		if (light == null)
			return;
			
		// Update Position
		float pos[]	= new float[3];
		viewNode.getPosition(pos);
		light.setPosition(new Point3f(pos));
		
		// Update InfluencingBounds
		light.setInfluencingBounds(getSceneGraphBounds(sg, viewNode));
	}
	
	public void updateViewInfomation(ViewpointNode viewNode) {
	
		View view = getView();

		if (view == null)
			return;
			
		float position[]	= new float[3];
		float orientation[]	= new float[4];
		viewNode.getPosition(position);
		viewNode.getOrientation(orientation);
		float fov = viewNode.getFieldOfView();
						
		TransformGroup	viewTrans = getViewTransformGroup();
		Transform3D trans3D = new Transform3D();
		viewTrans.getTransform(trans3D);
		Vector3f vector = new Vector3f(position);
		trans3D.setTranslation(vector);
		AxisAngle4f axisAngle = new AxisAngle4f(orientation);
		trans3D.setRotation(axisAngle);
		viewTrans.setTransform(trans3D);
/*
		Transform3D trans3D = new Transform3D();
		Vector3f vector = new Vector3f(position);
		trans3D.setTranslation(vector);
		AxisAngle4f axisAngle = new AxisAngle4f(orientation);
		trans3D.setRotation(axisAngle);
		view.setLeftProjection(trans3D);
		view.setVpcToEc(trans3D);
*/		
		view.setFieldOfView(fov);
	}

	public void updateBackground(SceneGraph sg, BackgroundNode bgNode, ViewpointNode viewNode) {
		
		Background bg = getBackground();
		if (bg == null)
			return;
			
		// Update Color
		Color3f bgColor = null;
		if (0 < bgNode.getNSkyColors()) {
			float color[] = new float[3];
			bgNode.getSkyColor(0, color);
			bgColor = new Color3f(color);
		}
		else
			bgColor = new Color3f(0.0f, 0.0f, 0.0f);
		bg.setColor(bgColor);

		// Update ApplicationBounds
		bg.setApplicationBounds(getSceneGraphBounds(sg, viewNode));
	}
	
	public void updateNodes(SceneGraph sg) {
		for (vrml.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal())
			node.updateObject();
	}
		
	public boolean update(SceneGraph sg) {
		
		ViewpointNode view = sg.getViewpointNode();
		if (view == null)
			view = sg.getDefaultViewpointNode(); 
		
		updateViewInfomation(view);

		NavigationInfoNode navInfo = sg.getNavigationInfoNode();
		if (navInfo == null)
			navInfo = sg.getDefaultNavigationInfoNode();
			
		boolean headlightOn = navInfo.getHeadlight();
		setHeadlightState(headlightOn);
		if (headlightOn == true)
			updateHeadlight(sg, view);
		
		BackgroundNode bg = sg.getBackgroundNode();
		if (bg == null)
			bg = sg.getDefaultBackgroundNode();
		updateBackground(sg, bg, view);

		updateNodes(sg);
		return true;
	}
	
	///////////////////////////////////////////////
	// remove
	///////////////////////////////////////////////
	
	public boolean remove(SceneGraph sg) {
		removeBranchGroup();
		
		for (vrml.node.Node node = sg.getNodes(); node != null; node = node.nextTraversal()) {
			NodeObject nodeObject = node.getObject();
			if (nodeObject != null)
				nodeObject.remove(node);
		}

		addBranchGroup();
		
		return true;
	}

	///////////////////////////////////////////////
	// Start / Stop
	///////////////////////////////////////////////

	public boolean	start(SceneGraph sg) {
		Debug.message("SceneGraphJ3dObject.start");
		
		View view = getView();
		if (view != null) {
			view.startBehaviorScheduler();
			view.startView();
		}
		
		Canvas3D canvas3d = getCanvas3D();
		if (canvas3d != null)
			canvas3d.startRenderer();
			
		return true;
	}
	
	public boolean	stop(SceneGraph sg) {
		Debug.message("SceneGraphJ3dObject.stop");

		View view = getView();
		if (view != null) {
			view.stopBehaviorScheduler();
			view.stopView();
		}
		
		Canvas3D canvas3d = getCanvas3D();
		if (canvas3d != null)
			canvas3d.stopRenderer();
			
		return true;
	}
	
	///////////////////////////////////////////////
	// Create J3D Node
	///////////////////////////////////////////////
	
	public NodeObject createNodeObject(SceneGraph sg, vrml.node.Node node) {
		NodeObject nodeObject = null;
			
		if (node.isAnchorNode())
			nodeObject = new AnchorNodeObject((AnchorNode)node);
		else if (node.isAppearanceNode()) 
			nodeObject = new AppearanceNodeObject((AppearanceNode)node);
/*
		else if (isAudioClipNode())
			nodeObject = new AudioClipNode();
*/
		else if (node.isBackgroundNode())
			nodeObject = null;
		else if (node.isBillboardNode())
			nodeObject = new BillboardNodeObject((BillboardNode)node);
		else if (node.isBoxNode())
			nodeObject = new BoxNodeObject((BoxNode)node);
		else if (node.isCollisionNode())
			nodeObject = new CollisionNodeObject((CollisionNode)node);
		else if (node.isColorNode())
			nodeObject = null;
		else if (node.isColorInterpolatorNode())
			nodeObject = null;
		else if (node.isConeNode())
			nodeObject = new ConeNodeObject((ConeNode)node);
		else if (node.isCoordinateNode())
			nodeObject = null;
		else if (node.isCoordinateInterpolatorNode())
			nodeObject = null;
		else if (node.isCylinderNode())
			nodeObject = new CylinderNodeObject((CylinderNode)node);
/*
		else if (isCylinderSensorNode())
			nodeObject = new CylinderSensorNode();
*/
		else if (node.isDirectionalLightNode())
			nodeObject = new DirectionalLightNodeObject((DirectionalLightNode)node);

		else if (node.isElevationGridNode())
			nodeObject = new ElevationGridNodeObject((ElevationGridNode)node);
		else if (node.isExtrusionNode())
			nodeObject = new ExtrusionNodeObject((ExtrusionNode)node);
		else if (node.isFogNode()) {
			FogNode fogNode = (FogNode)node;
			if (fogNode.isLiner() == true)
				nodeObject = new LinerFogNodeObject(fogNode);
			if (fogNode.isExponential() == true)
				nodeObject = new ExponentialFogNodeObject(fogNode);
		}
		else if (node.isFontStyleNode())
			nodeObject = null;
		else if (node.isGroupNode())
			nodeObject = new GroupNodeObject((GroupNode)node);
		else if (node.isImageTextureNode()) {
			nodeObject = null;
			ImageTextureNode imgTex = (ImageTextureNode)node;
			Canvas3D canvas3d = getCanvas3D();
			if (canvas3d != null) {
				ImageTextureLoader imgTexLoader = new ImageTextureLoader(imgTex, canvas3d);
				nodeObject = new ImageTextureNodeObject(imgTexLoader);
			}
		}
		else if (node.isIndexedFaceSetNode())
			nodeObject = new IndexedFaceSetNodeObject((IndexedFaceSetNode)node);
		else if (node.isIndexedLineSetNode()) 
			nodeObject = new IndexedLineSetNodeObject((IndexedLineSetNode)node);
		else if (node.isInlineNode()) 
			nodeObject = new InlineNodeObject((InlineNode)node);
		else if (node.isLODNode())
			nodeObject = new LODNodeObject((LODNode)node);
		else if (node.isMaterialNode())
			nodeObject = new MaterialNodeObject((MaterialNode)node);
/*
		else if (isMovieTextureNode())
			nodeObject = new MovieTextureNode();
*/
		else if (node.isNavigationInfoNode())
			nodeObject = null;
		else if (node.isNormalNode())
			nodeObject = null;
		else if (node.isNormalInterpolatorNode())
			nodeObject = null;
		else if (node.isOrientationInterpolatorNode())
			nodeObject = null;
/*
		else if (isPixelTextureNode())
			nodeObject = new PixelTextureNode();
		else if (isPlaneSensorNode())
			nodeObject = new PlaneSensorNode();
*/
		else if (node.isPointLightNode())
			nodeObject = new PointLightNodeObject((PointLightNode)node);
		else if (node.isPointSetNode())
			nodeObject = new PointSetNodeObject((PointSetNode)node);
		else if (node.isPositionInterpolatorNode())
			nodeObject = null;
/*
		else if (isProximitySensorNode())
			nodeObject = new ProximitySensorNode();
*/
		else if (node.isScalarInterpolatorNode())
			nodeObject = null;
		else if (node.isScriptNode())
			nodeObject = null;
		else if (node.isShapeNode())
			nodeObject = new ShapeNodeObject((ShapeNode)node);
/*
		else if (isSoundNode())
			nodeObject = new SoundNode();
*/
		else if (node.isSphereNode())
			nodeObject = new SphereNodeObject((SphereNode)node);
/*
		else if (isSphereSensorNode())
			nodeObject = new SphereSensorNode();
*/
		else if (node.isSpotLightNode())
			nodeObject = new SpotLightNodeObject((SpotLightNode)node);
		else if (node.isSwitchNode())
			nodeObject = new SwitchNodeObject((SwitchNode)node);
		else if (node.isTextNode())
			nodeObject = new TextNodeObject((TextNode)node);
/*
		else if (isTextureCoordinateNode())
			nodeObject = new TextureCoordinateNode();
		else if (isTextureTransformNode())
			nodeObject = new TextureTransformNode();
		else if (isTimeSensorNode())
			nodeObject = new TimeSensorNode();
		else if (isTouchSensorNode())
			nodeObject = new TouchSensorNode();
*/
		else if (node.isTransformNode())
			nodeObject = new TransformNodeObject((TransformNode)node);
		else if (node.isViewpointNode())
			nodeObject = null;
/*
		else if (isVisibilitySensorNode())
			nodeObject = new VisibilitySensorNode();
*/
		else if (node.isWorldInfoNode())
			nodeObject = null;
			
		return nodeObject;
	}

	////////////////////////////////////////////////
	// Redering mode
	////////////////////////////////////////////////

	public boolean setRenderingMode(SceneGraph sg, int mode) {
		
		for (ShapeNode shapeNode = sg.findShapeNode(); shapeNode != null; shapeNode = (ShapeNode)shapeNode.nextTraversalSameType()) {
			ShapeNodeObject shapeObject = (ShapeNodeObject)shapeNode.getObject();
			if (shapeNode == null)
				continue;
			Appearance app = shapeObject.getAppearance();
			if (app == null)
				continue;
			PolygonAttributes polyAttr = app.getPolygonAttributes(); 
			if (polyAttr == null)
				continue;
			if (mode == SceneGraph.RENDERINGMODE_LINE) 
				polyAttr.setPolygonMode(PolygonAttributes.POLYGON_LINE);
			else if (mode == SceneGraph.RENDERINGMODE_FILL)
				polyAttr.setPolygonMode(PolygonAttributes.POLYGON_FILL);
			app.setPolygonAttributes(polyAttr);
		}
		
		return true;
	}
	
	////////////////////////////////////////////////
	// pick object
	////////////////////////////////////////////////

	public Shape3D pickShape3D(int mx, int my) {
		SceneGraphPath sgPath = mPickObject.pickClosest(mx, my, mPickObject.USE_BOUNDS);

		if (sgPath == null)
			return null;
		Shape3D shape3d = (Shape3D)mPickObject.pickNode(sgPath, mPickObject.SHAPE3D, 1);
		
		return shape3d;
	}

	public ShapeNode pickShapeNode(SceneGraph sg, int mx, int my) {
		Debug.message("SceneGraphJ3dObject::pickShapeNode = " + mx + ", " + my);		
		
		Shape3D pickedShape3D = pickShape3D(mx, my);
		
		ShapeNode pickedShapeNode = null;
		
		if (pickedShape3D != null) {
			for (ShapeNode shape = sg.findShapeNode(); shape != null; shape = (ShapeNode)shape.nextTraversalSameType()) {
				if (shape.getObject() == pickedShape3D) {
					pickedShapeNode = shape;
					break;
				}
			}
		}
		
		Debug.message("\tPicked Shape3D = " + pickedShape3D);		
		Debug.message("\tPicked Shape   = " + pickedShapeNode);
		
		return pickedShapeNode;
	}

	////////////////////////////////////////////////
	// Infomation
	////////////////////////////////////////////////

	public void addNodeString(StringBuffer stringBuffer, javax.media.j3d.Node node, int indent) {
		int n;
		
		for (n=0; n<indent; n++) 
			stringBuffer.append('\t');
		stringBuffer.append(node.toString());
		stringBuffer.append('\n');
		
		try {
			Group gnode = (Group)node;
			for (n=0; n<gnode.numChildren(); n++)
				addNodeString(stringBuffer, gnode.getChild(n), indent + 1);
		}
		catch (CapabilityNotSetException e) {}
		catch (ClassCastException e) {}
	}
	
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		addNodeString(stringBuffer, getRootNode(), 0);
		return stringBuffer.toString();
	}
}
