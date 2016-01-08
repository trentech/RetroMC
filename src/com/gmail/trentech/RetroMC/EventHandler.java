package com.gmail.trentech.RetroMC;

import java.util.HashMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Managers.ConfigManager;
import com.gmail.trentech.RetroMC.Managers.RetroManager;

import ninja.leaping.configurate.ConfigurationNode;

public class EventHandler {

	HashMap<Player,Inventory> hash = new HashMap<>();
	
	@Listener
	public void onPlayerDeath(DestructEntityEvent.Death event) {
		if(!(event.getTargetEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getTargetEntity();

        if(!player.hasPermission("retro.enable")) {
        	return;    	
        }

    	ConfigManager playerConfigManager = new ConfigManager(player);
    	ConfigurationNode playerConfig = playerConfigManager.getConfig();
    	
    	int lives = playerConfig.getNode("Lives").getInt() - 1;
    	if(lives <= 0) {
    		playerConfig.getNode("Lives").setValue(new ConfigManager().getConfig().getNode("Lives").getInt());
    		playerConfigManager.save();
    		player.sendMessage(Text.of(TextColors.DARK_RED, "Game Over!"));
    		RetroManager.resetPlayer(player);
    	}else{
    		playerConfig.getNode("Lives").setValue(lives);
    		playerConfigManager.save();
    		player.sendMessage(Text.of(TextColors.YELLOW, "Lives: ", lives));
    		hash.put(player, player.getInventory());
    	}   	
	}
	
	@Listener
	public void onDropItemEvent(DropItemEvent.Destruct event, @First Player player) {
        if(!player.hasPermission("retro.enable")) {
        	return;    	
        }
        
    	event.setCancelled(true);	
	}
}
