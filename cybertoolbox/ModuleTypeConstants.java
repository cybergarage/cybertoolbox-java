/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	ModuleTypeConstants.java
*
******************************************************************/

public interface ModuleTypeConstants {
	public final int MODULE_INOUTNODE_MAXNUM						= 4;

	public static final String	MODULE_FILE_EXTENTION				= "mod";
	public static final String	MODULE_ICON_FILE_EXTENTION			= "ico";

	public static final int		MODULETYPE_ATTRIBUTE_SYSTEM			= 0x00;
	public static final int		MODULETYPE_ATTRIBUTE_USER			= 0x01;

	public static final String	MODULETYPEFILE_CLASSNAME_STRING		= "CLASSNAME";
	public static final String	MODULETYPEFILE_MODULENAME_STRING	= "MODULENAME";
	public static final String	MODULETYPEFILE_ATTRIBUTE_STRING		= "ATTRIBUTE";
	public static final String	MODULETYPEFILE_SCRIPTNAME_STRING	= "SCRIPTNAME";
	public static final String	MODULETYPEFILE_ICONNAME_STRING		= "ICONNAME";
	public static final String	MODULETYPEFILE_EVENTIN_STRING		= "EVENTIN";
	public static final String	MODULETYPEFILE_EVENTOUT_STRING		= "EVENTOUT";
	public static final String	MODULETYPEFILE_EXECUTIONNODE_STRING	= "EXECUTIONNODE";

	public static final String	MODULETYPE_ATTRIBUTE_SYSTEM_STRING	= "SYSTEM";
	public static final String	MODULETYPE_ATTRIBUTE_USER_STRING	= "USER";

	public static final String	MODULETYPE_FILENAME[] = {
		"BooleanEqual.mod",
		"BooleanNotEqual.mod",
		"BooleanGreater.mod",
		"BooleanLess.mod",
		"BooleanEqualGreater.mod",
		"BooleanEqualLess.mod",
		"BooleanNot.mod",
		"BooleanAnd.mod",
		"BooleanOr.mod",
		//"BooleanIf.mod",

		"FilterScale.mod",
		"FilterOffset.mod",
		"FilterCeil.mod",
		"FilterFloor.mod",
		"FilterHigh.mod",
		"FilterLow.mod",
		"FilterRange.mod",
		//"FilterScalarInterpolator.mod",
		//"FilterPosition2DInterpolator.mod",
		//"FilterPosition3DInterpolator.mod",
		//"FilterOrientationInterpolator.mod",

		"GeometryNormalize.mod",
		"GeometryInverse.mod",
		"GeometryGetLength.mod",
		"GeometryGetDot.mod",
		"GeometryGetAngle.mod",
		"GeometryGetVector.mod",
		"GeometryGetDistance.mod",
		"GeometryGetCross.mod",
		"GeometryRotate.mod",
		
		"LightSetOn.mod",
		"LightSetColor.mod",
		"LightSetIntensity.mod",
		"LightSetLocation.mod",
		"LightSetDirection.mod",
		"LightSetRadius.mod",
		"LightGetOn.mod",
		"LightGetColor.mod",
		"LightGetIntensity.mod",
		"LightGetLocation.mod",
		"LightGetDirection.mod",
		"LightGetRadius.mod",

		"MaterialSetAmbientIntensity.mod",
		"MaterialSetDiffuseColor.mod",
		"MaterialSetEmissiveColor.mod",
		"MaterialSetShininess.mod",
		"MaterialSetTransparency.mod",
		"MaterialGetAmbientIntensity.mod",
		"MaterialGetDiffuseColor.mod",
		"MaterialGetEmissiveColor.mod",
		"MaterialGetShininess.mod",
		"MaterialGetTransparency.mod",

		"MathIncrement.mod",
		"MathDecrement.mod",
		"MathAbs.mod",
		"MathNegative.mod",
		"MathPow.mod",
		"MathSqrt.mod",
		"MathMin.mod",
		"MathMax.mod",
		"MathLog.mod",
		"MathExp.mod",
		"MathSin.mod",
		"MathCos.mod",
		"MathTan.mod",
		"MathASin.mod",
		"MathACos.mod",
		"MathATan.mod",
		"MathDegree2Radian.mod",
		"MathRadian2Degree.mod",

		"NumericAdd2Values.mod",
		"NumericSub2Values.mod",
		"NumericMulti2Values.mod",
		"NumericDiv2Values.mod",
		"NumericMod2Values.mod",
		"NumericAnd2Values.mod",
		"NumericOr2Values.mod",
		"NumericXor2Values.mod",

		"ObjectSetLocation.mod",
		"ObjectSetRotation.mod",
		"ObjectSetScale.mod",
		"ObjectSetCenter.mod",
		"ObjectGetLocation.mod",
		"ObjectGetRotation.mod",
		"ObjectGetScale.mod",
		"ObjectGetCenter.mod",

		"StringValue.mod",
		"StringPosition.mod",
		"StringDirection.mod",
		"StringRotation.mod",
		"StringBool.mod",
		"StringColor.mod",
		"StringPI.mod",
		"StringE.mod",
		"StringDivide2Values.mod",
		"StringDivide3Values.mod",
		"StringDivide4Values.mod",
		"StringMerge2Values.mod",
		"StringMerge3Values.mod",
		"StringMerge4Values.mod",
		"StringSelector.mod",
		//"StringSetValue.mod",
		//"StringSetArrayValue.mod",
		//"StringGetValue.mod",
		//"StringGetArrayValue.mod",

		"SystemArea.mod",
		"SystemClock.mod",
		"SystemDrag.mod",
		"SystemFrame.mod",
		"SystemPickup.mod",

		"InterpScalar.mod",
		"InterpPosition.mod",
		"InterpNormal.mod",
		"InterpOrientation.mod",
		"InterpColor.mod",

		"ViewSetPosition.mod",
		"ViewSetOrientation.mod",
		"ViewSetFOV.mod",
		"ViewGetPosition.mod",
		"ViewGetOrientation.mod",
		"ViewGetFOV.mod",
		
		//"NodeSetImageTexture.mod",
		//"NodeSetAudioClip.mod",
		"NodeSetSwitch.mod",
		//"NodeSetText.mod",
		"NodeSetSkyColor.mod",

		"MiscGetTime.mod",
		"MiscRandom.mod",
		"MiscBeep.mod",
		//"MiscPlaySound.mod",
		"MiscJavaConsole.mod",
		//"MiscObjectCollision.mod",
	};
}
