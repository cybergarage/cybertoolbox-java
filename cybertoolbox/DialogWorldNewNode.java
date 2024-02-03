/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	DialogWorldNewNode.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import vrml.*;
import vrml.node.*;

public class DialogWorldNewNode extends Dialog {

	static private		Font				mDefaultFont;
	static private		Color				mSelectedBackgroundColor = Color.yellow;

	static {
		try {
			mDefaultFont = new Font("SansSerif", Font.BOLD, 12);
		} catch (Exception e) {}
	}
	
	private NodeListComponent mNodeListComponent = null;
		
	public DialogWorldNewNode(Frame parentFrame, Node parentNode) {
		super(parentFrame, "New Node");
		mNodeListComponent = new NodeListComponent(parentNode);
		mNodeListComponent.setSize(new Dimension(300, 400));
		JScrollPane nodeListScrollPane = new JScrollPane(mNodeListComponent);
		JComponent dialogComponent[] = new JComponent[1];
		dialogComponent[0] = nodeListScrollPane;
		setComponents(dialogComponent);
	}
			
	public Node getSelectedNode() {
		return mNodeListComponent.getSelectedNode();
	}

	private class NodeListComponent extends JList {
		private NodeListModel mNodeListModel = null;
		public NodeListComponent(Node parentNode) {
			mNodeListModel = new NodeListModel(parentNode);
			setModel(mNodeListModel);
			setCellRenderer(new NodeCellRenderer());			
			setBorder(new LineBorder(Color.black));
		}
			
		public Node getSelectedNode() {
			return mNodeListModel.getNode(getLeadSelectionIndex());
		}
	}

	private class NodeListModel extends DefaultListModel {
		
		public NodeListModel(Node parentNode) {
			createNodeList(parentNode);
			for (Node node=getNodes(); node!=null; node=node.next()) 
				addElement(node);
		}
			
		private NodeList	mNodeList = null;
		private Node		mParentNode = null;
		
		public Node getNodes() {
			return (Node)mNodeList.getNodes();		
		}

		public Node getNode(int n) {
			return (Node)mNodeList.getNode(n);		
		}
			
		private boolean addNode(Node node) {
			if (mParentNode.isChildNodeType(node)) {
				mNodeList.addNode(node);	
				return true;
			}
			return false;
		}
		
		private void createNodeList(Node parentNode) {
			mNodeList = new NodeList();
			mParentNode = parentNode;
		
			addNode(new AnchorNode());
			addNode(new AppearanceNode());
			addNode(new AudioClipNode());
			addNode(new BackgroundNode());
			addNode(new BillboardNode());
			addNode(new BoxNode());
			addNode(new CollisionNode());
			addNode(new ColorNode());
			addNode(new ColorInterpolatorNode());
			addNode(new ConeNode());
			addNode(new CoordinateNode());
			addNode(new CoordinateInterpolatorNode());
			addNode(new CylinderNode());
			addNode(new CylinderSensorNode());
			addNode(new DirectionalLightNode());
			addNode(new ElevationGridNode());
			addNode(new ExtrusionNode());
			addNode(new FogNode());
			addNode(new FontStyleNode());
			addNode(new GroupNode());
			addNode(new ImageTextureNode());
			addNode(new IndexedFaceSetNode());
			addNode(new IndexedLineSetNode());
			addNode(new InlineNode());
			addNode(new LODNode());
			addNode(new MaterialNode());
			addNode(new MovieTextureNode());
			addNode(new NavigationInfoNode());
			addNode(new NormalNode());
			addNode(new NormalInterpolatorNode());
			addNode(new OrientationInterpolatorNode());
			addNode(new PixelTextureNode());
			addNode(new PlaneSensorNode());
			addNode(new PointLightNode());
			addNode(new PointSetNode());
			addNode(new PositionInterpolatorNode());
			addNode(new ProximitySensorNode());
			addNode(new ScalarInterpolatorNode());
			addNode(new ScriptNode());
			addNode(new ShapeNode());
			addNode(new SoundNode());
			addNode(new SphereNode());
			addNode(new SphereSensorNode());
			addNode(new SpotLightNode());
			addNode(new SwitchNode());
			addNode(new TextNode());
			addNode(new TextureCoordinateNode());
			addNode(new TextureTransformNode());
			addNode(new TimeSensorNode());
			addNode(new TouchSensorNode());
			addNode(new TransformNode());
			addNode(new ViewpointNode());
			addNode(new VisibilitySensorNode());
			addNode(new WorldInfoNode());
		}
	}

	private class NodeCellRenderer extends DefaultListCellRenderer {
		NodeCellRenderer() {
		}

		public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus) {
			Node		node				= (Node)value;
			String	nodeTypeName	= node.getType();	
				
			setIcon(NodeImage.getImageIcon(node));

			return super.getListCellRendererComponent(list, nodeTypeName, modelIndex, isSelected, cellHasFocus);
		}
	}
}
		
