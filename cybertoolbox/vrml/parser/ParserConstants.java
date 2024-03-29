/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package vrml.parser;

public interface ParserConstants {

  int EOF = 0;
  int COMMENT = 5;
  int children = 6;
  int TRUE = 10;
  int FALSE = 11;
  int DEF = 14;
  int USE = 15;
  int ROUTE = 16;
  int TO = 17;
  int Anchor = 19;
  int url = 20;
  int parameter = 21;
  int description = 22;
  int bboxCenter = 23;
  int bboxSize = 24;
  int Appearance = 25;
  int material = 26;
  int textureTransform = 27;
  int texture = 28;
  int AudioClip = 29;
  int loop = 30;
  int pitch = 31;
  int startTime = 32;
  int stopTime = 33;
  int Background = 34;
  int backUrl = 35;
  int bottomUrl = 36;
  int frontUrl = 37;
  int leftUrl = 38;
  int rightUrl = 39;
  int topUrl = 40;
  int groundAngle = 41;
  int skyAngle = 42;
  int groundColor = 43;
  int skyColor = 44;
  int Billboard = 45;
  int axisOfRotation = 46;
  int Box = 47;
  int size = 48;
  int Collision = 49;
  int collide = 50;
  int Color = 51;
  int color = 52;
  int ColorInterpolator = 53;
  int Cone = 54;
  int height = 55;
  int bottomRadius = 56;
  int side = 57;
  int bottom = 58;
  int Coordinate = 59;
  int point = 60;
  int CoordinateInterpolator = 61;
  int key = 62;
  int keyValue = 63;
  int Cylinder = 64;
  int top = 65;
  int radius = 66;
  int CylinderSensor = 67;
  int minAngle = 68;
  int maxAngle = 69;
  int enabled = 70;
  int diskAngle = 71;
  int offset = 72;
  int autoOffset = 73;
  int DirectionalLight = 74;
  int on = 75;
  int intensity = 76;
  int ambientIntensity = 77;
  int direction = 78;
  int ElevationGrid = 79;
  int ccw = 80;
  int colorPerVertex = 81;
  int creaseAngle = 82;
  int normalPerVertex = 83;
  int solid = 84;
  int xDimension = 85;
  int xSpacing = 86;
  int zDimension = 87;
  int zSpacing = 88;
  int Extrusion = 89;
  int beginCap = 90;
  int endCap = 91;
  int crossSection = 92;
  int spine = 93;
  int Fog = 94;
  int fogType = 95;
  int visibilityRange = 96;
  int FontStyle = 97;
  int justify = 98;
  int family = 99;
  int style = 100;
  int horizontal = 101;
  int leftToRight = 102;
  int topToBottom = 103;
  int language = 104;
  int spacing = 105;
  int Group = 106;
  int ImageTexture = 107;
  int repeatS = 108;
  int repeatT = 109;
  int IndexedFaceSet = 110;
  int coord = 111;
  int normal = 112;
  int texCoord = 113;
  int coordIndex = 114;
  int colorIndex = 115;
  int texCoordIndex = 116;
  int normalIndex = 117;
  int convex = 118;
  int IndexedLineSet = 119;
  int Inline = 120;
  int LOD = 121;
  int center = 122;
  int range = 123;
  int level = 124;
  int Material = 125;
  int diffuseColor = 126;
  int specularColor = 127;
  int emissiveColor = 128;
  int shininess = 129;
  int transparency = 130;
  int MovieTexture = 131;
  int speed = 132;
  int NavigationInfo = 133;
  int avatarSize = 134;
  int headlight = 135;
  int type = 136;
  int visibilityLimit = 137;
  int Normal = 138;
  int vector = 139;
  int NormalInterpolator = 140;
  int OrientationInterpolator = 141;
  int PixelTexture = 142;
  int image = 143;
  int PlaneSensor = 144;
  int minPosition = 145;
  int maxPosition = 146;
  int PointLight = 147;
  int location = 148;
  int attenuation = 149;
  int PointSet = 150;
  int PositionInterpolator = 151;
  int ProximitySensor = 152;
  int ScalarInterpolator = 153;
  int Script = 154;
  int directOutput = 155;
  int mustEvaluate = 156;
  int eventIn = 157;
  int eventOut = 158;
  int field = 159;
  int SFBool = 160;
  int SFColor = 161;
  int SFFloat = 162;
  int SFInt32 = 163;
  int SFNode = 164;
  int SFRotation = 165;
  int SFString = 166;
  int SFTime = 167;
  int SFVec2f = 168;
  int SFVec3f = 169;
  int MFColor = 170;
  int MFFloat = 171;
  int MFInt32 = 172;
  int MFNode = 173;
  int MFRotation = 174;
  int MFString = 175;
  int MFTime = 176;
  int MFVec2f = 177;
  int MFVec3f = 178;
  int Sound = 180;
  int spatialize = 181;
  int maxBack = 182;
  int minBack = 183;
  int maxFront = 184;
  int minFront = 185;
  int priority = 186;
  int source = 187;
  int Sphere = 188;
  int SphereSensor = 189;
  int SpotLight = 190;
  int beamWidth = 191;
  int cutOffAngle = 192;
  int Shape = 193;
  int appearance = 194;
  int geometry = 195;
  int Switch = 196;
  int whichChoice = 197;
  int choice = 198;
  int Text = 199;
  int string = 200;
  int length = 201;
  int maxExtent = 202;
  int fontStyle = 203;
  int TextureCoordinate = 204;
  int TextureTransform = 205;
  int TimeSensor = 206;
  int cycleInterval = 207;
  int TouchSensor = 208;
  int Transform = 209;
  int rotation = 210;
  int scale = 211;
  int scaleOrientation = 212;
  int translation = 213;
  int Viewpoint = 214;
  int position = 215;
  int orientation = 216;
  int fieldOfView = 217;
  int jump = 218;
  int VisibilitySensor = 219;
  int WorldInfo = 220;
  int title = 221;
  int info = 222;
  int NUMBER = 223;
  int ID = 224;
  int STRING = 225;
  int FLOAT_NUMBER = 226;
  int EXPONENT = 227;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "<COMMENT>",
    "\"children\"",
    "\"{\"",
    "\"}\"",
    "\",\"",
    "\"TRUE\"",
    "\"FALSE\"",
    "\"[\"",
    "\"]\"",
    "\"DEF\"",
    "\"USE\"",
    "\"ROUTE\"",
    "\"TO\"",
    "\".\"",
    "\"Anchor\"",
    "\"url\"",
    "\"parameter\"",
    "\"description\"",
    "\"bboxCenter\"",
    "\"bboxSize\"",
    "\"Appearance\"",
    "\"material\"",
    "\"textureTransform\"",
    "\"texture\"",
    "\"AudioClip\"",
    "\"loop\"",
    "\"pitch\"",
    "\"startTime\"",
    "\"stopTime\"",
    "\"Background\"",
    "\"backUrl\"",
    "\"bottomUrl\"",
    "\"frontUrl\"",
    "\"leftUrl\"",
    "\"rightUrl\"",
    "\"topUrl\"",
    "\"groundAngle\"",
    "\"skyAngle\"",
    "\"groundColor\"",
    "\"skyColor\"",
    "\"Billboard\"",
    "\"axisOfRotation\"",
    "\"Box\"",
    "\"size\"",
    "\"Collision\"",
    "\"collide\"",
    "\"Color\"",
    "\"color\"",
    "\"ColorInterpolator\"",
    "\"Cone\"",
    "\"height\"",
    "\"bottomRadius\"",
    "\"side\"",
    "\"bottom\"",
    "\"Coordinate\"",
    "\"point\"",
    "\"CoordinateInterpolator\"",
    "\"key\"",
    "\"keyValue\"",
    "\"Cylinder\"",
    "\"top\"",
    "\"radius\"",
    "\"CylinderSensor\"",
    "\"minAngle\"",
    "\"maxAngle\"",
    "\"enabled\"",
    "\"diskAngle\"",
    "\"offset\"",
    "\"autoOffset\"",
    "\"DirectionalLight\"",
    "\"on\"",
    "\"intensity\"",
    "\"ambientIntensity\"",
    "\"direction\"",
    "\"ElevationGrid\"",
    "\"ccw\"",
    "\"colorPerVertex\"",
    "\"creaseAngle\"",
    "\"normalPerVertex\"",
    "\"solid\"",
    "\"xDimension\"",
    "\"xSpacing\"",
    "\"zDimension\"",
    "\"zSpacing\"",
    "\"Extrusion\"",
    "\"beginCap\"",
    "\"endCap\"",
    "\"crossSection\"",
    "\"spine\"",
    "\"Fog\"",
    "\"fogType\"",
    "\"visibilityRange\"",
    "\"FontStyle\"",
    "\"justify\"",
    "\"family\"",
    "\"style\"",
    "\"horizontal\"",
    "\"leftToRight\"",
    "\"topToBottom\"",
    "\"language\"",
    "\"spacing\"",
    "\"Group\"",
    "\"ImageTexture\"",
    "\"repeatS\"",
    "\"repeatT\"",
    "\"IndexedFaceSet\"",
    "\"coord\"",
    "\"normal\"",
    "\"texCoord\"",
    "\"coordIndex\"",
    "\"colorIndex\"",
    "\"texCoordIndex\"",
    "\"normalIndex\"",
    "\"convex\"",
    "\"IndexedLineSet\"",
    "\"Inline\"",
    "\"LOD\"",
    "\"center\"",
    "\"range\"",
    "\"level\"",
    "\"Material\"",
    "\"diffuseColor\"",
    "\"specularColor\"",
    "\"emissiveColor\"",
    "\"shininess\"",
    "\"transparency\"",
    "\"MovieTexture\"",
    "\"speed\"",
    "\"NavigationInfo\"",
    "\"avatarSize\"",
    "\"headlight\"",
    "\"type\"",
    "\"visibilityLimit\"",
    "\"Normal\"",
    "\"vector\"",
    "\"NormalInterpolator\"",
    "\"OrientationInterpolator\"",
    "\"PixelTexture\"",
    "\"image\"",
    "\"PlaneSensor\"",
    "\"minPosition\"",
    "\"maxPosition\"",
    "\"PointLight\"",
    "\"location\"",
    "\"attenuation\"",
    "\"PointSet\"",
    "\"PositionInterpolator\"",
    "\"ProximitySensor\"",
    "\"ScalarInterpolator\"",
    "\"Script\"",
    "\"directOutput\"",
    "\"mustEvaluate\"",
    "\"eventIn\"",
    "\"eventOut\"",
    "\"field\"",
    "\"SFBool\"",
    "\"SFColor\"",
    "\"SFFloat\"",
    "\"SFInt32\"",
    "\"SFNode\"",
    "\"SFRotation\"",
    "\"SFString\"",
    "\"SFTime\"",
    "\"SFVec2f\"",
    "\"SFVec3f\"",
    "\"MFColor\"",
    "\"MFFloat\"",
    "\"MFInt32\"",
    "\"MFNode\"",
    "\"MFRotation\"",
    "\"MFString\"",
    "\"MFTime\"",
    "\"MFVec2f\"",
    "\"MFVec3f\"",
    "\"NULL\"",
    "\"Sound\"",
    "\"spatialize\"",
    "\"maxBack\"",
    "\"minBack\"",
    "\"maxFront\"",
    "\"minFront\"",
    "\"priority\"",
    "\"source\"",
    "\"Sphere\"",
    "\"SphereSensor\"",
    "\"SpotLight\"",
    "\"beamWidth\"",
    "\"cutOffAngle\"",
    "\"Shape\"",
    "\"appearance\"",
    "\"geometry\"",
    "\"Switch\"",
    "\"whichChoice\"",
    "\"choice\"",
    "\"Text\"",
    "\"string\"",
    "\"length\"",
    "\"maxExtent\"",
    "\"fontStyle\"",
    "\"TextureCoordinate\"",
    "\"TextureTransform\"",
    "\"TimeSensor\"",
    "\"cycleInterval\"",
    "\"TouchSensor\"",
    "\"Transform\"",
    "\"rotation\"",
    "\"scale\"",
    "\"scaleOrientation\"",
    "\"translation\"",
    "\"Viewpoint\"",
    "\"position\"",
    "\"orientation\"",
    "\"fieldOfView\"",
    "\"jump\"",
    "\"VisibilitySensor\"",
    "\"WorldInfo\"",
    "\"title\"",
    "\"info\"",
    "<NUMBER>",
    "<ID>",
    "<STRING>",
    "<FLOAT_NUMBER>",
    "<EXPONENT>",
  };

}
