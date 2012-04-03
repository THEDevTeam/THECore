package com.thedevteam.thecore.parts;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;


import com.thedevteam.thecore.THECore;
import com.thedevteam.thecore.utils.Downloader;

public class PartRegister {

	
	private final THECore tc;
	  private final File dir;
	  private Map<String, Part> Parts;

	  public PartRegister(THECore theCore)
	  {
	    tc = theCore;
	    Parts = new HashMap<String, Part>();
	    dir = new File(tc.getDataFolder(), "Parts");
	    dir.mkdirs();
	    
	    
	    for (String file : dir.list()) {
	      if (!file.contains(".jar")) continue;
	      File f = new File(dir, file);
	      String partName = file.replace(".jar", "");
	      if(isLoaded(partName))continue;
	     Part p = loadPart(f);
	     if(p !=null){
	    	 if(resolveDependencies(p)){
	    		 addPart(p);
	    	 }
	     }
	      
	    }
	    
	    tc.log(Level.INFO, "All Parts loaded. Active Parts: " + Parts.keySet());
	  }

	  public Map<String, Part> getActiveParts()
	  {
	    return Parts;
	  }

	  public Part getKioPack(String s) {
	    for (Part p : getActiveParts().values()) {
	      if (s.equalsIgnoreCase(p.getName())) return p;
	    }
	    return null;
	  }


	  private boolean resolveDependencies(Part p) {
		List<String> deps = p.getDependencies();
		for (String dep:deps){
			// check to see if already loaded
			if(isLoaded(dep))continue;
			//check if part is in directory
			File f = null;
			for (String file : dir.list()) {
			      if (!file.contains(".jar")) continue;
			      String partName = file.replace(".jar", "");
			      if (!partName.equalsIgnoreCase(dep))continue;
			      f = new File(dir, file);
			      }
			if(f == null){
				f =Downloader.fetch("", dep+".jar"); //TODO: Create Repo
			}
			Part d = loadPart(f);
			if(d !=null){
		    	 if(resolveDependencies(d)){
		    		 addPart(d);
		    	 }else{
		    		 return false;
		    	 }
		     }else{
		    	 return false;
		     }
		}
		return true;
		
	}

	private void addPart(Part p) {

	    Parts.put(p.getName(), p);
	    tc.log(Level.INFO, "Part " + p.getName() + " Loaded");
	  }

	  private Part loadPart(File f) {
	    try {
	      JarFile jarFile = new JarFile(f);
	      Enumeration<JarEntry> entries = jarFile.entries();

	      String mainClass = null;
	      while (entries.hasMoreElements()) {
	        JarEntry element = (JarEntry)entries.nextElement();
	        if (element.getName().equalsIgnoreCase("Pack.yml")) {
	          BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(element)));
	          mainClass = reader.readLine().substring(6);
	          break;
	        }
	      }
	      
	      List<URL> url = new ArrayList<URL>();
	      
	      try {
	          url.add(f.toURI().toURL());
	        } catch (MalformedURLException e) {
	          tc.log(Level.SEVERE, e);
	        }
	      
	      ClassLoader c = tc.getClass().getClassLoader();
		    ClassLoader classLoader = URLClassLoader.newInstance((URL[])url.toArray(new URL[url.size()]), c);
	      if (mainClass != null){ 
	        Class<?> clazz = Class.forName(mainClass, true, classLoader);
	        Class<? extends Part> partClass = clazz.asSubclass(Part.class);
	        Constructor<? extends Part> ctor = partClass.getConstructor(new Class[] { tc.getClass() });
	        Part p = (Part)ctor.newInstance(new Object[] { tc });

	        return p;
	      }
	      throw new Exception();
	    } catch (Exception e) {
	      e.printStackTrace();
	      tc.log(Level.INFO, "Part " + f.getName() + " has failed to load.");
	    }
	    return null;
	  }

	  private boolean isLoaded(String key) {
	    return Parts.containsKey(key.toLowerCase());
	  }

	  public void enableAllParts() {
		  Set<Entry<String, Part>> es = new HashSet<Entry<String, Part>>(Parts.entrySet());
		  while(!es.isEmpty()){
			  Part p = es.iterator().next().getValue();
			  for (String dep: p.getDependencies()){
				  while(es.iterator().hasNext()){
					  Entry<String, Part> d = es.iterator().next();
					  if (dep.equalsIgnoreCase(d.getKey()))d.getValue().init(); 
				  }
			  }
			  p.init();
		  }

	  }
}
