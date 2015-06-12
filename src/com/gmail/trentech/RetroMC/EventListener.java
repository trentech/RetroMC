package com.gmail.trentech.RetroMC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ninja.leaping.configurate.ConfigurationNode;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerDeathEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.Texts;

import com.gmail.trentech.RetroMC.Utils.ConfigLoader;
import com.gmail.trentech.RetroMC.Utils.Notifications;
import com.gmail.trentech.RetroMC.Utils.Utils;

public class EventListener {

	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getUser();
    	ConfigLoader pLoader = new ConfigLoader("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigurationNode playerConfig = pLoader.getConfig();
        playerConfig.getNode("Readable-Name").setValue(player.getName());
      
        if(playerConfig.getNode("Lives").getString() == null) {
        	playerConfig.getNode("Lives").setValue(new ConfigLoader().getConfig().getNode("Default-Lives").getInt());
        }
        if(playerConfig.getNode("Banned").getString() == null) {
        	playerConfig.getNode("Banned").setValue(false);	
        }
        if(playerConfig.getNode("Time").getString() == null){
        	playerConfig.getNode("Time").setValue(0);
        }
        pLoader.saveConfig();
        
        if(!player.hasPermission("RetroMC.enable")) {
        	return;
        }
        
    	ConfigurationNode config = new ConfigLoader().getConfig();
    	
        if(!config.getNode("config", "Ban", "Enabled").getBoolean()) {
        	return;
        }
        
        if(playerConfig.getNode("Banned").getBoolean()) {
        	
        	if(config.getNode("config", "Ban", "Temp-Ban").getBoolean()) {
        		String pDate = playerConfig.getNode("Time").getString();
        		Date date = new Date();
        		Date playerDate = null;
        		try {
        			playerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(pDate);
        			
        		} catch (ParseException e) {
        			e.printStackTrace();
        		}      		
        		long compare = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - playerDate.getTime());
        		long time = Utils.getTimeInSeconds(config.getNode("config", "Ban", "Time").getString());
        		if(!(time - compare <= 0)) {		
        			Notifications notify = new Notifications("Temp-Ban", Utils.getReadableTime((time - compare)), null, null);
        			player.kick(Texts.of(notify.getMessage()));
        		}else{
        			playerConfig.getNode("Banned").setValue(false);
        			playerConfig.getNode("Time").setValue(0);
        			pLoader.saveConfig();
        		}
        	}else{
        		player.kick(Texts.of("Game Over!"));
        	}	
        }
   
	}
	
	@Subscribe
	public void onPlayerJoin(PlayerDeathEvent event) {
		Player player = event.getUser();
        if(!player.hasPermission("RetroMC.enable")) {
        	return;
        }
        
    	ConfigLoader pLoader = new ConfigLoader("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigurationNode playerConfig = pLoader.getConfig();
    	
    	int lives = playerConfig.getNode("Lives").getInt();
    	if((lives -1) <= 0) {
    		player.getInventory().clear();
    		playerConfig.getNode("Lives").setValue(new ConfigLoader().getConfig().getNode("Default-Lives").getInt());
    		pLoader.saveConfig();
    	}else{
    		playerConfig.getNode("Lives").setValue(lives - 1);
			Notifications notify = new Notifications("Player-Lives", null, null, Integer.toString(lives - 1));
    		player.sendMessage(notify.getMessage());
    	}
        
	}
	
}
