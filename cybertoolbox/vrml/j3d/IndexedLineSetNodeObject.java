/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IndexedLineSetNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;
import vrml.util.Debug;

public class IndexedLineSetNodeObject extends IndexedLineArray implements NodeObject {
	
	public IndexedLineSetNodeObject(IndexedLineSetNode node) {
		super(getVertexCount(node), getVertexFormat(node), node.getNLines()*2);
		initialize(node);
	}

	static public int getVertexCount(IndexedLineSetNode node) {
		return (node.getCoordinateNodes() != null ? node.getCoordinateNodes().getNPoints() : 0);
	}
	
	static public int getVertexFormat(IndexedLineSetNode node) {
		int vertexFormat = COORDINATES | NORMALS;
		if (node.getColorNodes() != null)
			vertexFormat |= COLOR_3;
		return vertexFormat;
	}
	
	public boolean initialize(vrml.node.Node node) {
		IndexedLineSetNode idxLineSetNode = (IndexedLineSetNode)node;
		
		MFInt32	index = new MFInt32();

		int nLines					= 0;
		int nCoordinatePoints	= 0;	
		
		int nCoordIndices = idxLineSetNode.getNCoordIndices();

		/**** Coordinate *********************************************/
		CoordinateNode coordNode = idxLineSetNode.getCoordinateNodes();
		if (coordNode != null) {
			float point[] = new float[3];
			nCoordinatePoints = coordNode.getNPoints();
			for (int n=0; n<nCoordinatePoints; n++) {
				coordNode.getPoint(n, point);
				setCoordinate(n, point);
			}
			index.clear();
			nLines = 0;
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxLineSetNode.getCoordIndex(n);
				Debug.message("IndexedLineSetNodeObject::initialize = " + nCoordIndices + ", " + n + ", " + id);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-1); i++) {
						setCoordinateIndex(nLines, index.get1Value(i)); nLines++;
						setCoordinateIndex(nLines, index.get1Value(i+1)); nLines++;
					}
					index.clear();
				}
			}
		}
		
		/**** Color *********************************************/
		ColorNode colorNode = idxLineSetNode.getColorNodes();
		if (colorNode != null) {
			float color[] = new float[3];
			int nColors = colorNode.getNColors();
			for (int n=0; n<nColors && n<nCoordinatePoints; n++) {
				colorNode.getColor(n, color);
				setColor(n, color);
			}
			index.clear();
			nLines = 0;
			if (idxLineSetNode.isColorPerVertex() == true) {
				int nColorIndices = idxLineSetNode.getNColorIndices();
				boolean hasColorIndices = (nCoordIndices <= nColorIndices) ? true : false;
				if (hasColorIndices == false)
					 nColorIndices = nCoordIndices;
				for (int n=0; n<nColorIndices; n++) {
					int id = 0;
					if (hasColorIndices == true)
						id = idxLineSetNode.getColorIndex(n);
					else
						id = idxLineSetNode.getCoordIndex(n);
						
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nColorIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-1); i++) {
							setColorIndex(nLines++, index.get1Value(i));
							setColorIndex(nLines++, index.get1Value(i+1));
						}
						index.clear();
					}
				}
			}
			else {
				int nColorIndices = idxLineSetNode.getNColorIndices();
				boolean hasColorIndices = (idxLineSetNode.getNLines() <= nColorIndices) ? true : false;
				int lineNum = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxLineSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-1); i++) {
							int normalIndex = 0;
							if (hasColorIndices)
								normalIndex = idxLineSetNode.getColorIndex(lineNum);
							else
								normalIndex = lineNum;
							setColorIndex(nLines++, normalIndex);
							setColorIndex(nLines++, normalIndex);
						}
						index.clear();
						lineNum++;
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean uninitialize(vrml.node.Node node) {
		return true;
	}
			
	public boolean update(vrml.node.Node node) {
		return true;
	}
	
	public boolean add(vrml.node.Node node) {

		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(this);
				}
			}
		}
		
		return true;
	}

	public boolean remove(vrml.node.Node node) {
	
		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(new NullGeometryObject());
				}
			}
		}
		
		return true;
	}
}
