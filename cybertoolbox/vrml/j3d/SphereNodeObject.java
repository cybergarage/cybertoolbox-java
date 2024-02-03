/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : SphereNodeObject.java
*
******************************************************************/

package vrml.j3d;

import javax.media.j3d.*;
import javax.vecmath.*;
import vrml.node.*;
import vrml.field.*;

public class SphereNodeObject extends TriangleArray implements NodeObject {

	public SphereNodeObject(SphereNode node) {
		super(getVertexCount(node), COORDINATES | NORMALS | TEXTURE_COORDINATE_2);
		createSphere(node);
	}
	
	static private int getNDivide() {
		return 20;
	}
	
	static private int getVertexCount(SphereNode sphere) {
		int count = 0;
/*		
		count += getNDivide();
		count += (getNDivide() * 2) * (getNDivide() -2);
		count += getNDivide();
*/
		count = (getNDivide() * 2) * getNDivide();
		
		count *= 3;
		
		return count;
	}
	
	public boolean initialize(vrml.node.Node node) {
		return true;
	}

	private void createSphere(SphereNode sphere) {
		int offset = 0;
//		createTop(sphere, getNDivide(), offset);	offset += getNDivide() * 3;
//		for (int n=1; n<getNDivide()-1; n++)  {
		for (int n=0; n<getNDivide(); n++)  {
			createSide(sphere, getNDivide(), n, offset);	
			offset += getNDivide() * 3 * 2;
		}
//		createBottom(sphere, getNDivide(), offset);
	}

	private void createSide(SphereNode sphere, int nDivide, int nSide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3*2];
		Vector3f norms[]	= new Vector3f[nDivide*3*2];
		Point2f texCoords[]	= new Point2f[nDivide*3*2];
		
		float	radius		= sphere.getRadius();
		
		float	startDivAngle	= (float)Math.PI/(float)nDivide * (float)nSide;
		float	startYPos		= (float)Math.cos(startDivAngle);
		float	startYRadius	= startYPos * radius;
		float	startXRadius	= (float)Math.sin(startDivAngle) * radius;

		float	endDivAngle		= (float)Math.PI/(float)nDivide * (float)(nSide+1);
		float	endYPos			= (float)Math.cos(endDivAngle);
		float	endYRadius		= endYPos * radius;
		float	endXRadius		= (float)Math.sin(endDivAngle) * radius;

		for (int n=0; n<nDivide; n++) {
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);

			verts[n*6+0] = new Point3f(sx*startXRadius, startYRadius, sz*startXRadius);
			verts[n*6+1] = new Point3f(ex*startXRadius, startYRadius, ez*startXRadius);
			verts[n*6+2] = new Point3f(sx*endXRadius, endYRadius, sz*endXRadius);

			norms[n*6+0] = new Vector3f(sx, startYPos, sz); norms[n*6+0].normalize();
			norms[n*6+1] = new Vector3f(ex, startYPos, ez); norms[n*6+1].normalize();
			norms[n*6+2] = new Vector3f(sx, endYPos, sz);	norms[n*6+2].normalize();
			
			texCoords[n*6+0] = new Point2f((float)(n+0)/(float)nDivide, (startYPos+1.0f)/2.0f);
			texCoords[n*6+1] = new Point2f((float)(n+1)/(float)nDivide, (startYPos+1.0f)/2.0f);
			texCoords[n*6+2] = new Point2f((float)(n+0)/(float)nDivide, (endYPos+1.0f)/2.0f);

			verts[n*6+3] = new Point3f(ex*startXRadius, startYRadius, ez*startXRadius);
			verts[n*6+4] = new Point3f(ex*endXRadius, endYRadius, ez*endXRadius);
			verts[n*6+5] = new Point3f(sx*endXRadius, endYRadius, sz*endXRadius);

			norms[n*6+3] = new Vector3f(ex, startYPos, ez); norms[n*6+3].normalize();
			norms[n*6+4] = new Vector3f(ex, endYPos, ez);	norms[n*6+4].normalize();
			norms[n*6+5] = new Vector3f(sx, endYPos, sz);	norms[n*6+5].normalize();
			
