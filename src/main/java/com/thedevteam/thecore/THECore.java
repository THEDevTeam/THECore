package com.thedevteam.thecore;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.spout.api.plugin.CommonPlugin;

import com.thedevteam.thecore.parts.PartRegister;

/**
 * Core class
 *
 */
public class THECore extends CommonPlugin{
	
    public PartRegister pr;
    public static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onEnable() {
    	pr = new PartRegister(this);
    	pr.enableAllParts();
    }

    @Override
    public void onDisable() {
    }

    public void log(Level info, String string) {
        log.log(info, string);
    }
}
