package com.gmail.trentech.RetroMC;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ninja.leaping.configurate.ConfigurationNode;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.text.Texts;

import com.gmail.trentech.RetroMC.Utils.ConfigLoader;
import com.gmail.trentech.RetroMC.Utils.Notifications;
import com.gmail.trentech.RetroMC.Utils.Utils;

public class EventListener {

	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getUser();
		
        File playerConfigFile = new File("config/RetroMC/Players", player.getUniqueId().toString() + ".conf");
    	ConfigLoader pLoader = new ConfigLoader(playerConfigFile);
    	ConfigurationNode playerConfig = pLoader.getConfig();
        playerConfig.getNode("Readable-Name").setValue(player.getName());
        
        if(playerConfig.getNode("Lives").getString() == null){
        	playerConfig.getNode("Lives").setValue(new ConfigLoader(new File("config/RetroMC/config.conf")).getConfig().getNode("Default-Lives").getInt());
        }
        if(playerConfig.getNode("Banned").getString() == null){
        	playerConfig.getNode("Banned").setValue(false);	
        }
        if(playerConfig.getNode("Time").getString() == null){
        	playerConfig.getNode("Time").setValue(0);
        }
        pLoader.saveConfig();
        
    	ConfigurationNode config = new ConfigLoader(new File("config/RetroMC/config.conf")).getConfig();
    	
        if(!config.getNode("config", "Ban", "Enabled").getBoolean()){
        	return;
        }
        
        if(playerConfig.getNode("Banned").getBoolean()){
        	
        	if(config.getNode("config", "Ban", "Temp-Ban").getBoolean()){
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
        		if(!(time - compare <= 0)){		
        			Notifications notify = new Notifications("Temp-Ban", Utils.getReadableTime((time - compare)), null);
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
	
}
