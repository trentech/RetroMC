package com.gmail.trentech.RetroMC.Utils;

import java.io.File;

import ninja.leaping.configurate.ConfigurationNode;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;

import com.gmail.trentech.RetroMC.RetroMC;

public class Notifications {
	
	private String type;
	private String playerName;
	private String time;
	
	public Notifications() {
		
	}
	
	public Notifications(String type, String playerName, String time) {
		this.type = type;
		this.playerName = playerName;
		this.time = time;
	}
	
	public void getMessages() {
    	ConfigurationNode config = new ConfigLoader(new File("config/RetroMC/config.conf")).getConfig();

		RetroMC.messages.put("Permission-Denied", config.getNode("messages", "Permission-Denied").getString());
		RetroMC.messages.put("Not-Player", config.getNode("messages", "Not-Player").getString());
		RetroMC.messages.put("No-Player", config.getNode("messages", "No-Player").getString());
		RetroMC.messages.put("Invalid-Number", config.getNode("messages", "Invalid-Number").getString());
		RetroMC.messages.put("Invalid-Argument", config.getNode("messages", "Invalid-Argument").getString());
		RetroMC.messages.put("Ban", config.getNode("messages", "Ban").getString());
		RetroMC.messages.put("Temp-Ban", config.getNode("messages", "Temp-Ban").getString());
		RetroMC.messages.put("Player-Reset", config.getNode("messages", "Player-Reset").getString());
	}
	
	public Text getMessage() {
		String msg = null;
		if(RetroMC.messages.get(type) != null) {
			msg = RetroMC.messages.get(type);
			if(msg.contains("%P")) {
				msg = msg.replaceAll("%P", playerName);
			}
			if(msg.contains("%T")) {
				msg = msg.replaceAll("%T", time);
			}
		}else{
			throw new NullPointerException("Message Missing from Config!");
		}
		
		Text message = Texts.of(Utils.translateColorCodes(msg));
		return message;
	}
}
