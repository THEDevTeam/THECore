package com.thedevteam.thecore.parts;

import java.util.List;

import com.thedevteam.thecore.THECore;

public abstract class Part {

	private String name;
	private THECore tim;
	private List<String> dependencies;

	  public Part(THECore tim, String name) {
		    this.name = name;
		    this.tim = tim;
		  }
	
	public String getName() {
		return name;
	}

	public abstract void init() ;
	
	public List<String> getDependencies(){
		return dependencies;
	}

}
