package com.gmail.trentech.RetroMC;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Managers.ConfigManager;
import com.gmail.trentech.RetroMC.Managers.RetroManager;

import ninja.leaping.configurate.ConfigurationNode;

public class EventHandler {

	@Listener
	public void onPlayerDeath(DestructEntityEvent.Death event) {
		if(!(event.getTargetEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getTargetEntity();

        if(!player.hasPermission("RetroMC.enable")) {
        	return;    	
        }

    	ConfigManager playerConfigManager = new ConfigManager(player);
    	ConfigurationNode playerConfig = playerConfigManager.getConfig();
    	
    	int lives = playerConfig.getNode("Lives").getInt() - 1;
    	if(lives <= 0) {
    		//CLEAR DROPS
    		playerConfig.getNode("Lives").setValue(new ConfigManager().getConfig().getNode("Lives").getInt());
    		playerConfigManager.save();
    		player.sendMessage(Texts.of(TextColors.DARK_RED, "Game Over!"));
    		RetroManager.resetPlayer(player);
    	}else{
    		playerConfig.getNode("Lives").setValue(lives);
    		playerConfigManager.save();
    		player.sendMessage(Texts.of(TextColors.YELLOW, "Lives: ", lives));
    	}   	
	}
}
