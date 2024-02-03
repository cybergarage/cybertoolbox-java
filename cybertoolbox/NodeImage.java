/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	NodeImage.java
*
******************************************************************/

import java.awt.*;

import javax.swing.*;
import javax.swing.tree.*;

import vrml.*;
import vrml.node.*;
import vrml.util.Debug;

public class NodeImage implements WorldConstants {

	static private		ImageIcon			mWorldImage;
	static private		ImageIcon			mCommonImage;
	static private		ImageIcon			mNodeImage[]	= new ImageIcon[54];

	static {
		try {
			String dir = WORLD_IMAGE_NODE_DIRECTORY + System.getProperty("file.separator");
			mWorldImage = new ImageIcon(dir + "world.gif");
			mCommonImage = new ImageIcon(dir + "common.gif");
			mNodeImage[0] = new ImageIcon(dir + "anchor.gif");
			mNodeImage[1] = new ImageIcon(dir + "appearance.gif");
			mNodeImage[2] = new ImageIcon(dir + "audioclip.gif");
			mNodeImage[3] = new ImageIcon(dir + "background.gif");
			mNodeImage[4] = new ImageIcon(dir + "billboard.gif");
			mNodeImage[5] = new ImageIcon(dir + "box.gif");
			mNodeImage[6] = new ImageIcon(dir + "collision.gif");
			mNodeImage[7] = new ImageIcon(dir + "color.gif");
			mNodeImage[8] = new ImageIcon(dir + "colorinterp.gif");
			mNodeImage[9] = new ImageIcon(dir + "cone.gif");
			mNodeImage[10] = new ImageIcon(dir + "coordinate.gif");
			mNodeImage[11] = new ImageIcon(dir + "coordinateinterp.gif");
			mNodeImage[12] = new ImageIcon(dir + "cylinder.gif");
			mNodeImage[13] = new ImageIcon(dir + "cylindersensor.gif");
			mNodeImage[14] = new ImageIcon(dir + "directionallight.gif");
			mNodeImage[15] = new ImageIcon(dir + "elevationgrid.gif");
			mNodeImage[16] = new ImageIcon(dir + "extrusion.gif");
			mNodeImage[17] = new ImageIcon(dir + "fog.gif");
			mNodeImage[18] = new ImageIcon(dir + "fontstyle.gif");
			mNodeImage[19] = new ImageIcon(dir + "group.gif");
			mNodeImage[20] = new ImageIcon(dir + "idxfaceset.gif");
			mNodeImage[21] = new ImageIcon(dir + "idxlineset.gif");
			mNodeImage[22] = new ImageIcon(dir + "imgtexture.gif");
			mNodeImage[23] = new ImageIcon(dir + "inline.gif");
			mNodeImage[24] = new ImageIcon(dir + "lod.gif");
			mNodeImage[25] = new ImageIcon(dir + "material.gif");
			mNodeImage[26] = new ImageIcon(dir + "movtexture.gif");
			mNodeImage[27] = new ImageIcon(dir + "navigationinfo.gif");
			mNodeImage[28] = new ImageIcon(dir + "normal.gif");
			mNodeImage[29] = new ImageIcon(dir + "normalinterp.gif");
			mNodeImage[30] = new ImageIcon(dir + "orientationinterp.gif");
			mNodeImage[31] = new ImageIcon(dir + "pixeltexture.gif");
			mNodeImage[32] = new ImageIcon(dir + "planesensor.gif");
			mNodeImage[33] = new ImageIcon(dir + "pointlight.gif");
			mNodeImage[34] = new ImageIcon(dir + "pointset.gif");
			mNodeImage[35] = new ImageIcon(dir + "positioninterp.gif");
			mNodeImage[36] = new ImageIcon(dir + "proximitysensor.gif");
			mNodeImage[37] = new ImageIcon(dir + "scalarinterp.gif");
			mNodeImage[38] = new ImageIcon(dir + "script.gif");
			mNodeImage[39] = new ImageIcon(dir + "shape.gif");
			mNodeImage[40] = new ImageIcon(dir + "sound.gif");
			mNodeImage[41] = new ImageIcon(dir + "sphere.gif");
			mNodeImage[42] = new ImageIcon(dir + "spheresensor.gif");
			mNodeImage[43] = new ImageIcon(dir + "spotlight.gif");
			mNodeImage[44] = new ImageIcon(dir + "switch.gif");
			mNodeImage[45] = new ImageIcon(dir + "texcoord.gif");
			mNodeImage[46] = new ImageIcon(dir + "text.gif");
			mNodeImage[47] = new ImageIcon(dir + "textransform.gif");
			mNodeImage[48] = new ImageIcon(dir + "timesensor.gif");
			mNodeImage[49] = new ImageIcon(dir + "touchsensor.gif");
			mNodeImage[50] = new ImageIcon(dir + "transform.gif");
			mNodeImage[51] = new ImageIcon(dir + "viewpoint.gif");
			mNodeImage[52] = new ImageIcon(dir + "visibilitysensor.gif");
			mNodeImage[53] = new ImageIcon(dir + "worldinfo.gif");
		} catch (Exception e) {
			Debug.warning("Couldn't load images: " + e);
		}
	}

