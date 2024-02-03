/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SceneGraph.java
*
******************************************************************/

package vrml;

import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.NoSuchElementException;

import vrml.util.*;
import vrml.parser.*;
import vrml.node.*;
import vrml.field.*;
import vrml.route.*;

public class SceneGraph extends LinkedListNode implements Constants, Runnable {

	static public final int	USE_PREPROCESSOR			= 0x01;
	static public final int	NORMAL_GENERATION			= 0x02;
	static public final int	TEXTURE_GENERATION			= 0x04;
  
	public void initializeMember() {
		setHeaderFlag(false);
		setOption(0);
		setViewpointStartPosition(0.0f, 0.0f, 0.0f);
		setViewpointStartOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public SceneGraph() {
		initializeMember();
	}

	public SceneGraph(int option) {
		initializeMember();
		setOption(option);
	}
	
	public SceneGraph(String filename) {
		initializeMember();
		load(filename);
	}

	public SceneGraph(File file) {
		initializeMember();
		load(file);
	}

	////////////////////////////////////////////////
	//	Node List
	////////////////////////////////////////////////

	private RootNode	mRootNode = new RootNode(this);

	public RootNode getRootNode() {
		return mRootNode;		
	}

	public int getNNodes() {
		return getRootNode().getNChildNodes();		
	}

	public Node getNode(int n) {
		return getRootNode().getChildNode(n);		
	}

	public Node getNodes() {
		return getRootNode().getChildNodes();		
	}

	public void addNode(Node node) {
		RootNode rootNode = getRootNode();
		rootNode.addChildNode(node);
	}

	public void moveNode(Node node) {
		RootNode rootNode = getRootNode();
		rootNode.moveChildNode(node);
	}

	public void clearNodes() {
		getRootNode().removeChildNodes();		
	}

	public Node getNodes(String typeName) {
		Node node = getNodes();
		if (node == null)
			return null;
		String nodeString = node.getType();
		if (nodeString.compareTo(typeName) == 0)
			return node;
		else
			return node.next(typeName);
	}

	///////////////////////////////////////////////
	//	Route
	///////////////////////////////////////////////

	private LinkedList	mRouteList	= new LinkedList();

	public Route getRoutes() {
		return (Route)mRouteList.getNodes();
	}

	public Route getRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		for (Route route=getRoutes(); route!=null; route=route.next()) {
			if (eventOutNode == route.getEventOutNode() && eventOutField == route.getEventOutField() &&
				eventInNode == route.getEventInNode() && eventInField == route.getEventInField() ) {
				return route;
			}
		}
		return null;
	}

	public Route addRoute(Route route) {
		if (route.getEventOutNode() == route.getEventInNode()) {
			Debug.warning("Invalidate route infomation = " + route);
			return null;
		}
		if (getRoute(route.getEventOutNode(), route.getEventOutField(), route.getEventInNode(), route.getEventInField()) != null) {
			Debug.message("The same route infomation is already added = " + route);
			return null;
		}
		mRouteList.addNode(route);
		return route;
	}

