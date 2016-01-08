package com.gmail.trentech.RetroMC.Managers;

import java.io.File;
import java.io.IOException;

import org.spongepowered.api.entity.living.player.Player;

import com.gmail.trentech.RetroMC.Main;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ConfigManager {

	private File file;
	private CommentedConfigurationNode config;
	private ConfigurationLoader<CommentedConfigurationNode> loader;
	private Player player;

	public ConfigManager(Player player) {
		this.player = player;
		String folder = "config/retromc/players/";
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		file = new File(folder, player.getUniqueId().toString() + ".conf");
		
		create();
		load();
		init();
	}

	public ConfigManager() {
		String folder = "config/retromc/";
        if (!new File(folder).isDirectory()) {
        	new File(folder).mkdirs();
        }
		file = new File(folder, "config.conf");
		
		create();
		load();
		init();
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
		if(file.getName().equalsIgnoreCase("config.conf")){
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
		}else if(player != null){
			config.getNode("Readable-Name").setValue(player.getName());
		  
		    if(config.getNode("Lives").getString() == null) {
		    	config.getNode("Lives").setValue(new ConfigManager().getConfig().getNode("Lives").getInt());
		    }
		}

        save();
	}

	private void create(){
		if(!file.exists()) {
			try {
				Main.getLog().info("Creating new " + file.getName() + " file...");
				file.createNewFile();		
			} catch (IOException e) {				
				Main.getLog().error("Failed to create new config file");
				e.printStackTrace();
			}
		}
	}
	
	private void load(){
		loader = HoconConfigurationLoader.builder().setFile(file).build();
		try {
			config = loader.load();
		} catch (IOException e) {
			Main.getLog().error("Failed to load config");
			e.printStackTrace();
		}
	}

}
