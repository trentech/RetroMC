package com.gmail.trentech.RetroMC.Utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigLoader {

	File file;
	CommentedConfigurationNode config;
	ConfigurationLoader<CommentedConfigurationNode> loader;
	
	public ConfigLoader(String folder, String fileName) {
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		this.file = new File(folder, fileName);
		create();
	}
	
	public ConfigLoader() {
        if (!new File("config/RetroMC/").isDirectory()) {
        	new File("config/RetroMC/").mkdirs();
        }
		this.file = new File("config/RetroMC/", "config.conf");
		create();
	}
	
	public CommentedConfigurationNode getConfig() {
    	loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public boolean saveConfig() {
		if(config == null) {
			getConfig();
		}
		try {
			loader.save(config);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getGlobal().severe("Failed to save Config!");
			return false;
		}
	}
	
	private void create(){
		if(!file.exists()) {
			try {
				file.createNewFile();		
			} catch (IOException e) {
				e.printStackTrace();
				Utils.getLogger().error("Failed to create config file!");
			}
		}
	}
	
	public void initConfig() {		

		CommentedConfigurationNode config = getConfig();
        if(config.getNode("config", "Ban", "Temp-Ban").getString() == null) {
        	config.getNode("config", "Ban", "Temp-Ban").setValue(false)
        	.setComment("New players will receive this kit when first joining the server.");
        }
        if(config.getNode("config", "Ban", "Enabled").getString() == null) {
        	config.getNode("config", "Ban", "Enabled").setValue(false)
        	.setComment("Enable Banning!");
        }  
        if(config.getNode("config", "Ban", "Time").getString() == null) {
        	config.getNode("config", "Ban", "Time").setValue(0)
        	.setComment("Temp Ban Time");
        }  
        if(config.getNode("config", "Lives").getString() == null) {
        	config.getNode("config", "Lives").setValue(3)
        	.setComment("Default lives a player has.");
        }
        
        if(config.getNode("messages", "Permission-Denied").getString() == null) {
        	config.getNode("messages", "Permission-Denied").setValue("&4You do not have permission!");
        }
        if(config.getNode("messages", "Temp-Ban").getString() == null) {
        	config.getNode("messages", "Temp-Ban").setValue("You've been temp banned for %T");
        }
        if(config.getNode("messages", "Ban").getString() == null) {
        	config.getNode("messages", "Ban").setValue("Game Over!");
        }
        if(config.getNode("messages", "Player-Reset").getString() == null) {
        	config.getNode("messages", "Player-Reset").setValue("&eReset %P's lives!");
        }
        if(config.getNode("messages", "Player-Lives").getString() == null) {
        	config.getNode("messages", "Player-Lives").setValue("&eLives: %L");
        }
        if(config.getNode("messages", "Not-Player").getString() == null) {
        	config.getNode("messages", "Not-Player").setValue("&4You must be a player to run this command!");
        }
        if(config.getNode("messages", "No-Player").getString() == null) {
        	config.getNode("messages", "No-Player").setValue("&4Player is offline or does not exist!");
        }
        if(config.getNode("messages", "Invalid-Number").getString() == null) {
        	config.getNode("messages", "Invalid-Number").setValue("&4Invalid number format!");
        }
        if(config.getNode("messages", "Invalid-Argument").getString() == null) {
        	config.getNode("messages", "Invalid-Argument").setValue("&4Invalid argument!");
        }    

        saveConfig();
	}

}
