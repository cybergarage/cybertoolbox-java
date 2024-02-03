/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : CylinderNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class CylinderNodeObject extends TriangleArray implements NodeObject {

	public CylinderNodeObject(CylinderNode node) {
		super(getVertexCount(node), COORDINATES | NORMALS | TEXTURE_COORDINATE_2);
		createCone(node);
	}
	
	static private int getNDivide() {
		return 20;
	}
	
	static private int getVertexCount(CylinderNode cylinder) {
		int count = 0;
		if (cylinder.getSide())
			count += getNDivide()*2;
		if (cylinder.getTop())
			count += getNDivide();
		if (cylinder.getBottom())
			count += getNDivide();
		
		count *= 3;
		
		return count;
	}
	
	public boolean initialize(vrml.node.Node node) {
		return true;
	}

	private void createCone(CylinderNode cylinder) {
		int offset = 0;
		if (cylinder.getSide()) {
			createSide(cylinder, getNDivide(), offset);
			offset += getNDivide()*3*2;
		}
		if (cylinder.getTop()) {
			createTop(cylinder, getNDivide(), offset);
			offset += getNDivide()*3;
		}
		if (cylinder.getBottom())
			createBottom(cylinder, getNDivide(), offset);
	}
		
	private void createSide(CylinderNode cylinder, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3*2];
		Vector3f norms[]	= new Vector3f[nDivide*3*2];
		Point2f texCoords[]	= new Point2f[nDivide*3*2];
		
		float	height = cylinder.getHeight();
		float	radius = cylinder.getRadius();
		
		for (int n=0; n<nDivide; n++) {
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);

			verts[n*6+0] = new Point3f(sx*radius, height/2.0f, sz*radius);
			verts[n*6+1] = new Point3f(ex*radius, height/2.0f, ez*radius);
			verts[n*6+2] = new Point3f(sx*radius, -height/2.0f, sz*radius);

			norms[n*6+0] = new Vector3f(sx, 0.0f, sz); norms[n*6+0].normalize();
			norms[n*6+1] = new Vector3f(ex, 0.0f, ez); norms[n*6+1].normalize();
			norms[n*6+2] = new Vector3f(sx, 0.0f, sz); norms[n*6+2].normalize();
			
			texCoords[n*6+0] = new Point2f((float)(n+0)/(float)nDivide, 1.0f);
			texCoords[n*6+1] = new Point2f((float)(n+1)/(float)nDivide, 1.0f);
			texCoords[n*6+2] = new Point2f((float)(n+0)/(float)nDivide, 0.0f);

			verts[n*6+3] = new Point3f(ex*radius, height/2.0f, ez*radius);
			verts[n*6+4] = new Point3f(ex*radius, -height/2.0f, ez*radius);
			verts[n*6+5] = new Point3f(sx*radius, -height/2.0f, sz*radius);

			norms[n*6+3] = new Vector3f(ex, 0.0f, ez); norms[n*6+3].normalize();
			norms[n*6+4] = new Vector3f(ex, 0.0f, ez); norms[n*6+4].normalize();
			norms[n*6+5] = new Vector3f(sx, 0.0f, sz); norms[n*6+5].normalize();
			
			texCoords[n*6+3] = new Point2f((float)(n+1)/(float)nDivide, 1.0f);
			texCoords[n*6+4] = new Point2f((float)(n+1)/(float)nDivide, 0.0f);
			texCoords[n*6+5] = new Point2f((float)(n+0)/(float)nDivide, 0.0f);
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
	}

	private float cosValueToTexCoordX(float cosValue) {
		return (cosValue+1.0f)/2.0f;
	}

	private float sinValueToTexCoordY(float sinValue) {
		return 0.5f - sinValue/2.0f;
	}					   
		
	private void createTop(CylinderNode cylinder, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	height = cylinder.getHeight();
		float	radius = cylinder.getRadius();
		
		for (int n=0; n<nDivide; n++) {
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			
			verts[n*3+0] = new Point3f(0.0f, height/2.0f, 0.0f);
			verts[n*3+1] = new Point3f(sx*radius, height/2.0f, sz*radius);
			verts[n*3+2] = new Point3f(ex*radius, height/2.0f, ez*radius);

			norms[n*3+0] = new Vector3f(0.0f, 1.0f, 0.0f); 
			norms[n*3+1] = new Vector3f(0.0f, 1.0f, 0.0f);
			norms[n*3+2] = new Vector3f(0.0f, 1.0f, 0.0f);
			
			texCoords[n*3+0] = new Point2f(0.5f, 0.5f);
			texCoords[n*3+1] = new Point2f(cosValueToTexCoordX(sx), sinValueToTexCoordY(sz));
			texCoords[n*3+2] = new Point2f(cosValueToTexCoordX(ex), sinValueToTexCoordY(ez));
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
	}

	private void createBottom(CylinderNode cylinder, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	height = cylinder.getHeight();
		float	radius = cylinder.getRadius();
		
		for (int n=0; n<nDivide; n++) {
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			
			verts[n*3+0] = new Point3f(0.0f, -height/2.0f, 0.0f);
			verts[n*3+1] = new Point3f(sx*radius, -height/2.0f, sz*radius);
			verts[n*3+2] = new Point3f(ex*radius, -height/2.0f, ez*radius);

			norms[n*3+0] = new Vector3f(0.0f, -1.0f, 0.0f); 
			norms[n*3+1] = new Vector3f(0.0f, -1.0f, 0.0f);
			norms[n*3+2] = new Vector3f(0.0f, -1.0f, 0.0f);
			
			texCoords[n*3+0] = new Point2f(0.5f, 0.5f);
			texCoords[n*3+1] = new Point2f(cosValueToTexCoordX(sx), sinValueToTexCoordY(sz));
			texCoords[n*3+2] = new Point2f(cosValueToTexCoordX(ex), sinValueToTexCoordY(ez));
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
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
