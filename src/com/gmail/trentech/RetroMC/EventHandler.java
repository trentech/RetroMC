package com.gmail.trentech.RetroMC;

import java.util.HashMap;
import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.RetroMC.Data.PlayerData;
import com.gmail.trentech.RetroMC.Managers.ConfigManager;
import com.gmail.trentech.RetroMC.Managers.RetroManager;

public class EventHandler {

	HashMap<Player,Inventory> hash = new HashMap<>();
	
	@Listener
	public void onClientConnectionEvent(ClientConnectionEvent.Join event, @First Player player){
		Optional<PlayerData> optionalplayerDara = player.get(PlayerData.class);
		
		if(optionalplayerDara.isPresent()){
			return;
		}
		
		player.offer(new PlayerData());
	}
	
	@Listener
	public void onPlayerDeath(DestructEntityEvent.Death event) {
		if(!(event.getTargetEntity() instanceof Player)){
			return;
		}
		Player player = (Player) event.getTargetEntity();

        if(!player.hasPermission("retro.enable")) {
        	return;    	
        }

        PlayerData playerData = player.get(PlayerData.class).get();

    	int lives = playerData.lives().get() - 1;
    	
    	if(lives <= 0) {
    		playerData.lives().set(new ConfigManager().getConfig().getNode("Lives").getInt());

    		player.sendMessage(Text.of(TextColors.DARK_RED, "Game Over!"));
    		RetroManager.resetPlayer(player);
    	}else{
    		playerData.lives().set(lives);
    		
    		player.sendMessage(Text.of(TextColors.YELLOW, "Lives: ", lives));
    		hash.put(player, player.getInventory());
    	} 
    	player.offer(playerData);
	}
	
	@Listener
	public void onDropItemEvent(DropItemEvent.Destruct event, @First Player player) {
        if(!player.hasPermission("retro.enable")) {
        	return;    	
        }
        
    	event.setCancelled(true);	
	}
}
