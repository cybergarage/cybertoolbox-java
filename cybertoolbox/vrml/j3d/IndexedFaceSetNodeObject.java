/******************************************************************
*
*	CyberVRML97 for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : IndexedFaseSetNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;
import vrml.util.Debug;

public class IndexedFaceSetNodeObject extends IndexedTriangleArray implements NodeObject {
	
	public IndexedFaceSetNodeObject(IndexedFaceSetNode node) {
		super(getVertexCount(node), getVertexFormat(node), node.getNTriangleCoordIndices());
		initialize(node);
	}

	static public int getVertexCount(IndexedFaceSetNode node) {
		int count = node.getCoordinateNodes() != null ? node.getCoordinateNodes().getNPoints() : 0;
		Debug.message("IndexedFaceSetNodeObject::getVertexCount = " + count);
		return count;
	}
	
	static public int getVertexFormat(IndexedFaceSetNode node) {
		int vertexFormat = COORDINATES | NORMALS;
		if (node.getColorNodes() != null)
			vertexFormat |= COLOR_3;
		if (node.getTextureCoordinateNodes() != null)
			vertexFormat |= TEXTURE_COORDINATE_2;
		return vertexFormat;
	}
	
	public boolean initialize(vrml.node.Node node) {
		IndexedFaceSetNode idxFaceSetNode = (IndexedFaceSetNode)node;
		
		MFInt32	index = new MFInt32();

		int		nTriangle			= 0;
		int		nCoordinatePoints	= 0;	
		
		int nCoordIndices = idxFaceSetNode.getNCoordIndices();

		/**** Coordinate *********************************************/
		CoordinateNode coordNode = idxFaceSetNode.getCoordinateNodes();
		if (coordNode != null) {
			nCoordinatePoints = coordNode.getNPoints();
			float point[] = new float[3];
			for (int n=0; n<nCoordinatePoints; n++) {
				coordNode.getPoint(n, point);
				setCoordinate(n, point);
			}
			index.clear();
			nTriangle = 0;
			for (int n=0; n<nCoordIndices; n++) {
				int id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-2); i++) {
						setCoordinateIndex(nTriangle*3,		index.get1Value(0));
						setCoordinateIndex(nTriangle*3+1,	index.get1Value(i+1));
						setCoordinateIndex(nTriangle*3+2,	index.get1Value(i+2));
						nTriangle++;
					}
					index.clear();
				}
			}
		}
		
		/**** Color *********************************************/
		ColorNode colorNode = idxFaceSetNode.getColorNodes();
		if (colorNode != null) {
			float color[] = new float[3];
			int nColors = colorNode.getNColors();
			for (int n=0; n<nColors && n<nCoordinatePoints; n++) {
				colorNode.getColor(n, color);
				setColor(n, color);
			}
			index.clear();
			nTriangle = 0;
			if (idxFaceSetNode.isColorPerVertex() == true) {
				int nColorIndices = idxFaceSetNode.getNColorIndices();
				boolean hasColorIndices = (nCoordIndices <= nColorIndices) ? true : false;
				if (hasColorIndices == false)
					 nColorIndices = nCoordIndices;
				for (int n=0; n<nColorIndices; n++) {
					int id = 0;
					if (hasColorIndices == true)
						id = idxFaceSetNode.getColorIndex(n);
					else
						id = idxFaceSetNode.getCoordIndex(n);
						
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nColorIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							setColorIndex(nTriangle*3,		index.get1Value(0));
							setColorIndex(nTriangle*3+1,	index.get1Value(i+1));
							setColorIndex(nTriangle*3+2,	index.get1Value(i+2));
							nTriangle++;
						}
						index.clear();
					}
				}
			}
			else {
				int nColorIndices = idxFaceSetNode.getNColorIndices();
				boolean hasColorIndices = (idxFaceSetNode.getNPolygons() <= nColorIndices) ? true : false;
				int nPolygon = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							int normalIndex = 0;
							if (hasColorIndices)
								normalIndex = idxFaceSetNode.getColorIndex(nPolygon);
							else
								normalIndex = nPolygon;
							setColorIndex(nTriangle*3,		normalIndex);
							setColorIndex(nTriangle*3+1,	normalIndex);
							setColorIndex(nTriangle*3+2,	normalIndex);
							nTriangle++;
						}
						index.clear();
						nPolygon++;
					}
				}
			}
		}
	
		/**** Normal *********************************************/
		NormalNode normalNode = idxFaceSetNode.getNormalNodes();
		if (normalNode != null) {
			float vector[] = new float[3];
			int nVectors = normalNode.getNVectors();
			for (int n=0; n<nVectors && n<nCoordinatePoints; n++) {
				normalNode.getVector(n, vector);
				setNormal(n, vector);
			}
			index.clear();
			nTriangle = 0;
			if (idxFaceSetNode.isNormalPerVertex() == true) {
				int nNormalIndices = idxFaceSetNode.getNNormalIndices();
				boolean hasNormalIndices = (nCoordIndices <= nNormalIndices) ? true : false;
				if (hasNormalIndices == false)
					 nNormalIndices = nCoordIndices;
				for (int n=0; n<nNormalIndices; n++) {
					int id = 0;
					if (hasNormalIndices == true)
						id = idxFaceSetNode.getNormalIndex(n);
					else
						id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nNormalIndices-1)) {
						int indexSize = index.getSize();
						for (int i=0; i<(indexSize-2); i++) {
							setNormalIndex(nTriangle*3,		index.get1Value(0));
							setNormalIndex(nTriangle*3+1,	index.get1Value(i+1));
							setNormalIndex(nTriangle*3+2,	index.get1Value(i+2));
							nTriangle++;
						}	
						index.clear();
					}
				}
			}
			else {
				int nNormalIndices = idxFaceSetNode.getNNormalIndices();
				boolean hasNormalIndices = (idxFaceSetNode.getNPolygons() <= nNormalIndices) ? true : false;
				int nPolygon = 0;
				for (int n=0; n<nCoordIndices; n++) {
					int id = idxFaceSetNode.getCoordIndex(n);
					if (id != -1)
						index.addValue(id);
					if (id == -1 || n == (nCoordIndices-1)) {
						for (int i=0; i<(index.getSize()-2); i++) {
							int normalIndex = 0;
							if (hasNormalIndices)
								normalIndex = idxFaceSetNode.getNormalIndex(nPolygon);
							else
								normalIndex = nPolygon;
							setNormalIndex(nTriangle*3,		normalIndex);
							setNormalIndex(nTriangle*3+1,	normalIndex);
							setNormalIndex(nTriangle*3+2,	normalIndex);
							nTriangle++;
						}
						index.clear();
						nPolygon++;
					}
				}
			}
		}

		/**** TexCoord *********************************************/
		TextureCoordinateNode texCoordNode = idxFaceSetNode.getTextureCoordinateNodes();
		if (texCoordNode != null) {
			float point[] = new float[2];
			int nTexCoordPoints = texCoordNode.getNPoints();
			for (int n=0; n<nTexCoordPoints && n<nCoordinatePoints; n++) {
				texCoordNode.getPoint(n, point);
				setTextureCoordinate(n, point);
			}
			index.clear();
			nTriangle = 0;
			int nTexCoordIndices = idxFaceSetNode.getNTexCoordIndices();
			boolean hasTexCoordIndices = (nCoordIndices <= nTexCoordIndices) ? true : false;
			if (hasTexCoordIndices == false)
				 nTexCoordIndices = nCoordIndices;
			for (int n=0; n<nTexCoordIndices; n++) {
				int id = 0;
				if (hasTexCoordIndices == true)
					id = idxFaceSetNode.getTexCoordIndex(n);
				else
					id = idxFaceSetNode.getCoordIndex(n);
				if (id != -1)
					index.addValue(id);
				if (id == -1 || n == (nTexCoordIndices-1)) {
					int indexSize = index.getSize();
					for (int i=0; i<(indexSize-2); i++) {
						setTextureCoordinateIndex(nTriangle*3,		index.get1Value(0));
						setTextureCoordinateIndex(nTriangle*3+1,	index.get1Value(i+1));
						setTextureCoordinateIndex(nTriangle*3+2,	index.get1Value(i+2));
						nTriangle++;
					}	
					index.clear();
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