	public Route addRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		Route route = new Route(eventOutNode, eventOutField, eventInNode, eventInField);
		return addRoute(route);
	}

	public Route addRoute(String eventOutNodeName, String eventOutFieldName, String eventInNodeName, String eventInFieldName)
	{
		Node eventInNode = findNodeByName(eventInNodeName);
		Node eventOutNode = findNodeByName(eventOutNodeName);

		Field eventOutField = null;

		if (eventOutNode != null) {
			try {
				eventOutField = ((Node)eventOutNode).getEventOut(eventOutFieldName);
			}
			catch (InvalidEventOutException eventOutException) {
				try {
					eventOutField = eventOutNode.getExposedField(eventOutFieldName);
				}
				catch (InvalidExposedFieldException exposedFieldException) {}
			}
		}

		if (eventOutField == null)
			Debug.warning("Couldn't a field (" + eventOutNodeName + "::" + eventOutFieldName + ")");

		Field eventInField = null;

		if (eventInNode != null) {
			try {
				eventInField = eventInNode.getEventIn(eventInFieldName);
			}
			catch (InvalidEventInException eventInException) {
				try {
					eventInField = eventInNode.getExposedField(eventInFieldName);
				}
				catch (InvalidExposedFieldException exposedFieldException) {}
			}
		}

		if (eventInField == null)
			Debug.warning("Couldn't a field (" + eventInNodeName + "::" + eventInFieldName + ")");

		return addRoute(eventOutNode, eventOutField, eventInNode, eventInField);
	}

	public void removeRoute(Node eventOutNode, Field eventOutField, Node eventInNode, Field eventInField)
	{
		Route route = getRoute(eventOutNode, eventOutField, eventInNode, eventInField);
		if (route!=null)
			route.remove();
	}

	void removeEventInFieldRoutes(Node node, Field field) {
		Route	route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (route.getEventInNode() == node && route.getEventInField() == field)
				route.remove();
			route = nextRoute;
		}
	}

	public void removeEventOutFieldRoutes(Node node, Field field) {
		Route	route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (route.getEventOutNode() == node && route.getEventOutField() == field)
				route.remove();
			route = nextRoute;
		}
	}

	public void removeRoutes(Node node) {
		Route route = getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			if (node == route.getEventInNode() || node == route.getEventOutNode())
				route.remove();
			route = nextRoute;
		}
	}

	public void removeRoutes(Node node, Field field) {
		removeEventInFieldRoutes(node, field);
		removeEventOutFieldRoutes(node, field);
	}

	public void removeRoute(Route removeRoute)
	{
		for (Route route=getRoutes(); route!=null; route=route.next()) {
			if (removeRoute == route) {
				route.remove();
				return;
			}
		}
	}

	////////////////////////////////////////////////
	//	find node
	////////////////////////////////////////////////

	public Node findNodeByType(String typeName) {
		Node rootNode = getRootNode();
		return rootNode.nextTraversalByType(typeName);
	}

	public Node findNodeByName(String name) {
		Node rootNode = getRootNode();
		return rootNode.nextTraversalByName(name);
	}

	////////////////////////////////////////////////
	//	child node list
	////////////////////////////////////////////////

	public GroupingNode getGroupingNodes() {
		for (Node node = getNodes(); node != null; node = node.next()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public AnchorNode getAnchorNodes() {
		return (AnchorNode)getNodes(anchorTypeName);
	}

	public AppearanceNode getAppearanceNodes() {
		return (AppearanceNode)getNodes(appearanceTypeName);
	}

	public AudioClipNode getAudioClipNodes() {
		return (AudioClipNode)getNodes(audioClipTypeName);
	}

	public BackgroundNode getBackgroundNodes() {
		return (BackgroundNode)getNodes(backgroundTypeName);
	}

	public BillboardNode getBillboardNodes() {
		return (BillboardNode)getNodes(billboardTypeName);
	}

	public BoxNode getBoxNodes() {
		return (BoxNode)getNodes(boxTypeName);
	}

	public CollisionNode getCollisionNodes() {
		return (CollisionNode)getNodes(collisionTypeName);
	}

	public ColorNode getColorNodes() {
		return (ColorNode)getNodes(colorTypeName);
	}

	public ColorInterpolatorNode getColorInterpolatorNodes() {
		return (ColorInterpolatorNode)getNodes(colorInterpolatorTypeName);
	}

	public ConeNode getConeNodes() {
		return (ConeNode)getNodes(coneTypeName);
	}

	public CoordinateNode getCoordinateNodes() {
		return (CoordinateNode)getNodes(coordinateTypeName);
	}

	public CoordinateInterpolatorNode getCoordinateInterpolatorNodes() {
		return (CoordinateInterpolatorNode)getNodes(coordinateInterpolatorTypeName);
	}

	public CylinderNode getCylinderNodes() {
		return (CylinderNode)getNodes(cylinderTypeName);
	}

	public CylinderSensorNode getCylinderSensorNodes() {
		return (CylinderSensorNode)getNodes(cylinderSensorTypeName);
	}

	public DirectionalLightNode getDirectionalLightNodes() {
		return (DirectionalLightNode)getNodes(directionalLightTypeName);
	}

	public ElevationGridNode getElevationGridNodes() {
		return (ElevationGridNode)getNodes(elevationGridTypeName);
	}

	public ExtrusionNode getExtrusionNodes() {
		return (ExtrusionNode)getNodes(extrusionTypeName);
	}

	public FogNode getFogNodes() {
		return (FogNode)getNodes(fogTypeName);
	}

	public FontStyleNode getFontStyleNodes() {
		return (FontStyleNode)getNodes(fontStyleTypeName);
	}

	public GroupNode getGroupNodes() {
		return (GroupNode)getNodes(groupTypeName);
	}

	public ImageTextureNode getImageTextureNodes() {
		return (ImageTextureNode)getNodes(imageTextureTypeName);
	}

	public IndexedFaceSetNode getIndexedFaceSetNodes() {
		return (IndexedFaceSetNode)getNodes(indexedFaceSetTypeName);
	}

	public IndexedLineSetNode getIndexedLineSetNodes() {
		return (IndexedLineSetNode)getNodes(indexedLineSetTypeName);
	}

	public InlineNode getInlineNodes() {
		return (InlineNode)getNodes(inlineTypeName);
	}

	public LODNode getLODNodes() {
		return (LODNode)getNodes(lodTypeName);
	}

	public MaterialNode getMaterialNodes() {
		return (MaterialNode)getNodes(materialTypeName);
	}

	public MovieTextureNode getMovieTextureNodes() {
		return (MovieTextureNode)getNodes(movieTextureTypeName);
	}

	public NavigationInfoNode getNavigationInfoNodes() {
		return (NavigationInfoNode)getNodes(navigationInfoTypeName);
	}

	public NormalNode getNormalNodes() {
		return (NormalNode)getNodes(normalTypeName);
	}

	public NormalInterpolatorNode getNormalInterpolatorNodes() {
		return (NormalInterpolatorNode)getNodes(normalInterpolatorTypeName);
	}

	public OrientationInterpolatorNode getOrientationInterpolatorNodes() {
		return (OrientationInterpolatorNode)getNodes(orientationInterpolatorTypeName);
	}

	public PixelTextureNode getPixelTextureNodes() {
		return (PixelTextureNode)getNodes(pixelTextureTypeName);
	}

	public PlaneSensorNode getPlaneSensorNodes() {
		return (PlaneSensorNode)getNodes(planeSensorTypeName);
	}

	public PointLightNode getPointLightNodes() {
		return (PointLightNode)getNodes(pointLightTypeName);
	}

	public PointSetNode getPointSetNodes() {
		return (PointSetNode)getNodes(pointSetTypeName);
	}

	public PositionInterpolatorNode getPositionInterpolatorNodes() {
		return (PositionInterpolatorNode)getNodes(positionInterpolatorTypeName);
	}

	public ProximitySensorNode getProximitySensorNodes() {
		return (ProximitySensorNode)getNodes(proximitySensorTypeName);
	}

	public ScalarInterpolatorNode getScalarInterpolatorNodes() {
		return (ScalarInterpolatorNode)getNodes(scalarInterpolatorTypeName);
	}

	public ScriptNode getScriptNodes() {
		return (ScriptNode)getNodes(scriptTypeName);
	}

	public ShapeNode getShapeNodes() {
		return (ShapeNode)getNodes(shapeTypeName);
	}

	public SoundNode getSoundNodes() {
		return (SoundNode)getNodes(soundTypeName);
	}

	public SphereNode getSphereNodes() {
		return (SphereNode)getNodes(sphereTypeName);
	}

	public SphereSensorNode getSphereSensorNodes() {
		return (SphereSensorNode)getNodes(sphereSensorTypeName);
	}

	public SpotLightNode getSpotLightNodes() {
		return (SpotLightNode)getNodes(spotLightTypeName);
	}

	public SwitchNode getSwitchNodes() {
		return (SwitchNode)getNodes(switchTypeName);
	}

	public TextNode getTextNodes() {
		return (TextNode)getNodes(textTypeName);
	}

	public TextureCoordinateNode getTextureCoordinateNodes() {
		return (TextureCoordinateNode)getNodes(textureCoordinateTypeName);
	}

	public TextureTransformNode getTextureTransformNodes() {
		return (TextureTransformNode)getNodes(textureTransformTypeName);
	}

	public TimeSensorNode getTimeSensorNodes() {
		return (TimeSensorNode)getNodes(timeSensorTypeName);
	}

	public TouchSensorNode getTouchSensorNodes() {
		return (TouchSensorNode)getNodes(touchSensorTypeName);
	}

	public TransformNode getTransformNodes() {
		return (TransformNode)getNodes(transformTypeName);
	}

	public ViewpointNode getViewpointNodes() {
		return (ViewpointNode)getNodes(viewpointTypeName);
	}

	public VisibilitySensorNode getVisibilitySensorNodes() {
		return (VisibilitySensorNode)getNodes(visibilitySensorTypeName);
	}

	public WorldInfoNode getWorldInfoNodes() {
		return (WorldInfoNode)getNodes(worldInfoTypeName);
	}

	////////////////////////////////////////////////
	//	find a node by type (Common)
	////////////////////////////////////////////////

	public GroupingNode findGroupingNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isGroupingNode())
				return (GroupingNode)node;
		}
		return null;
	}

	public LightNode findLightNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isLightNode())
				return (LightNode)node;
		}
		return null;
	}

	public GeometryNode findGeometryNode() {
		for (Node node = (getRootNode()).nextTraversal(); node != null; node = node.nextTraversal()) {
			if (node.isGeometryNode())
				return (GeometryNode)node;
		}
		return null;
	}

	////////////////////////////////////////////////
	//	find a node by type
	////////////////////////////////////////////////
	
	public AnchorNode findAnchorNode() {
		return (AnchorNode)findNodeByType(anchorTypeName);
	}

	public AppearanceNode findAppearanceNode() {
		return (AppearanceNode)findNodeByType(appearanceTypeName);
	}

	public AudioClipNode findAudioClipNode() {
		return (AudioClipNode)findNodeByType(audioClipTypeName);
	}

	public BackgroundNode findBackgroundNode() {
		return (BackgroundNode)findNodeByType(backgroundTypeName);
	}

	public BillboardNode findBillboardNode() {
		return (BillboardNode)findNodeByType(billboardTypeName);
	}

	public BoxNode findBoxNode() {
		return (BoxNode)findNodeByType(boxTypeName);
	}

	public CollisionNode findCollisionNode() {
		return (CollisionNode)findNodeByType(collisionTypeName);
	}

	public ColorNode findColorNode() {
		return (ColorNode)findNodeByType(colorTypeName);
	}

	public ColorInterpolatorNode findColorInterpolatorNode() {
		return (ColorInterpolatorNode)findNodeByType(colorInterpolatorTypeName);
	}

	public ConeNode findConeNode() {
		return (ConeNode)findNodeByType(coneTypeName);
	}

	public CoordinateNode findCoordinateNode() {
		return (CoordinateNode)findNodeByType(coordinateTypeName);
	}

	public CoordinateInterpolatorNode findCoordinateInterpolatorNode() {
		return (CoordinateInterpolatorNode)findNodeByType(coordinateInterpolatorTypeName);
	}

	public CylinderNode findCylinderNode() {
		return (CylinderNode)findNodeByType(cylinderTypeName);
	}

	public CylinderSensorNode findCylinderSensorNode() {
		return (CylinderSensorNode)findNodeByType(cylinderSensorTypeName);
	}

	public DirectionalLightNode findDirectionalLightNode() {
		return (DirectionalLightNode)findNodeByType(directionalLightTypeName);
	}

	public ElevationGridNode findElevationGridNode() {
		return (ElevationGridNode)findNodeByType(elevationGridTypeName);
	}

	public ExtrusionNode findExtrusionNode() {
		return (ExtrusionNode)findNodeByType(extrusionTypeName);
	}

	public FogNode findFogNode() {
		return (FogNode)findNodeByType(fogTypeName);
	}

	public FontStyleNode findFontStyleNode() {
		return (FontStyleNode)findNodeByType(fontStyleTypeName);
	}

	public GroupNode findGroupNode() {
		return (GroupNode)findNodeByType(groupTypeName);
	}

	public ImageTextureNode findImageTextureNode() {
		return (ImageTextureNode)findNodeByType(imageTextureTypeName);
	}

	public IndexedFaceSetNode findIndexedFaceSetNode() {
		return (IndexedFaceSetNode)findNodeByType(indexedFaceSetTypeName);
	}

	public IndexedLineSetNode findIndexedLineSetNode() {
		return (IndexedLineSetNode)findNodeByType(indexedLineSetTypeName);
	}

	public InlineNode findInlineNode() {
		return (InlineNode)findNodeByType(inlineTypeName);
	}

	public LODNode findLODNode() {
		return (LODNode)findNodeByType(lodTypeName);
	}

	public MaterialNode findMaterialNode() {
		return (MaterialNode)findNodeByType(materialTypeName);
	}

	public MovieTextureNode findMovieTextureNode() {
		return (MovieTextureNode)findNodeByType(movieTextureTypeName);
	}

	public NavigationInfoNode findNavigationInfoNode() {
		return (NavigationInfoNode)findNodeByType(navigationInfoTypeName);
	}

	public NormalNode findNormalNode() {
		return (NormalNode)findNodeByType(normalTypeName);
	}

	public NormalInterpolatorNode findNormalInterpolatorNode() {
		return (NormalInterpolatorNode)findNodeByType(normalInterpolatorTypeName);
	}

	public OrientationInterpolatorNode findOrientationInterpolatorNode() {
		return (OrientationInterpolatorNode)findNodeByType(orientationInterpolatorTypeName);
	}

	public PixelTextureNode findPixelTextureNode() {
		return (PixelTextureNode)findNodeByType(pixelTextureTypeName);
	}

	public PlaneSensorNode findPlaneSensorNode() {
		return (PlaneSensorNode)findNodeByType(planeSensorTypeName);
	}

	public PointLightNode findPointLightNode() {
		return (PointLightNode)findNodeByType(pointLightTypeName);
	}

	public PointSetNode findPointSetNode() {
		return (PointSetNode)findNodeByType(pointSetTypeName);
	}

	public PositionInterpolatorNode findPositionInterpolatorNode() {
		return (PositionInterpolatorNode)findNodeByType(positionInterpolatorTypeName);
	}

	public ProximitySensorNode findProximitySensorNode() {
		return (ProximitySensorNode)findNodeByType(proximitySensorTypeName);
	}

	public ScalarInterpolatorNode findScalarInterpolatorNode() {
		return (ScalarInterpolatorNode)findNodeByType(scalarInterpolatorTypeName);
	}

	public ScriptNode findScriptNode() {
		return (ScriptNode)findNodeByType(scriptTypeName);
	}

	public ShapeNode findShapeNode() {
		return (ShapeNode)findNodeByType(shapeTypeName);
	}

	public SoundNode findSoundNode() {
		return (SoundNode)findNodeByType(soundTypeName);
	}

	public SphereNode findSphereNode() {
		return (SphereNode)findNodeByType(sphereTypeName);
	}

	public SphereSensorNode findSphereSensorNode() {
		return (SphereSensorNode)findNodeByType(sphereSensorTypeName);
	}

	public SpotLightNode findSpotLightNode() {
		return (SpotLightNode)findNodeByType(spotLightTypeName);
	}

	public SwitchNode findSwitchNode() {
		return (SwitchNode)findNodeByType(switchTypeName);
	}

	public TextNode findTextNode() {
		return (TextNode)findNodeByType(textTypeName);
	}

	public TextureCoordinateNode findTextureCoordinateNode() {
		return (TextureCoordinateNode)findNodeByType(textureCoordinateTypeName);
	}

	public TextureTransformNode findTextureTransformNode() {
		return (TextureTransformNode)findNodeByType(textureTransformTypeName);
	}

	public TimeSensorNode findTimeSensorNode() {
		return (TimeSensorNode)findNodeByType(timeSensorTypeName);
	}

	public TouchSensorNode findTouchSensorNode() {
		return (TouchSensorNode)findNodeByType(touchSensorTypeName);
	}

	public TransformNode findTransformNode() {
		return (TransformNode)findNodeByType(transformTypeName);
	}

	public ViewpointNode findViewpointNode() {
		return (ViewpointNode)findNodeByType(viewpointTypeName);
	}

	public VisibilitySensorNode findVisibilitySensorNode() {
		return (VisibilitySensorNode)findNodeByType(visibilitySensorTypeName);
	}

	public WorldInfoNode findWorldInfoNode() {
		return (WorldInfoNode)findNodeByType(worldInfoTypeName);
	}

	////////////////////////////////////////////////
	//	find a node by name (Common)
	////////////////////////////////////////////////

	public GroupingNode findGroupingNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findGroupingNode(); node != null; node = node.nextTraversal()) {
			if (node.isGroupingNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (GroupingNode)node;
				}
			}
		}
		return null;
	}

	public LightNode findLightNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findLightNode(); node != null; node = node.nextTraversal()) {
			if (node.isLightNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (LightNode)node;
				}
			}
		}
		return null;
	}

	public GeometryNode findGeometryNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (Node node = findGeometryNode(); node != null; node = node.nextTraversal()) {
			if (node.isGeometryNode() == true) {
				String nodeName = node.getName();
				if (nodeName != null) {
					if (name.equals(nodeName) == true)
						return (GeometryNode)node;
				}
			}
		}
		return null;
	}
	
	////////////////////////////////////////////////
	//	find a node by name
	////////////////////////////////////////////////

	public AnchorNode findAnchorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (AnchorNode node = findAnchorNode(); node != null; node = (AnchorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public AppearanceNode findAppearanceNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (AppearanceNode node = findAppearanceNode(); node != null; node = (AppearanceNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public AudioClipNode findAudioClipNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (AudioClipNode node = findAudioClipNode(); node != null; node = (AudioClipNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public BackgroundNode findBackgroundNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (BackgroundNode node = findBackgroundNode(); node != null; node = (BackgroundNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public BillboardNode findBillboardNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (BillboardNode node = findBillboardNode(); node != null; node = (BillboardNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public BoxNode findBoxNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (BoxNode node = findBoxNode(); node != null; node = (BoxNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public CollisionNode findCollisionNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (CollisionNode node = findCollisionNode(); node != null; node = (CollisionNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ColorNode findColorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ColorNode node = findColorNode(); node != null; node = (ColorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ColorInterpolatorNode findColorInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ColorInterpolatorNode node = findColorInterpolatorNode(); node != null; node = (ColorInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ConeNode findConeNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ConeNode node = findConeNode(); node != null; node = (ConeNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public CoordinateNode findCoordinateNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (CoordinateNode node = findCoordinateNode(); node != null; node = (CoordinateNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public CoordinateInterpolatorNode findCoordinateInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (CoordinateInterpolatorNode node = findCoordinateInterpolatorNode(); node != null; node = (CoordinateInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public CylinderNode findCylinderNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (CylinderNode node = findCylinderNode(); node != null; node = (CylinderNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public CylinderSensorNode findCylinderSensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (CylinderSensorNode node = findCylinderSensorNode(); node != null; node = (CylinderSensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public DirectionalLightNode findDirectionalLightNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (DirectionalLightNode node = findDirectionalLightNode(); node != null; node = (DirectionalLightNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ElevationGridNode findElevationGridNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ElevationGridNode node = findElevationGridNode(); node != null; node = (ElevationGridNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ExtrusionNode findExtrusionNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ExtrusionNode node = findExtrusionNode(); node != null; node = (ExtrusionNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public FogNode findFogNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (FogNode node = findFogNode(); node != null; node = (FogNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public FontStyleNode findFontStyleNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (FontStyleNode node = findFontStyleNode(); node != null; node = (FontStyleNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public GroupNode findGroupNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (GroupNode node = findGroupNode(); node != null; node = (GroupNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ImageTextureNode findImageTextureNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ImageTextureNode node = findImageTextureNode(); node != null; node = (ImageTextureNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public IndexedFaceSetNode findIndexedFaceSetNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (IndexedFaceSetNode node = findIndexedFaceSetNode(); node != null; node = (IndexedFaceSetNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public IndexedLineSetNode findIndexedLineSetNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (IndexedLineSetNode node = findIndexedLineSetNode(); node != null; node = (IndexedLineSetNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public InlineNode findInlineNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (InlineNode node = findInlineNode(); node != null; node = (InlineNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public LODNode findLODNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (LODNode node = findLODNode(); node != null; node = (LODNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public MaterialNode findMaterialNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (MaterialNode node = findMaterialNode(); node != null; node = (MaterialNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public MovieTextureNode findMovieTextureNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (MovieTextureNode node = findMovieTextureNode(); node != null; node = (MovieTextureNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public NavigationInfoNode findNavigationInfoNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (NavigationInfoNode node = findNavigationInfoNode(); node != null; node = (NavigationInfoNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public NormalNode findNormalNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (NormalNode node = findNormalNode(); node != null; node = (NormalNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public NormalInterpolatorNode findNormalInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (NormalInterpolatorNode node = findNormalInterpolatorNode(); node != null; node = (NormalInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public OrientationInterpolatorNode findOrientationInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (OrientationInterpolatorNode node = findOrientationInterpolatorNode(); node != null; node = (OrientationInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public PixelTextureNode findPixelTextureNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (PixelTextureNode node = findPixelTextureNode(); node != null; node = (PixelTextureNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public PlaneSensorNode findPlaneSensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (PlaneSensorNode node = findPlaneSensorNode(); node != null; node = (PlaneSensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public PointLightNode findPointLightNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (PointLightNode node = findPointLightNode(); node != null; node = (PointLightNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public PointSetNode findPointSetNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (PointSetNode node = findPointSetNode(); node != null; node = (PointSetNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public PositionInterpolatorNode findPositionInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (PositionInterpolatorNode node = findPositionInterpolatorNode(); node != null; node = (PositionInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ProximitySensorNode findProximitySensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ProximitySensorNode node = findProximitySensorNode(); node != null; node = (ProximitySensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ScalarInterpolatorNode findScalarInterpolatorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ScalarInterpolatorNode node = findScalarInterpolatorNode(); node != null; node = (ScalarInterpolatorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ScriptNode findScriptNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ScriptNode node = findScriptNode(); node != null; node = (ScriptNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ShapeNode findShapeNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ShapeNode node = findShapeNode(); node != null; node = (ShapeNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public SoundNode findSoundNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (SoundNode node = findSoundNode(); node != null; node = (SoundNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public SphereNode findSphereNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (SphereNode node = findSphereNode(); node != null; node = (SphereNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public SphereSensorNode findSphereSensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (SphereSensorNode node = findSphereSensorNode(); node != null; node = (SphereSensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public SpotLightNode findSpotLightNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (SpotLightNode node = findSpotLightNode(); node != null; node = (SpotLightNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public SwitchNode findSwitchNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (SwitchNode node = findSwitchNode(); node != null; node = (SwitchNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TextNode findTextNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TextNode node = findTextNode(); node != null; node = (TextNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TextureCoordinateNode findTextureCoordinateNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TextureCoordinateNode node = findTextureCoordinateNode(); node != null; node = (TextureCoordinateNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TextureTransformNode findTextureTransformNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TextureTransformNode node = findTextureTransformNode(); node != null; node = (TextureTransformNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TimeSensorNode findTimeSensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TimeSensorNode node = findTimeSensorNode(); node != null; node = (TimeSensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TouchSensorNode findTouchSensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TouchSensorNode node = findTouchSensorNode(); node != null; node = (TouchSensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public TransformNode findTransformNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (TransformNode node = findTransformNode(); node != null; node = (TransformNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public ViewpointNode findViewpointNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (ViewpointNode node = findViewpointNode(); node != null; node = (ViewpointNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public VisibilitySensorNode findVisibilitySensorNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (VisibilitySensorNode node = findVisibilitySensorNode(); node != null; node = (VisibilitySensorNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}

	public WorldInfoNode findWorldInfoNode(String name) {
		if (name == null)
			return null;
		if (name.length() <= 0)
			return null;
		for (WorldInfoNode node = findWorldInfoNode(); node != null; node = (WorldInfoNode)node.nextTraversalSameType()) {
			String nodeName = node.getName();
			if (nodeName != null) {
				if (name.equals(nodeName) == true)
					return node;
			}
		}
		return null;
	}


	////////////////////////////////////////////////
	//	initialize
	////////////////////////////////////////////////

	public void initialize() {
	
		stop();

		Debug.message("Node::initialize nodes .....");
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal())
			node.initialize();
		
		Debug.message("Node::initialize nodes ..... done");
		
		setBackgroundNode(findBackgroundNode(), true);
		setFogNode(findFogNode(), true);
		setNavigationInfoNode(findNavigationInfoNode(), true);
		setViewpointNode(findViewpointNode(), true);
		
		updateBoundingBox();
		
		Debug.message("Node::initialize object .....");
		
		initializeObject();
		
		Debug.message("Node::initialize object ..... done");
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isRunnable() == true) {
				if (node.getRunnableType() == Node.RUNNABLE_TYPE_ALWAYS) 
					node.start();
			}
		}
		
		start();

	}

	public void uninitialize() {
		stop();

		for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
			if (node.isRunnable() == true) {
				if (node.getRunnableType() == Node.RUNNABLE_TYPE_ALWAYS) 
					node.stop();
			}
		}
		
		for (Node node = getNodes(); node != null; node = node.nextTraversal())
			node.uninitialize();
	}

	////////////////////////////////////////////////
	//	update
	////////////////////////////////////////////////

	public void update() {
		for (Node node = getNodes(); node != null; node = node.nextTraversal())
			node.update();
		updateObject();
	}

	public void updateRoute(Node eventOutNode, Field eventOutField) {
		//Debug.message("SceneGraph::updateRoute = " + eventOutNode + ", " + eventOutField);
		if (eventOutNode == null || eventOutField == null)
			return;
		for (Route route = getRoutes(); route != null; route = route.next()) {
			if (route.getEventOutNode() == eventOutNode && route.getEventOutField() == eventOutField) {
				Node eventInNode = route.getEventInNode();
				Field eventInField = route.getEventInField();
				if (eventInNode != null && eventInField != null) {
					//Debug.message("\t" + route);
					route.update();
					eventInNode.update();
					updateRoute(eventInNode, eventInField);
				}
			}
		}
	}

	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////

	private boolean mIsSimulationRunning = false;
	
	public void startSimulation() {
		if (isSimulationRunning() == false) {
			for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
				if (node.isRunnable() == true) {
					if (node.getRunnableType() != Node.RUNNABLE_TYPE_ALWAYS)
						node.start();
				}
			}
			setSimulationRunningFlag(true);
		}
	}

	public void stopSimulation() {
		if (isSimulationRunning() == true) {
			for (Node node = getNodes(); node != null; node = node.nextTraversal()) {
				if (node.isRunnable() == true) {
					if (node.getRunnableType() != Node.RUNNABLE_TYPE_ALWAYS)
						node.stop();
				}
			}
			setSimulationRunningFlag(false);
		}
	}
	
	public boolean isSimulationRunning() {
		return mIsSimulationRunning;
	}

	public void setSimulationRunningFlag(boolean flag) {
		mIsSimulationRunning = flag;
	}
			
	///////////////////////////////////////////////
	//	Load infomations
	///////////////////////////////////////////////

	private boolean mLoadingResult = false;
	
	private void setLoadingResult(boolean flag) {
		mLoadingResult = flag;
	}

	public boolean getLoadingResult() {
		return mLoadingResult;
	}
	
	public boolean isLoadingOK() {
		return getLoadingResult();
	}
	
	public void moveNodes(Parser parser) {
		Node node = parser.getNodes();
		while (node != null) {
			Node nextNode = node.next();
			moveNode(node);
			node = nextNode;
		}
	}

	public void moveRoutes(Parser parser) {
		Route route = parser.getRoutes();
		while (route != null) {
			Route nextRoute = route.next();
			route.remove();
			addRoute(route);
			route = nextRoute;
		}
	}

	public boolean add(File file) {
		stop();
		
		setDirectory(file.getParent());
		
		Debug.message("SceneGraph::add = Loading a file (" + file + ") ..... ");
		
		try {
			setLoadingResult(false);

			InputStream		inputStream	= null;
			Preprocessor	prepro		= null;
			
			boolean isPreprocessorOn = getOption(USE_PREPROCESSOR);
			Debug.message("\tPreprocessor = " + isPreprocessorOn);
			if (isPreprocessorOn == true) {
				prepro = new Preprocessor(file);
				inputStream = prepro.getInputStream();
			}
			else
				inputStream = new FileInputStream(file);

			Debug.message("\tInputStream = " + inputStream);
			
			Debug.message("\tStart parsering .....");
			
			Parser parser = new Parser(inputStream);
			parser.Input();
			
			Debug.message("\tmoving nodes .....");
			moveNodes(parser);
			
			Debug.message("\tmoving routes .....");
			moveRoutes(parser);
		
			Debug.message("\tInitializing .....");
			
			initialize();

			if (getOption(USE_PREPROCESSOR))
				prepro.delete();
			else
				inputStream.close();
		
			setLoadingResult(true);
			
			// Save Start Viewpoint Position / Orientation
			saveViewpointStartPositionAndOrientation();
		}
		catch (FileNotFoundException e) {
			Debug.warning("Loading Error (FileNotFoundException) = " + file);
		}
		catch (ParseException e) {
			Debug.warning("Loading Error (ParseException) = " + file);
			System.out.println(e.getMessage());
		}
		catch (TokenMgrError e) {
			Debug.warning("Loading Error (TokenMgrError) = " + file);
			System.out.println(e.getMessage());
		}
		catch (IOException e) {
			Debug.warning("Loading Error (IOException) = " + file);
			System.out.println(e.getMessage());
		} 
		catch (Exception e) {
			Debug.warning("Loading Error (Exception) = " + file);
			e.printStackTrace();
		} 
		finally {
			if (getLoadingResult() == true)
				Debug.message("\tLoading is OK !!");
			else
				Debug.message("\tLoading is failed !!");
			start();
			return isLoadingOK();
		}
	}
	
	public boolean load(File file) {
		clear();
		return add(file);
	}

	public boolean add(String filename) {
		File file = null;
		try {
			file = new File(filename);
		}
		catch (NullPointerException e) {
			UrlFile urlFile = new UrlFile(filename);
			if (urlFile.download() == true) {
				file = new File(urlFile.getDownloadedFilename());
				setURL(urlFile.getHost());
			}
			else
				return false;
		}
				
		return add(file);
	}

	public boolean load(String filename) {
		clear();
		return add(filename);
	}

	///////////////////////////////////////////////
	//	Save infomations
	///////////////////////////////////////////////
	
	public boolean save(OutputStream outputStream) {
	
		uninitialize();
		
		PrintWriter printStream = new PrintWriter(outputStream);

		printStream.println("#VRML V2.0 utf8");

		for (Node node = getNodes(); node != null; node = node.next()) {
			node.output(printStream, 0);
		}
			
		for (Route route = getRoutes(); route != null; route = route.next()) {
			route.output(printStream);
		}
		
		printStream.close();
		
		initialize();
		
		return true;
	}

	public boolean save(File file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + file + ")");
			return false;
		}
		
		return true;
	}
	
	public boolean save(String filename) {
		try {
			FileOutputStream outputStream = new FileOutputStream(filename);
			save(outputStream);
			outputStream.close();
		}
		catch (IOException e) {
			Debug.warning("Couldn't open the file (" + filename + ")");
			return false;
		}
		
		return true;
	}

	///////////////////////////////////////////////
	//	Output node infomations
	///////////////////////////////////////////////
	
	public void print() {
		try {
			OutputStream os = System.out;
			save(os);
			os.close();
		}
		catch (IOException e) {
			System.out.println("Couldn't output to System.out !!");
		}
	}

	///////////////////////////////////////////////
	//	Clear infomations
	///////////////////////////////////////////////
	
	public void clear() {
		// Delete all nodes
		clearNodes();
	}

	///////////////////////////////////////////////
	//	Bindable Nodes
	///////////////////////////////////////////////
	
	private Vector	mBackgroundVector		= new Vector();
	private Vector	mFogVector				= new Vector();
	private Vector	mNavigationInfoVector	= new Vector();
	private Vector	mViewpointVector		= new Vector();	

	private BackgroundNode		mDefaultBackgroundNode		= new BackgroundNode();
	private FogNode				mDefaultFogNode				= new FogNode();
	private NavigationInfoNode	mDefaultNavigationInfoNode	= new NavigationInfoNode();
	private ViewpointNode		mDefaultViewpointNode		= new ViewpointNode();

	public BindableNode bindableGetTopNode(Vector vector) {
		BindableNode topNode;

		try {
			topNode = (BindableNode)vector.lastElement();
		} catch(NoSuchElementException e) { 
			topNode = null;
		}

		return topNode;
	}

	public void setBindableNode(Vector nodeVector, BindableNode node, boolean bind) {
		if (node == null)
			return;

		BindableNode topNode = bindableGetTopNode(nodeVector);

		if (bind) {
			if (topNode != node) {
				if (topNode != null) {
					topNode.setIsBound(false);
					topNode.sendEvent(topNode.getIsBoundField());
				}
	
				nodeVector.removeElement(node);
				nodeVector.addElement(node);
	
				node.setIsBound(true);
				node.sendEvent(node.getIsBoundField());
			}
		}
		else {
			if (topNode == node) {
				node.setIsBound(false);
				node.sendEvent(node.getIsBoundField());
	
				nodeVector.removeElement(node);
	
				BindableNode newTopNode = bindableGetTopNode(nodeVector);

				if (newTopNode != null) {
					newTopNode.setIsBound(true);
					newTopNode.sendEvent(newTopNode.getIsBoundField());
				}
			}
			else {
				nodeVector.removeElement(node);
			}
		}
	}

	public void setBindableNode(BindableNode node, boolean bind) {
		if (node.isBackgroundNode())		setBackgroundNode((BackgroundNode)node, bind);
		if (node.isFogNode())				setFogNode((FogNode)node, bind);
		if (node.isNavigationInfoNode())	setNavigationInfoNode((NavigationInfoNode)node, bind);
		if (node.isViewpointNode())			setViewpointNode((ViewpointNode)node, bind);
	}

	public void setBackgroundNode(BackgroundNode bg, boolean bind) {
		setBindableNode(mBackgroundVector, bg, bind);
	}

	public void setFogNode(FogNode fog, boolean bind) {
		setBindableNode(mFogVector, fog, bind);
	}

	public void setNavigationInfoNode(NavigationInfoNode navInfo, boolean bind) {
		setBindableNode(mNavigationInfoVector, navInfo, bind);
	}

	public void setViewpointNode(ViewpointNode view, boolean bind) {
		setBindableNode(mViewpointVector, view, bind);
	}

	public BackgroundNode getBackgroundNode() {
		return (BackgroundNode)bindableGetTopNode(mBackgroundVector);
	}

	public FogNode getFogNode() {
		return (FogNode)bindableGetTopNode(mFogVector);
	}

	public NavigationInfoNode getNavigationInfoNode() {
		return (NavigationInfoNode)bindableGetTopNode(mNavigationInfoVector);
	}

	public ViewpointNode getViewpointNode() {
		return (ViewpointNode)bindableGetTopNode(mViewpointVector);
	}

	public BackgroundNode getDefaultBackgroundNode() {
		return mDefaultBackgroundNode;
	}

	public FogNode getDefaultFogNode() {
		return mDefaultFogNode;
	}

	public NavigationInfoNode getDefaultNavigationInfoNode() {
		return mDefaultNavigationInfoNode;
	}

	public ViewpointNode getDefaultViewpointNode() {
		return mDefaultViewpointNode;
	}

	///////////////////////////////////////////////
	//	Delete/Remove Node
	///////////////////////////////////////////////

	public void removeNode(Node node) {
		removeRoutes(node);
		node.remove();
	}

	////////////////////////////////////////////////
	//	SceneGraph Object
	////////////////////////////////////////////////
	
	private SceneGraphObject	mObject = null;
	
	public void setObject(SceneGraphObject object) {
		mObject = object;
	}
	
	public SceneGraphObject getObject() {
		return mObject;
	}
	
	public boolean hasObject() {
		return (mObject != null ? true : false);
	}
	
	public boolean initializeObject() {
		if (hasObject())
			return mObject.initialize(this);
		return false;
	}

	public boolean uninitializeObject() {
		if (hasObject())
			return mObject.uninitialize(this);
		return false;
	}

	public boolean addNodeObject(Node node) {
		if (hasObject())
			return mObject.addNode(this, node);
		return false;
	}

	public boolean removeNodeObject(Node node) {
		if (hasObject())
			return mObject.removeNode(this, node);
		return false;
	}

	public boolean updateObject() {
		if (hasObject())
			return mObject.update(this);
		return false;
	}
	
	public boolean removeObject() {
		if (hasObject())
			return mObject.remove(this);
		return false;
	}

	public NodeObject	createNodeObject(Node node) {
		if (hasObject() == false)
			return null;
		return getObject().createNodeObject(this, node);
	}

	////////////////////////////////////////////////
	//	Option
	////////////////////////////////////////////////
	
	private int mOption	= 0;
	
	public void setOption(int option) {
		mOption = option;
	}
	
	public int getOption() {
		return mOption;
	}
	
	public boolean getOption(int option) {
		if ((mOption & option) == option)
			return true;
		return false;
	}

	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	private float mBoundingBoxCenter[]	= new float[3];
	private float mBoundingBoxSize[]	= new float[3];

	public void setBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxCenter[n] = center[n];
	}

	public void setBoundingBoxCenter(float x, float y, float z) {
		mBoundingBoxCenter[0] = x;
		mBoundingBoxCenter[1] = y;
		mBoundingBoxCenter[2] = z;
	}

	public void getBoundingBoxCenter(float center[]) {
		for (int n=0; n<3; n++)
			center[n] = mBoundingBoxCenter[n];
	}

	public float[] getBoundingBoxCenter() {
		float center[] = new float[3];
		getBoundingBoxCenter(center);
		return center;
	}

	public void setBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			mBoundingBoxSize[n] = size[n];
	}
		
	public void setBoundingBoxSize(float x, float y, float z) {
		mBoundingBoxSize[0] = x;
		mBoundingBoxSize[1] = y;
		mBoundingBoxSize[2] = z;
	}

	public void getBoundingBoxSize(float size[]) {
		for (int n=0; n<3; n++)
			size[n] = mBoundingBoxSize[n];
	}

	public float[] getBoundingBoxSize() {
		float size[] = new float[3];
		getBoundingBoxSize(size);
		return size;
	}

	public void updateBoundingBox(Node node, BoundingBox bbox) {
		//if (node.isGroupingNode() == true) {
		if (node.isGeometryNode() == true) {
			//GroupingNode gnode = (GroupingNode)node;
			GeometryNode gnode = (GeometryNode)node;

			float bboxCenter[]	= new float[3];
			float bboxSize[]	= new float[3];
			float point[]		= new float[3];

			gnode.getBoundingBoxCenter(bboxCenter);
			gnode.getBoundingBoxSize(bboxSize);
			
			if (bboxSize[0] >= 0.0f && bboxSize[1] >= 0.0f && bboxSize[2] >= 0.0f) {
				SFMatrix nodemx = node.getTransformMatrix();
				for (int n=0; n<8; n++) {
					point[0] = (n < 4)				? bboxCenter[0] - bboxSize[0] : bboxCenter[0] + bboxSize[0];
					point[1] = ((n % 2) != 0)	? bboxCenter[1] - bboxSize[1] : bboxCenter[1] + bboxSize[1];
					point[2] = ((n % 4) < 2)		? bboxCenter[2] - bboxSize[2] : bboxCenter[2] + bboxSize[2];
					nodemx.multi(point);
					bbox.addPoint(point);
				}
			}
		}
		
		for (Node cnode=node.getChildNodes(); cnode != null; cnode=cnode.next()) 
			updateBoundingBox(cnode, bbox);
	}
	
	public void updateBoundingBox() {
		BoundingBox bbox = new BoundingBox();
		for (Node node=getNodes(); node != null; node=node.next())
			updateBoundingBox(node, bbox);
		setBoundingBoxCenter(bbox.getCenter());
		setBoundingBoxSize(bbox.getSize());
	}
				
	////////////////////////////////////////////////
	//	BoundingBox
	////////////////////////////////////////////////
	
	public float getRadius() {
		SFVec3f bboxSize = new SFVec3f(getBoundingBoxSize());
		return bboxSize.getScalar(); 
	}
	
	////////////////////////////////////////////////
	//	Thread
	////////////////////////////////////////////////
	
	private Thread mThreadObject = null;
	
	public void setThreadObject(Thread obj) {
		mThreadObject = obj;
	}

	public Thread getThreadObject() {
		return mThreadObject;
	}

	public void run() {
		while (true) {
			updateObject();
			Thread threadObject = getThreadObject();
			if (threadObject != null) { 
//				threadObject.yield();
				try {
					threadObject.sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	public void start() {
		Thread threadObject = getThreadObject();
		if (threadObject == null) {
			threadObject = new Thread(this);
			setThreadObject(threadObject);
			threadObject.start();
		}
	}
	
	public void stop() {
		Thread threadObject = getThreadObject();
		if (threadObject != null) { 
			//threadObject.destroy();
			threadObject.stop();
			setThreadObject(null);
		}
	}

	////////////////////////////////////////////////
	//	Directory/URL
	////////////////////////////////////////////////
	
	private String mCurrentDirectory = null;
	private String mCurrentURL = null;

	public void setDirectory(String dir) {
		mCurrentDirectory = dir;
	}

	public String getDirectory() {
		return mCurrentDirectory;
	}

	public void setURL(String url) {
		mCurrentURL = url;
	}

	public String getURL() {
		return mCurrentURL;
	}

	////////////////////////////////////////////////
	//	Rendering mode
	////////////////////////////////////////////////
	
	private int mRenderingMode = RENDERINGMODE_FILL;
	
	public boolean setRenderingMode(int mode) {
		mRenderingMode = mode;
		if (hasObject() == false)
			return false;
		return mObject.setRenderingMode(this, mode);
	}

	public int getRenderingMode() {
		return mRenderingMode;
	}

	////////////////////////////////////////////////
	//	Headlight
	////////////////////////////////////////////////

	public void setHeadlightState(boolean state) {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		navInfo.setHeadlight(state);		
	}

	public boolean isHeadlightOn() {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		return navInfo.getHeadlight();		
	}

	////////////////////////////////////////////////
	//	Navigation Speed
	////////////////////////////////////////////////

	public void setNavigationSpeed(float speed) {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		navInfo.setSpeed(speed);		
	}

	public float getNavigationSpeed() {
		NavigationInfoNode navInfo = getNavigationInfoNode();
		if (navInfo == null) 
			navInfo = getDefaultNavigationInfoNode();
		return navInfo.getSpeed();		
	}

	////////////////////////////////////////////////
	//	TouchSensor 
	////////////////////////////////////////////////

	public double getCurrentTime() {
		Date	date = new Date();
		return (double)date.getTime() / 1000.0;
	}
	
	public void shapePressed(ShapeNode shapeNode, int mx, int my) {
		for (Node node=getNodes(); node != null; node=node.nextTraversal()) {
			if (node.isTouchSensorNode() == true) {
				Debug.message(node + " is active !!");
				TouchSensorNode touchSensor = (TouchSensorNode)node;
				Node parentNode = touchSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					ConstSFBool isActive = touchSensor.getIsActiveField();
					isActive.setValue(true);
					touchSensor.sendEvent(isActive);
						
					ConstSFTime touchTime = touchSensor.getTouchTimeField();
					touchTime.setValue(getCurrentTime());
					touchSensor.sendEvent(touchTime);
				}
			} 
			else if (node.isPlaneSensorNode() == true) {
				PlaneSensorNode planeSensor = (PlaneSensorNode)node;
				Node parentNode = planeSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					Debug.message(node + " is active !!");
					ConstSFBool isActive = planeSensor.getIsActiveField();
					isActive.setValue(true);
					planeSensor.sendEvent(isActive);
				}
			}
		}
	}
	
	public void shapeReleased(ShapeNode shapeNode, int mx, int my) {
		for (Node node=getNodes(); node != null; node=node.nextTraversal()) {
			if (node.isTouchSensorNode() == true) {
				Debug.message(node + " is inactive !!");
				TouchSensorNode touchSensor = (TouchSensorNode)node;
				Node parentNode = touchSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					ConstSFBool isActive = touchSensor.getIsActiveField();
					isActive.setValue(false);
					touchSensor.sendEvent(isActive);
				}
			} 
			else if (node.isPlaneSensorNode() == true) {
				PlaneSensorNode planeSensor = (PlaneSensorNode)node;
				Node parentNode = planeSensor.getParentNode();
				if (shapeNode.isAncestorNode(parentNode) == true) {
					Debug.message(node + " is inactive !!");
					ConstSFBool isActive = planeSensor.getIsActiveField();
					isActive.setValue(false);
					planeSensor.sendEvent(isActive);
				}
			}
		}
	}
	
	public void shapeDragged(ShapeNode shapeNode, int mx, int my) {
	/*
			float world[3];
			int mx = point.x - getStartMousePositionX();
			int my = point.y - getStartMousePositionY();
			getWorldPosition(mx, my, shapeNode, world);
			PlaneSensorNode planeSensor = getPlaneSensorNode();
			planeSensor.setTranslationChanged(world[0], world[1], world[2]);
			planeSensor.sendEvent(planeSensor.getTranslationChangedField());
	*/
	}

	////////////////////////////////////////////////
	// Default Viewpoint Position/ Orientation 
	////////////////////////////////////////////////

	private float mStartViewPosition[]		= new float[3];
	private float mStartViewOrientation[]	= new float[4];

	public void setViewpointStartPosition(float pos[]) {
		mStartViewPosition[0] = pos[0];
		mStartViewPosition[1] = pos[1];
		mStartViewPosition[2] = pos[2];
	}

	public void setViewpointStartPosition(float x, float y, float z) {
		mStartViewPosition[0] = x;
		mStartViewPosition[1] = y;
		mStartViewPosition[2] = z;
	}

	public void getViewpointStartPosition(float pos[]) {
		pos[0] = mStartViewPosition[0];
		pos[1] = mStartViewPosition[1];
		pos[2] = mStartViewPosition[2];
	}

	public void setViewpointStartOrientation(float x, float y, float z, float angle) {
		mStartViewOrientation[0] = x;
		mStartViewOrientation[1] = y;
		mStartViewOrientation[2] = z;
		mStartViewOrientation[3] = angle;	
	}

	public void setViewpointStartOrientation(float ori[]) {
		mStartViewOrientation[0] = ori[0];
		mStartViewOrientation[1] = ori[1];
		mStartViewOrientation[2] = ori[2];
		mStartViewOrientation[3] = ori[3];
	}

	public void getViewpointStartOrientation(float ori[]) {
		ori[0] = mStartViewOrientation[0];
		ori[1] = mStartViewOrientation[1];
		ori[2] = mStartViewOrientation[2];
		ori[3] = mStartViewOrientation[3];
	}

	public void saveViewpointStartPositionAndOrientation() {
		ViewpointNode view = getViewpointNode();
		Debug.message("Start Viewpoint = " + view);
		if (view != null) {
			float viewPos[] = new float[3];
			float viewOri[] = new float[4];
			view.getPosition(viewPos);
			view.getOrientation(viewOri);
			Debug.message("\tPosition    = " + viewPos[0] + ", " + viewPos[1] + ", " + viewPos[2]);
			Debug.message("\tOrientation = " + viewOri[0] + ", " + viewOri[1] + ", " + viewOri[2] + ", " + viewOri[3]);
			setViewpointStartPosition(viewPos);
			setViewpointStartOrientation(viewOri);
		}
	}
			
	////////////////////////////////////////////////
	// Reset Viewpoint
	////////////////////////////////////////////////
	
	private final static int XZ_PLANE	= 0;
	private final static int XY_PLANE	= 1;
	private final static int YZ_PLANE	= 2;
	
	private void resetViewpointAlongXAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[1] / (float)Math.tan(fov);
		float offset2 = bboxSize[2] / (float)Math.tan(fov);
		float offset = Math.max(offset1, offset2);
		view.setPosition(bboxCenter[0] + offset*3.0f, bboxCenter[1], bboxCenter[2]);
		view.setOrientation(0.0f, 1.0f, 0.0f, (float)(Math.PI/2.0));
	}

	private void resetViewpointAlongYAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[0] / (float)Math.tan(fov);
		float offset2 = bboxSize[2] / (float)Math.tan(fov);
		float offset = Math.max(offset1, offset2);
		view.setPosition(bboxCenter[0], bboxCenter[1] + offset*3.0f, bboxCenter[2]);
		view.setOrientation(1.0f, 0.0f, 0.0f, -(float)(Math.PI/2.0));
	}

	private void resetViewpointAlongZAxis(float bboxCenter[], float bboxSize[], ViewpointNode view, float fov) {
		float offset1 = bboxSize[0] / (float)Math.tan(fov);
		float offset2 = bboxSize[1] / (float)Math.tan(fov);
		float offset = Math.max(offset1, offset2);
		view.setPosition(bboxCenter[0], bboxCenter[1], bboxCenter[2] + offset*3.0f);
		view.setOrientation(0.0f, 0.0f, 1.0f, 0.0f);
	}

	public void resetViewpoint() {
		float viewPos[] = new float[3];
		float viewOri[] = new float[4];
									
		getViewpointStartPosition(viewPos);
		getViewpointStartOrientation(viewOri);

		ViewpointNode view = getViewpointNode();
		if (view == null)
			view = getDefaultViewpointNode();
										
		Debug.message("Reset Viewpoint = " + view);
		Debug.message("\tPosition    = " + viewPos[0] + ", " + viewPos[1] + ", " + viewPos[2]);
		Debug.message("\tOrientation = " + viewOri[0] + ", " + viewOri[1] + ", " + viewOri[2] + ", " + viewOri[3]);
									
		view.setPosition(viewPos);
		view.setOrientation(viewOri);
	}
	
	public void resetViewpoint(int plane) {
		ViewpointNode view = getViewpointNode();
		if (view == null)
			view = getDefaultViewpointNode();

		updateBoundingBox();
			
		float bboxCenter[]	= getBoundingBoxCenter();
		float bboxSize[]		= getBoundingBoxSize();
			
		float fov = view.getFieldOfView();
		
		Debug.message("Reset Viewpoint bboxCenter = " + bboxCenter[0] + ", " + bboxCenter[1] + ", " + bboxCenter[2]);
		Debug.message("                bboxSize   = " + bboxSize[0] + ", " + bboxSize[1] + ", " + bboxSize[2]);
		Debug.message("                fov        = " + fov);

		switch (plane) {
		case XY_PLANE:	resetViewpointAlongZAxis(bboxCenter, bboxSize, view, fov);	break;
		case XZ_PLANE:	resetViewpointAlongYAxis(bboxCenter, bboxSize, view, fov);	break;
		case YZ_PLANE:	resetViewpointAlongXAxis(bboxCenter, bboxSize, view, fov);	break;
		}			
	}

	public void resetViewpointAlongXAxis() {
		resetViewpoint(YZ_PLANE);
	}

	public void resetViewpointAlongYAxis() {
		resetViewpoint(XZ_PLANE);
	}

	public void resetViewpointAlongZAxis() {
		resetViewpoint(XY_PLANE);
	}
}