/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : Constants.java
*
******************************************************************/

package vrml;

public interface Constants {

  static public final int X = 0;
  static public final int Y = 1;
  static public final int Z = 2;
  static public final int W = 3;
  static public final int R = 0;
  static public final int G = 1;
  static public final int B = 2;

  static public final int RENDERINGMODE_LINE	= 0;
  static public final int RENDERINGMODE_FILL	= 1;
  
  static public final String rootTypeName							= "Root";
  static public final String appearanceTypeName					= "Appearance";
  static public final String viewpointTypeName					= "Viewpoint";
  static public final String transformTypeName					= "Transform";
  static public final String textureCoordinateTypeName			= "TextureCoordinate";
  static public final String spotLightTypeName					= "SpotLight";
  static public final String shapeTypeName						= "Shape";
  static public final String pointLightTypeName					= "PointLight";
  static public final String normalTypeName						= "Normal";
  static public final String materialTypeName					= "Material";
  static public final String indexedFaceSetTypeName				= "IndexedFaceSet";
  static public final String groupTypeName						= "Group";
  static public final String billboardTypeName					= "Billboard";
  static public final String collisionTypeName					= "Collision";
  static public final String lodTypeName							= "LOD";
  static public final String switchTypeName						= "Switch";
  static public final String inlineTypeName						= "Inline";
  static public final String directionalLightTypeName			= "DirectionalLight";
  static public final String coordinateTypeName					= "Coordinate";
  static public final String colorTypeName						= "Color";
  static public final String worldInfoTypeName					= "WorldInfo";
  static public final String timeSensorTypeName					= "TimeSensor";
  static public final String scalarInterpolatorTypeName		= "ScalarInterpolator";
  static public final String positionInterpolatorTypeName		= "PositionInterpolator";
  static public final String coordinateInterpolatorTypeName	= "CoordinateInterpolator";
  static public final String orientationInterpolatorTypeName	= "OrientationInterpolator";
  static public final String normalInterpolatorTypeName		= "NormalInterpolator";
  static public final String colorInterpolatorTypeName			= "ColorInterpolator";
  static public final String scriptTypeName						= "Script";
  static public final String cylinderSensorTypeName				= "CylinderSensor";
  static public final String cylinderTypeName					= "Cylinder";
  static public final String anchorTypeName						= "Anchor";
  static public final String audioClipTypeName					= "AudioClip";
  static public final String backgroundTypeName					= "Background";
  static public final String boxTypeName							= "Box";
  static public final String coneTypeName							= "Cone";
  static public final String elevationGridTypeName				= "ElevationGrid";
  static public final String extrusionTypeName					= "Extrusion";
  static public final String fogTypeName							= "Fog";
  static public final String fontStyleTypeName					= "FontStyle";
  static public final String imageTextureTypeName				= "ImageTexture";
  static public final String indexedLineSetTypeName				= "IndexedLineSet";
  static public final String movieTextureTypeName				= "MovieTexture";
  static public final String navigationInfoTypeName				= "NavigationInfo";
  static public final String pixelTextureTypeName				= "PixelTexture";
  static public final String pointSetTypeName					= "PointSet";
  static public final String soundTypeName						= "Sound";
  static public final String sphereTypeName						= "Sphere";
  static public final String textTypeName							= "Text";
  static public final String planeSensorTypeName				= "PlaneSensor";
  static public final String proximitySensorTypeName			= "ProximitySensor";
  static public final String sphereSensorTypeName				= "SphereSensor";
  static public final String touchSensorTypeName				= "TouchSensor";
  static public final String visibilitySensorTypeName			= "VisibilitySensor";
  static public final String textureTransformTypeName			= "TextureTransform";

  static public final String instanceTypeName					= "Instance";

  static public final String eventInStripString			= "set_";
  static public final String eventOutStripString		= "_changed";