	static public ImageIcon getImageIcon(Node node) {
	
		if (node == null)
			return mCommonImage;
			
		if (node.isAnchorNode())
			return mNodeImage[0];
		else if (node.isAppearanceNode()) 
			return mNodeImage[1];
		else if (node.isAudioClipNode())
			return mNodeImage[2];
		else if (node.isBackgroundNode())
			return mNodeImage[3];
		else if (node.isBillboardNode())
			return mNodeImage[4];
		else if (node.isBoxNode())
			return mNodeImage[5];
		else if (node.isCollisionNode())
			return mNodeImage[6];
		else if (node.isColorNode())
			return mNodeImage[7];
		else if (node.isColorInterpolatorNode())
			return mNodeImage[8];
		else if (node.isConeNode())
			return mNodeImage[9];
		else if (node.isCoordinateNode())
			return mNodeImage[10];
		else if (node.isCoordinateInterpolatorNode())
			return mNodeImage[11];
		else if (node.isCylinderNode())
			return mNodeImage[12];
		else if (node.isCylinderSensorNode())
			return mNodeImage[13];
		else if (node.isDirectionalLightNode())
			return mNodeImage[14];
		else if (node.isElevationGridNode())
			return mNodeImage[15];
		else if (node.isExtrusionNode())
			return mNodeImage[16];
		else if (node.isFogNode())
			return mNodeImage[17];
		else if (node.isFontStyleNode())
			return mNodeImage[18];
		else if (node.isGroupNode())
			return mNodeImage[19];
		else if (node.isImageTextureNode())
			return mNodeImage[20];
		else if (node.isIndexedFaceSetNode())
			return mNodeImage[21];
		else if (node.isIndexedLineSetNode()) 
			return mNodeImage[22];
		else if (node.isInlineNode()) 
			return mNodeImage[23];
		else if (node.isLODNode())
			return mNodeImage[24];
		else if (node.isMaterialNode())
			return mNodeImage[25];
		else if (node.isMovieTextureNode())
			return mNodeImage[26];
		else if (node.isNavigationInfoNode())
			return mNodeImage[27];
		else if (node.isNormalNode())
			return mNodeImage[28];
		else if (node.isNormalInterpolatorNode())
			return mNodeImage[29];
		else if (node.isOrientationInterpolatorNode())
			return mNodeImage[30];
		else if (node.isPixelTextureNode())
			return mNodeImage[31];
		else if (node.isPlaneSensorNode())
			return mNodeImage[32];
		else if (node.isPointLightNode())
			return mNodeImage[33];
		else if (node.isPointSetNode())
			return mNodeImage[34];
		else if (node.isPositionInterpolatorNode())
			return mNodeImage[35];
		else if (node.isProximitySensorNode())
			return mNodeImage[36];
		else if (node.isScalarInterpolatorNode())
			return mNodeImage[37];
		else if (node.isScriptNode())
			return mNodeImage[38];
		else if (node.isShapeNode())
			return mNodeImage[39];
		else if (node.isSoundNode())
			return mNodeImage[40];
		else if (node.isSphereNode())
			return mNodeImage[41];
		else if (node.isSphereSensorNode())
			return mNodeImage[42];
		else if (node.isSpotLightNode())
			return mNodeImage[43];
		else if (node.isSwitchNode())
			return mNodeImage[44];
		else if (node.isTextNode())
			return mNodeImage[45];
		else if (node.isTextureCoordinateNode())
			return mNodeImage[46];
		else if (node.isTextureTransformNode())
			return mNodeImage[47];
		else if (node.isTimeSensorNode())
			return mNodeImage[48];
		else if (node.isTouchSensorNode())
			return mNodeImage[49];
		else if (node.isTransformNode())
			return mNodeImage[50];
		else if (node.isViewpointNode())
			return mNodeImage[51];
		else if (node.isVisibilitySensorNode())
			return mNodeImage[52];
		else if (node.isWorldInfoNode())
			return mNodeImage[53];

		return null;
	}
}