			texCoords[n*6+3] = new Point2f((float)(n+1)/(float)nDivide, (startYPos+1.0f)/2.0f);
			texCoords[n*6+4] = new Point2f((float)(n+1)/(float)nDivide, (endYPos+1.0f)/2.0f);
			texCoords[n*6+5] = new Point2f((float)(n+0)/(float)nDivide, (endYPos+1.0f)/2.0f);
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
	}

	private void createTop(SphereNode sphere, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	radius		= sphere.getRadius();
		float	divAngle	= (float)Math.PI/(float)nDivide;
		float	ypos		= (float)Math.cos(divAngle);
		float	yradius		= ypos * radius;
		float	xradius		= (float)Math.sin(divAngle) * radius;

		for (int n=0; n<nDivide; n++) {
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			
			verts[n*3+0] = new Point3f(0.0f, radius, 0.0f);
			verts[n*3+1] = new Point3f(sx*xradius, yradius, sz*xradius);
			verts[n*3+2] = new Point3f(ex*xradius, yradius, ez*xradius);

			norms[n*3+0] = new Vector3f(0.0f, 1.0f, 0.0f);	norms[n*3+0].normalize();
			norms[n*3+1] = new Vector3f(sx, ypos, sz);		norms[n*3+1].normalize();
			norms[n*3+2] = new Vector3f(ex, ypos, ez);		norms[n*3+2].normalize();
			
			texCoords[n*3+0] = new Point2f(sx, 0.0f);
			texCoords[n*3+1] = new Point2f(sx, ypos);
			texCoords[n*3+2] = new Point2f(ex, ypos);
		}
		
		setCoordinates(offset, verts);
		setNormals(offset, norms);
		setTextureCoordinates(offset, texCoords);
	}

	private void createBottom(SphereNode sphere, int nDivide, int offset) {
		Point3f verts[]		= new Point3f[nDivide*3];
		Vector3f norms[]	= new Vector3f[nDivide*3];
		Point2f texCoords[]	= new Point2f[nDivide*3];
		
		float	radius		= sphere.getRadius();
		float	divAngle	= (float)Math.PI/(float)nDivide * (nDivide-1);
		float	ypos		= (float)Math.cos(divAngle);
		float	yradius		= ypos * radius;
		float	xradius		= (float)Math.sin(divAngle) * radius;

		for (int n=0; n<nDivide; n++) {
			float startAngle	= ((float)Math.PI*2.0f/(float)nDivide) * (float)n;
			float endAngle		= ((float)Math.PI*2.0f/(float)nDivide) * (float)(n+1);
			
			float sx = (float)Math.cos(startAngle);
			float sz = (float)Math.sin(startAngle);
			float ex = (float)Math.cos(endAngle);
			float ez = (float)Math.sin(endAngle);
			
			verts[n*3+0] = new Point3f(0.0f, radius, 0.0f);
			verts[n*3+1] = new Point3f(sx*xradius, yradius, sz*xradius);
			verts[n*3+2] = new Point3f(ex*xradius, yradius, ez*xradius);

			norms[n*3+0] = new Vector3f(0.0f, -1.0f, 0.0f);	norms[n*3+0].normalize();
			norms[n*3+1] = new Vector3f(sx, ypos, sz);		norms[n*3+1].normalize();
			norms[n*3+2] = new Vector3f(ex, ypos, ez);		norms[n*3+2].normalize();
			
			texCoords[n*3+0] = new Point2f(sx, 1.0f);
			texCoords[n*3+1] = new Point2f(sx, ypos);
			texCoords[n*3+2] = new Point2f(ex, ypos);
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
	
	private void setBounds(Shape3D shape3DNode, SphereNode sphereNode) {
System.out.println("Shape3d getBoundsAutoCompute : " + shape3DNode.getBoundsAutoCompute());
		Point3d center = new Point3d(0.0, 0.0, 0.0);
		BoundingSphere bounds = new BoundingSphere(center, 0);//sphereNode.getRadius());
		shape3DNode.setBoundsAutoCompute(false);
		shape3DNode.setBounds(bounds);
	}
	
	public boolean add(vrml.node.Node node) {

		vrml.node.Node parentNode = node.getParentNode();
		if (parentNode != null) {
			if (parentNode.isShapeNode() == true) {
				NodeObject parentNodeObject  = parentNode.getObject();
				if (parentNodeObject != null) {
					Shape3D parentShape3DNode = (Shape3D)parentNodeObject;
					parentShape3DNode.setGeometry(this);
					/*
					SphereNode sphereNode = (SphereNode)node;
					setBounds(parentShape3DNode, sphereNode);
					*/
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
