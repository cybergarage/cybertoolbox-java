/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ImageTextureLoader.java
*
******************************************************************/

package vrml.j3d;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.*;

import javax.media.j3d.*;

import vrml.*;
import vrml.node.*;
import vrml.field.*;
import vrml.util.*;
import vrml.util.Debug;

public class ImageTextureLoader extends Object {
	
	public ImageTextureLoader(ImageTextureNode imgTex, Component comp) {
		loadImageComponent(imgTex, comp);
	}

	private ImageComponent2D mImageComponent = null;

	private Image getImage(Component comp, String filename) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Image image = toolkit.getImage(filename);
    
		MediaTracker mt = new MediaTracker(comp);
		mt.addImage (image, 0);
		try { mt.waitForAll(); }
		catch (InterruptedException e) {
			Debug.warning("ImageTextureLoader::getImage = " + e.getMessage());
			return null;
		}
		if (mt.isErrorAny()) {
			return null;
		}
		
		return image;
	}
	
	private void loadImageComponent(ImageTextureNode imgTex, Component comp) {
	
		String url = imgTex.getUrl(0);
		Image image = getImage(comp, url);
		if (image == null) {
			SceneGraph sg = imgTex.getSceneGraph();
			if (sg != null) {
				String dir = sg.getDirectory();
				if (dir != null)
					image = getImage(comp, dir + File.separator + url);
			}
		}
		
		if (image == null) {
			UrlFile urlFile = new UrlFile(url);
			if (urlFile.download() == true)
				image = getImage(comp, urlFile.getDownloadedFilename());
			if (image == null) {
				SceneGraph sg = imgTex.getSceneGraph();
				if (sg != null) {
					String sgUrl = sg.getURL();
					if (sgUrl != null) {
						urlFile = new UrlFile(sgUrl + url);
						if (urlFile.download() == true)
							image = getImage(comp, urlFile.getDownloadedFilename());
					}
				}
			}
		}
		
		if (image == null) {
			vrml.util.Debug.warning("Texture (" + url + ") is not found");
			return;
		}
			
		int width = image.getWidth(comp);
		int height = image.getHeight(comp);
    
		// Create imageComponent from image
		mImageComponent = createImageComponent(createBufferedImage(image, comp));
	}

	private ImageComponent2D createImageComponent(BufferedImage bufferedImage) {
		return new ImageComponent2D(ImageComponent.FORMAT_RGBA, bufferedImage);
	}
	
	private BufferedImage createBufferedImage(Image image, Component observer) {
		int status;
		observer.prepareImage(image, null);
		while(true) {
			status = observer.checkImage(image, null);
			if ((status & ImageObserver.ERROR) != 0) {
				Debug.warning("Couldn't load a image in ImageTextureLoader::createBufferedImage");
				return null;
			} else if ((status & ImageObserver.ALLBITS) != 0) {
				break;
			} 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}

		int width = image.getWidth(observer);
		int height = image.getHeight(observer);

		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int[] intPixels = ((DataBufferInt)bImage.getRaster().getDataBuffer()).getData();

		// retrieve image data using PixelGrabber
		PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, intPixels, 0, width);
		try { pg.grabPixels(); }
		catch (InterruptedException e) {;}
		
		return bImage;
	}


	public ImageComponent2D getImageComponent() {
		return mImageComponent;
	}
		
	public int getWidth() {
		return mImageComponent.getWidth();
	}
	
	public int getHeight() {
		return mImageComponent.getHeight();
	}
}