  static public final String isActiveFieldName			= "isActive";
  static public final String fractionFieldName			= "fraction";
  static public final String durationFieldName			= "duration";
  static public final String valueFieldName				= "value";

  static public final int	fieldTypeNone					= 0x00000000;
  static public final int	fieldTypeSFBool				= 0x00000001;
  static public final int	fieldTypeSFColor				= 0x00000002;
  static public final int	fieldTypeSFFloat				= 0x00000004;
  static public final int	fieldTypeSFInt32				= 0x00000008;
  static public final int	fieldTypeSFRotation			= 0x00000010;
  static public final int	fieldTypeSFString				= 0x00000020;
  static public final int	fieldTypeSFTime				= 0x00000040;
  static public final int	fieldTypeSFVec2f				= 0x00000080;
  static public final int	fieldTypeSFVec3f				= 0x00000100;
  static public final int	fieldTypeSFNode				= 0x00000200;
  static public final int	fieldTypeSFImage				= 0x00000400;
  static public final int	fieldTypeMFBool				= 0x00000800;
  static public final int	fieldTypeMFColor				= 0x00001000;
  static public final int	fieldTypeMFFloat				= 0x00002000;
  static public final int	fieldTypeMFInt32				= 0x00004000;
  static public final int	fieldTypeMFRotation			= 0x00008000;
  static public final int	fieldTypeMFString				= 0x00010000;
  static public final int	fieldTypeMFTime				= 0x00020000;
  static public final int	fieldTypeMFVec2f				= 0x00040000;
  static public final int	fieldTypeMFVec3f				= 0x00080000;
  static public final int	fieldTypeMFNode				= 0x00100000;
  
  static public final int	fieldTypeConstSFBool		= (fieldTypeSFBool     | 0xFF000000);
  static public final int	fieldTypeConstSFColor		= (fieldTypeSFColor    | 0xFF000000);
  static public final int	fieldTypeConstSFFloat		= (fieldTypeSFFloat    | 0xFF000000);
  static public final int	fieldTypeConstSFInt32		= (fieldTypeSFInt32    | 0xFF000000);
  static public final int	fieldTypeConstSFRotation	= (fieldTypeSFRotation | 0xFF000000);
  static public final int	fieldTypeConstSFString		= (fieldTypeSFString   | 0xFF000000);
  static public final int	fieldTypeConstSFTime		= (fieldTypeSFTime     | 0xFF000000);
  static public final int	fieldTypeConstSFVec2f		= (fieldTypeSFVec2f    | 0xFF000000);
  static public final int	fieldTypeConstSFVec3f		= (fieldTypeSFVec3f    | 0xFF000000);
  static public final int	fieldTypeConstSFNode		= (fieldTypeSFNode     | 0xFF000000);
  static public final int	fieldTypeConstSFImage		= (fieldTypeSFImage    | 0xFF000000);
  static public final int	fieldTypeConstMFBool		= (fieldTypeMFBool     | 0xFF000000);
  static public final int	fieldTypeConstMFColor		= (fieldTypeMFColor    | 0xFF000000);
  static public final int	fieldTypeConstMFFloat		= (fieldTypeMFFloat    | 0xFF000000);
  static public final int	fieldTypeConstMFInt32		= (fieldTypeMFInt32    | 0xFF000000);
  static public final int	fieldTypeConstMFRotation	= (fieldTypeMFRotation | 0xFF000000);
  static public final int	fieldTypeConstMFString		= (fieldTypeMFString   | 0xFF000000);
  static public final int	fieldTypeConstMFTime		= (fieldTypeMFTime     | 0xFF000000);
  static public final int	fieldTypeConstMFVec2f		= (fieldTypeMFVec2f    | 0xFF000000);
  static public final int	fieldTypeConstMFVec3f		= (fieldTypeMFVec3f    | 0xFF000000);
  static public final int	fieldTypeConstMFNode		= (fieldTypeMFNode     | 0xFF000000);
}
