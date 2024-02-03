/******************************************************************
*
*	VRML Library for Java
*
*	Copyright (C) Satoshi Konno 1997-1998
*
*	File : ScriptClassLoder.java
*
******************************************************************/

package vrml.node;

import java.lang.ClassLoader;
import java.net.URL;
import java.net.URLConnection;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import vrml.util.Debug;

public class ScriptClassLoader extends ClassLoader {

	public synchronized Class loadClass(String classname, boolean resolve) throws ClassNotFoundException {
		Class c = null;
		try {
			c=Class.forName(classname);
		} catch (java.lang.ClassNotFoundException e) {}
		return super.loadClass(classname, resolve);
	}
	
	public synchronized Class loadClass(String classname) throws ClassNotFoundException {
		return loadClass(classname, true);
	}

	public synchronized Class loadClass(DataInputStream dis, String classname) throws ClassNotFoundException {
		try {
			byte buffer[] = new byte[dis.available()];
			dis.readFully(buffer);
			return defineClass(classname, buffer, 0, buffer.length);
   	} catch (java.io.IOException e) {}
   	throw new ClassNotFoundException();
 	}
 	
	public synchronized Class loadClass(String dir, String classname) throws ClassNotFoundException {
		try {
			File file = new File(dir, classname + ".class");
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			return loadClass(dis, classname);
   	} catch (java.io.IOException e) {
   		Debug.message("ScriptClassLoader::loadClass = " + dir  + ", " + classname);
   	}
   	throw new ClassNotFoundException();
 	}
 	
	public synchronized Class loadClass(URL url, String classname) throws ClassNotFoundException {
		try {
			URLConnection urlc = url.openConnection();
			urlc.connect();
			DataInputStream dis = new DataInputStream(urlc.getInputStream());
			return loadClass(dis, classname);
   	} catch (java.io.IOException e) {
   		Debug.message("ScriptClassLoader::loadClass = " + url  + ", " + classname);
   	}
   	throw new ClassNotFoundException();
 	}
}