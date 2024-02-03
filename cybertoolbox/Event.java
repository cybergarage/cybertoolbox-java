/******************************************************************
*
*	CyberToolBox for Java
*
*	Copyright (C) Satoshi Konno 1998
*
*	File:	Event.java
*
******************************************************************/

import java.io.*;
import java.util.Vector;

import vrml.*;
import vrml.node.*;
import vrml.field.*;

///////////////////////////////////////////////////////////////////////
//
//	NODENAME		:	CTB_EVENT_("USER"|"SYSTEM")_(EventName)_(EventOption)_(NODETYPE)
//					;
//
//	NODETYPE		:	VRML_NODE_TYPE
//					|	"THIS"
//					;
//
///////////////////////////////////////////////////////////////////////

public class Event extends Object implements EventConstants, EventTypeConstants, WorldConstants {
	
	private Node	mNode = null;
	private World	mWorld = null;
	
	public void setWorld(World world) {
		mWorld = world;
	}
	
	public World getWorld() {
		return mWorld;
	}
	
	public Event(World world, EventType eventType, String optionString) {
		Debug.message("Event.<init>");
		Debug.message("\tworld        " + world); 
		Debug.message("\teventType    " + eventType); 
		Debug.message("\toptionString " + optionString); 
		
		setWorld(world);
		setNode(null); 
	
		if (optionString == null)
			optionString = EVENT_NONE_OPTION_NAME;
			
		// Create event node
		Node eventNode = null;

		//////////////////////////////////////
		//	Create Main Event Node
		//////////////////////////////////////

		switch (getWorld().getEventTypeNumber(eventType)) {
		case EVENTTYPE_START: // START (SYSTEM EVENT)
			{
				eventNode = new TimeSensorNode();
				((TimeSensorNode)eventNode).setStopTime(-1.0);
				((TimeSensorNode)eventNode).setLoop(true);
				((TimeSensorNode)eventNode).setCycleInterval(.1);
			}
			break;
		case EVENTTYPE_FRAME: // FRAME (SYSTEM EVENT)
			{
				eventNode = new TimeSensorNode();
				((TimeSensorNode)eventNode).setStopTime(-1.0);
				((TimeSensorNode)eventNode).setLoop(true);
				((TimeSensorNode)eventNode).setCycleInterval(.1);
			}
			break;
		case EVENTTYPE_CLOCK:
			{
				eventNode = new TimeSensorNode();
				((TimeSensorNode)eventNode).setStopTime(-1.0);
				((TimeSensorNode)eventNode).setLoop(true);
			}
			break;
		case EVENTTYPE_TIMER:
			{
				eventNode = new TimeSensorNode();
				((TimeSensorNode)eventNode).setStopTime(-1.0);
				((TimeSensorNode)eventNode).setLoop(true);
			}
			break;
		case EVENTTYPE_PICKUP:
			{
				eventNode = new GroupNode();
			}
			break;
		case EVENTTYPE_AREA:
			{
				eventNode = new ProximitySensorNode();
			}
			break;
		case EVENTTYPE_DRAG:
			{
				eventNode = new GroupNode();
			}
			break;
		default:
			Debug.warning("Event Class Error : Invalidate event type (" + eventType.getName() + ")");
			break;
		}

		setNode(eventNode);
		setName(eventNode, eventType, optionString);

		//////////////////////////////////////
		//	Create Sub Event Node
		//////////////////////////////////////
	
		switch (getWorld().getEventTypeNumber(eventType)) {
		case EVENTTYPE_START: // START (SYSTEM EVENT)
			{
				// Create sub event node
				ScriptNode eventStartNode = new ScriptNode();
				eventStartNode.setDirectOutput(true);
				eventStartNode.addUrl(EVENTTYPE_START_SCRIPT_NAME);
				eventStartNode.addEventIn(EVENTTYPE_START_SCRIPT_EVENTIN_NAME, new SFTime());
				eventStartNode.addEventOut(EVENTTYPE_START_SCRIPT_EVENTOUT_NAME, new ConstSFBool());
				String nodeName = getSourceNodeName();
				eventStartNode.setName(nodeName);
				getWorld().addSubSystemEvent(eventStartNode);

				// Connect the event route
				Node	sourceNode,		eventInNode;
				Field	sourceField,	eventInField;
				sourceNode		= eventNode;
				sourceField		= eventNode.getEventOut("time");
				eventInNode		= eventStartNode;
				eventInField	= eventStartNode.getEventIn(EVENTTYPE_START_SCRIPT_EVENTIN_NAME);
				getWorld().getSceneGraph().addRoute(sourceNode, sourceField, eventInNode, eventInField);
				sourceNode		= eventStartNode;
				sourceField		= eventStartNode.getEventOut(EVENTTYPE_START_SCRIPT_EVENTOUT_NAME);
				eventInNode		= eventNode;
				eventInField	= eventNode.getExposedField("enabled");
				getWorld().getSceneGraph().addRoute(sourceNode, sourceField, eventInNode, eventInField);
			}
			break;
		}
	}


