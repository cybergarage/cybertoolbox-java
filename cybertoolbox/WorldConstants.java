/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	WorldConstants.java
*
******************************************************************/

public interface WorldConstants {
	public static final String WORLD_RELEASE_NUMBER	= "2.0";
	public static final String WORLD_RELEASE_DATE	= "1999/01/10";
	
	public static final String USERDIR	= System.getProperty("user.dir");
	public static final String FILESEP	= System.getProperty("file.separator");

	public static final String WORLD_EVENTTYPE_FILENAME			= "EVENTTYPE.DEF";

	public static final String WORLD_NODE_NAME								= "WORLD_";
	public static final String WORLD_ROOT_NODE_NAME						= "WORLD_ROOT_NODE";
	public static final String WORLD_DIAGRAM_ROOT_NODE_NAME		= "WORLD_DIAGRAM_ROOT_NODE";
	public static final String WORLD_GLOBALDATA_ROOT_NODE_NAME	= "WORLD_GLOBAL_DATA_ROOT_NODE";

	public static final String WORLD_EVENT_ROOT_NODE_NAME			= "WORLD_EVENT_ROOT_NODE";
	public static final String WORLD_EVENT_NODE_NAME					= "WORLD_EVENT";
	public static final String WORLD_SUBEVENT_ROOT_NODE_NAME		= "WORLD_SUBEVENT_ROOT_NODE";
	public static final String WORLD_SUBEVENT_NODE_NAME				= "WORLD_SUBEVENT";

	public static final String WORLD_DIAGRAM_NODENAME					= "WORLD_DIAGRAM";

	public static final int WORLD_RENDERING_WIRE		= 0;
	public static final int WORLD_RENDERING_SHADE	= 1;
	public static final int WORLD_RENDERING_TEXTURE	= 2;

	public static final int WORLD_OPETATION_MODE_NONE							= 0;
	public static final int WORLD_OPETATION_MODE_MODULETYPE_DRAGGED	= 1;

	public static final String WORLD_GLOBALDATA_NODENAME				= "WORLD_GLOBALDATA";

	public static final String WORLD_MODULETYPE_DIRECTORY				= USERDIR + FILESEP + "modules";
	public static final String WORLD_MODULETYPE_ICON_DIRECTORY		= WORLD_MODULETYPE_DIRECTORY + FILESEP + "images";
	public static final String WORLD_MODULETYPE_SCRIPT_DIRECTORY	= WORLD_MODULETYPE_DIRECTORY + FILESEP + "scripts";

	public static final String WORLD_IMAGE_DIRECTORY									= USERDIR + FILESEP + "images";
	public static final String WORLD_IMAGE_TOOLBAR_DIRECTORY							= WORLD_IMAGE_DIRECTORY + FILESEP + "toolbar";
	public static final String WORLD_IMAGE_TOOLBAR_WORLDTREE_DIRECTORY			= WORLD_IMAGE_TOOLBAR_DIRECTORY + FILESEP + "worldtree";
	public static final String WORLD_IMAGE_TOOLBAR_PERSPECTIVEVIEW_DIRECTORY	= WORLD_IMAGE_TOOLBAR_DIRECTORY + FILESEP + "perspectiveview";
	public static final String WORLD_IMAGE_TOOLBAR_DIAGRAM_DIRECTORY				= WORLD_IMAGE_TOOLBAR_DIRECTORY + FILESEP + "diagram";
	public static final String WORLD_IMAGE_NODE_DIRECTORY								= WORLD_IMAGE_DIRECTORY + FILESEP + "node";
}

