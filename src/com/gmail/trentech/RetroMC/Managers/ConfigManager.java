package com.gmail.trentech.RetroMC.Managers;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.spongepowered.api.entity.living.player.Player;

import com.gmail.trentech.RetroMC.Main;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {

	private File file;
	private CommentedConfigurationNode config;
	private ConfigurationLoader<CommentedConfigurationNode> loader;
	private boolean defaultConfig = false;
	private Player player;
	
	public ConfigManager(String folder, String fileName) {
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		file = new File(folder, fileName);
		
		create();
		load();

	}
	
	public ConfigManager(Player player) {
		this.player = player;
        if (!new File("config/RetroMC/Players/").isDirectory()) {
        	new File("config/RetroMC/Players/").mkdirs();
        }
		file = new File("config/RetroMC/Players/", player.getUniqueId().toString() + ".conf");
		
		create();
		load();
	}
	
	public ConfigManager() {
		defaultConfig = true;
		
        if (!new File("config/RetroMC/").isDirectory()) {
        	new File("config/RetroMC/").mkdirs();
        }
		file = new File("config/RetroMC/", "config.conf");
		
		create();
		load();
	}
	
	public ConfigurationLoader<CommentedConfigurationNode> getLoader() {
		return loader;
	}

	public CommentedConfigurationNode getConfig() {
		return config;
	}

	public void save(){
		try {
			loader.save(config);
		} catch (IOException e) {
			Main.getLog().error("Config save FAIL:");
			e.printStackTrace();
		}
	}
	
	public void init() {
		CommentedConfigurationNode config = getConfig();
		if(defaultConfig){

	        if(config.getNode("Ban", "Enabled").getString() == null) {
	        	config.getNode("Ban", "Enabled").setValue(false)
	        	.setComment("Enable Ban when run out of lives");
	        }  
	        if(config.getNode("Ban", "Time").getString() == null) {
	        	config.getNode("Ban", "Time").setValue(0)
	        	.setComment("Set a temporary ban but entering a time duration. 0 to disable, Ex. [1y 3mo 2w 1d 4h 5m 10s]");
	        }  
	        if(config.getNode("Lives").getString() == null) {
	        	config.getNode("Lives").setValue(3)
	        	.setComment("Default lives a player begins with.");
	        }
	        if(config.getNode("Zero-Balance").getString() == null) {
	        	config.getNode("Zero-Balance").setValue(true)
	        	.setComment("Set balance to 0 if economy support is available");
	        }
		}else{
			config.getNode("Readable-Name").setValue(player.getName());
		  
		    if(config.getNode("Lives").getString() == null) {
		    	config.getNode("Lives").setValue(new ConfigManager().getConfig().getNode("Lives").getInt());
		    }
		    if(config.getNode("Banned").getString() == null) {
		    	config.getNode("Banned").setValue(false);	
		    }
		    if(config.getNode("Time").getString() == null){
    			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			Date date = new Date();
		    	config.getNode("Time").setValue(dateFormat.format(date).toString());
		    }
		}

        save();
	}

	private void create(){
		if(!file.exists()) {
			try {
				file.createNewFile();		
			} catch (IOException e) {				
				Main.getLog().error("Config create FAIL:");
				e.printStackTrace();
			}
		}
	}
	
	private void load(){
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = loader.load();
		} catch (IOException e) {
			Main.getLog().error("Config load FAIL:");
			e.printStackTrace();
		}
	}

}