	public Event(World world, Node node) {
		Debug.assert(world != null, "Event::Event (world == null)");
		Debug.assert(node != null, "Event::Event (node == null)");
		setWorld(world);
		setNode(node);
	}

	//////////////////////////////////////
	// Event Type
	//////////////////////////////////////

	public String getEventTypeName() {
		String eventName = getName();

		if (eventName == null)
			return null;
	
		int	index1 = eventName.indexOf('_');
		int	index2 = eventName.indexOf('_', index1 + 1);

		int length = (index2-1) - index1;
		String eventTypeName = new String(eventName.toCharArray(), index1+1, length);
		
		return eventTypeName;
	}
	
	public EventType getEventType() {
		String eventName = getEventTypeName();
		if (eventName == null)
			return null;

		EventType eventType = getWorld().getEventType(eventName);
		return eventType;
	}

	public int getEventTypeNumber() {
		return getWorld().getEventTypeNumber(getEventType());
	}

	public int getAttributeType() {
		return getEventType().getAttributeType();
	}
	
	public String getOptionString() {
		String eventName = getName();
	
		if (eventName == null)
			return null;

		int	index1 = eventName.indexOf('_');
		int	index2 = eventName.indexOf('_', index1 + 1);
		int	index3 = eventName.indexOf('_', index2 + 1);

		int length = (index3-1) - index2;
		String eventOptionName = new String(eventName.toCharArray(), index2+1, length);

		if (eventOptionName.equals(EVENT_NONE_OPTION_NAME) == true)
			eventOptionName = null;

		return eventOptionName;
	}

	private class OptionStringTokenizer extends StreamTokenizer {
	
		public OptionStringTokenizer(String string) { 
			super(new StringReader(string));
		}

		public float[] getValues() {
			Vector tokenBuffer = new Vector();
			try {
				nextToken();
				while (ttype != TT_EOF) {
					switch (ttype) {
					case TT_NUMBER:
						tokenBuffer.addElement(new Double(nval));
						break;
					}
					nextToken();
				}
			}
			catch (IOException e) {
				return null;
			}
		
			float values[] = new float[tokenBuffer.size()];
			for (int n=0; n<tokenBuffer.size(); n++) 
				values[n] = ((Double)tokenBuffer.elementAt(n)).floatValue();
		
			return values;
		}	
	} 
	
	public float []getOptionValues() {
		String optionString = getOptionString();
		OptionStringTokenizer tokenizer = new OptionStringTokenizer(optionString);
		return tokenizer.getValues();
	}

	public float getOptionValue() {
		Double optionValue;
		try {
			 optionValue = new Double(getOptionString());
		}
		catch (NumberFormatException e) {
			return 0;
		}
		return optionValue.floatValue();
	}
	
	//////////////////////////////////////
	// Node
	//////////////////////////////////////
	
	public boolean isEventNode() {
		String headNameString = null;
		
		Node node = getNode();
		if (node == null)
			return false;
		
		String nodeName = node.getName();
		if (nodeName == null)
			return false;
			
		try {
			headNameString = new String(nodeName.toCharArray(), 0, WORLD_EVENT_NODE_NAME.length());
		}
		catch (StringIndexOutOfBoundsException e) {
			return false;
		}
		
		if (headNameString.equals(WORLD_EVENT_NODE_NAME) == true)
			return true;
		else
			return false;
	}
		
	public void	setNode(Node node) {
		mNode = node;
	}
	
	public Node getNode() {
		return mNode;
	}
	
	//////////////////////////////////////
	// Name
	//////////////////////////////////////

	void setName(Node node, EventType eventType, String optionString) {
		Debug.message("Event::setName");
		Debug.message("\tnode         = " + node);
		Debug.message("\teventType    = " + eventType);
		Debug.message("\toptionString = " + optionString);
		
		if (eventType == null)
			return;

		int n;
		
		/**** GET CURRENT DIAGRAM NODES ****/
		Node		eventNode = getNode();
		Vector		diagramNodes = new Vector();
		int			nDiagramNodes = 0;
		for (TransformNode transform=getWorld().getDiagramNodes(); transform != null; transform=getWorld().nextDiagramNode(transform)) {
			Diagram diagram = new Diagram(getWorld(), transform);
			if (eventNode == diagram.getEventNode()) {
				diagramNodes.addElement(transform);
				nDiagramNodes++;
			}
		}

		/**** SET OPTION PARAMETER ****/
		switch (getWorld().getEventTypeNumber(eventType)) {
		case EVENTTYPE_CLOCK:
			{
				int intervalTime = 1;
				try {
					Integer time = new Integer(optionString);
					intervalTime = time.intValue();
				}
				catch (NumberFormatException e) {
					Debug.warning("Event::setName");
					Debug.warning("\tClock invalidate option string = " + optionString);
					break;
				}
				((TimeSensorNode)eventNode).setCycleInterval(intervalTime);
				((TimeSensorNode)eventNode).setLoop(true);
			}
			break;
		case EVENTTYPE_AREA:
			{
				OptionStringTokenizer optionTokenizer = new OptionStringTokenizer(optionString);
				float optionValues[] = optionTokenizer.getValues();
				
				if (optionValues.length == 6) {
					float		center[]		= new float[3];
					float		size[]		= new float[3];
				
					center[0] = optionValues[0];
					center[0] = optionValues[1];
					center[0] = optionValues[2];
					
					size[0] = optionValues[3];
					size[0] = optionValues[4];
					size[0] = optionValues[5];
					
					((ProximitySensorNode)eventNode).setCenter(center);
					((ProximitySensorNode)eventNode).setSize(size);
				}
				else
					Debug.warning("\tArea invalidate option string = " + optionString);
			}
			break;
		}

		Node		sourceNode;
		String	sourceNodeName;
		
		/**** DELETE CURRENT SUB EVENT NODE ****/
		sourceNode = getSourceNode();
		if (sourceNode != null) {
			if (sourceNode != getNode()) 
				sourceNode.remove();
		}
		
		/**** DELETE CURRENT SUB EVENT NODE (FOR TIMER EVENT)****/
		sourceNodeName = getSourceNodeName();
		Node subEventNode = getWorld().getSceneGraph().findNodeByName(sourceNodeName);
		if (subEventNode != null)
			subEventNode.remove();

		/**** TARGET EVENT NODE TYPE ****/
		String eventNodeTypeName = EVENT_DEFAULT_NODE_TYPE;
		switch (getWorld().getEventTypeNumber(eventType)) {
		case EVENTTYPE_PICKUP:
			eventNodeTypeName = "TouchSensor";
			break;
		case EVENTTYPE_DRAG:
			eventNodeTypeName = "PlaneSensor";
			break;
		}

		/**** SET NODE NAME ****/
		String nodeName = WORLD_EVENT_NODE_NAME + "_" + eventType.getAttributeTypeString() + "_" + eventType.getName() + "_" + optionString + "_" + eventNodeTypeName;
		node.setName(nodeName);

		/**** CREATE NEW SUB EVENT NODE ****/
		switch (getWorld().getEventTypeNumber(eventType)) {
		case EVENTTYPE_PICKUP:
			{
				sourceNodeName = getSourceNodeName();
				TouchSensorNode touchSensor = new TouchSensorNode();
				touchSensor.setName(sourceNodeName);
				Node parentNode = getWorld().getSceneGraph().findNodeByName(optionString);
				if (parentNode != null)
					parentNode.addChildNode(touchSensor);
			}
			break;

		case EVENTTYPE_DRAG:
			{
				sourceNodeName = getSourceNodeName();
				PlaneSensorNode planeSensor = new PlaneSensorNode();
				planeSensor.setName(nodeName);
				planeSensor.setMinPosition(-1.0f, -1.0f);
				planeSensor.setMaxPosition(1.0f, 1.0f);
				Node parentNode = getWorld().getSceneGraph().findNodeByName(optionString);
				if (parentNode != null)
					parentNode.addChildNode(planeSensor);
			}
			break;
		case EVENTTYPE_TIMER: // TIMER (USER EVENT)
			{
				// Create sub event node
				ScriptNode eventSetTimerNode = new ScriptNode();
				eventSetTimerNode.setDirectOutput(true);
				eventSetTimerNode.setMustEvaluate(true);
				eventSetTimerNode.addUrl(EVENTTYPE_TIMER_SCRIPT_NAME);
				eventSetTimerNode.addField(EVENTTYPE_TIMER_SCRIPT_VALUE_NAME, new SFTime(optionString));
				eventSetTimerNode.addEventIn(EVENTTYPE_TIMER_SCRIPT_EVENTIN_NAME, new SFTime());
				eventSetTimerNode.addEventOut(EVENTTYPE_TIMER_SCRIPT_STARTTIME_NAME, new ConstSFTime());
				eventSetTimerNode.addEventOut(EVENTTYPE_TIMER_SCRIPT_STOPTIME_NAME, new ConstSFTime());
			
				nodeName = getSourceNodeName();
				eventSetTimerNode.setName(nodeName);
				getWorld().addSubSystemEvent(eventSetTimerNode);

				// Connect the event route
				Node	eventOutNode,	eventInNode;
				Field	eventOutField,	eventInField;

				eventOutNode	= getWorld().getEventNode(getWorld().getEventType(EVENTTYPE_START));
				eventOutField	= eventOutNode.getEventOut("time");
				eventInNode		= eventSetTimerNode;
				eventInField	= eventSetTimerNode.getEventIn(EVENTTYPE_TIMER_SCRIPT_EVENTIN_NAME);
				getWorld().getSceneGraph().addRoute(eventOutNode, eventOutField, eventInNode, eventInField);
		
				eventOutNode	= eventSetTimerNode;
				eventOutField	= eventSetTimerNode.getEventOut(EVENTTYPE_TIMER_SCRIPT_STARTTIME_NAME);
				eventInNode		= eventNode;
				eventInField	= eventNode.getExposedField("startTime");
				getWorld().getSceneGraph().addRoute(eventOutNode, eventOutField, eventInNode, eventInField);
			
				eventOutNode	= eventSetTimerNode;
				eventOutField	= eventSetTimerNode.getEventOut(EVENTTYPE_TIMER_SCRIPT_STOPTIME_NAME);
				eventInNode		= eventNode;
				eventInField	= eventNode.getExposedField("stopTime");
				getWorld().getSceneGraph().addRoute(eventOutNode, eventOutField, eventInNode, eventInField);
			}
			break;
		}

		/**** RESET DIAGRAM NODE NAME ****/
		for (n=0; n<nDiagramNodes; n++) {
			Diagram diagram = new Diagram(getWorld(), (TransformNode)diagramNodes.elementAt(n));
			String name = diagram.getName();
			diagram.setTransformNodeName(name, this);
			name = diagram.getName();
			diagram.setName(name);
		}
		
		/**** RESET DIAGRAM MODULES****/
		for (n=0; n<nDiagramNodes; n++) {
			Diagram diagram = new Diagram(getWorld(), (TransformNode)diagramNodes.elementAt(n));
			int nModules = diagram.getNModules();
			for (int i=0; i<nModules; i++) {
				Module module = new Module(getWorld(), diagram.getModule(i));
				diagram.setModuleDefaultRoute(module);
			}
		}
		
	}
	
	public String getName() {
		String nodeName = getNode().getName();

		Debug.message("node     = " + getNode());
		Debug.message("nodeName = " + nodeName);
		
		if (nodeName == null)
			return null;

		int headNameLength = WORLD_EVENT_NODE_NAME.length() + 1 /* '_'*/;
		if (headNameLength < nodeName.length())
			return new String(nodeName.toCharArray(), headNameLength, nodeName.length() - headNameLength);
		else
			return null;
	}
	

	public void setOptionString(String optionString) {
		setName(getNode(), getEventType(), optionString);
	}
	
	//////////////////////////////////////
	// Node
	//////////////////////////////////////

	public String getSourceNodeTypeName() {
		String eventName = getName();

		if (eventName == null)
			return null;

		int	index = eventName.lastIndexOf('_');

		String eventNodeTypeName = new String(eventName.toCharArray(), index+1, eventName.length() - (index+1));

		return eventNodeTypeName;
	}
	
	public String getSourceNodeName() {
		Node node = getNode();
		if (node == null)
			return null;

		String sourceNodeName = node.getName() + "_" + EVENT_SOURCE_NODE_NAME;
		return sourceNodeName;
	}
	
	public Node getSourceNode() {
		String eventNodeTypeName = getSourceNodeTypeName();
		
		if (eventNodeTypeName == null)
			return null;
			
		if (eventNodeTypeName.equals(EVENT_DEFAULT_NODE_TYPE) == true)
			return getNode();

		String nodeName = getSourceNodeName();
		if (nodeName == null)
			return null;

		Node sourceNode = null;
		for (Node node = getWorld().getSceneGraph().getNodes(); node != null; node=node.nextTraversal()) {
			if (eventNodeTypeName.equals(node.getType()) == true) {
				if (nodeName.equals(node.getName()) == true) {
					sourceNode = node;
					break;
				}
			}
		}

		Debug.assert(sourceNode != null, "Event::getSourceNode (" + nodeName + " is not found)");
		
		return sourceNode;
	}
	
	public Field getSourceFiled() {
		Node		eventNode = getSourceNode();
		EventType	eventType = getEventType();
		
		Debug.assert(eventNode != null, "Event::getSourceField (eventNode == null)");
		Debug.assert(eventType != null, "Event::getSourceField (eventType == null)");
		
		Field field = eventNode.getEventOut(eventType.getFieldName());

		return field;
	}
}
