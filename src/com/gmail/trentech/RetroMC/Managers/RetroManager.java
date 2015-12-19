package com.gmail.trentech.RetroMC.Managers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.manipulator.mutable.entity.AchievementData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.data.manipulator.mutable.entity.TradeOfferData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.merchant.TradeOffer;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.ban.Ban.User;

import com.gmail.trentech.RetroMC.Main;
import com.gmail.trentech.RetroMC.Utils.Utils;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class RetroManager {

	public static String getBanTimeRemaining(Player player){
		ConfigManager configManager = new ConfigManager(player);
		CommentedConfigurationNode config = configManager.getConfig();
		
		String formattedDate = config.getNode("Time").getString();

		Date date = new Date();
		Date expirationDate = null;
		try {
			expirationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formattedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	    if (expirationDate.before(new Date())) {
			long compare = TimeUnit.MILLISECONDS.toSeconds(date.getTime() - expirationDate.getTime());
			long time = Utils.getTimeInSeconds(new ConfigManager().getConfig().getNode("Ban", "Time").getString());

			return Utils.getReadableTime(time - compare); 
	    }
		return null;
	}
	
	public static void wipePlayerData(Player player){
		deletePlayerData(player);
		deleteMoney(player);
		blockRollback(player);
		setBan(player);	
	}
	
	private static void deletePlayerData(Player player){
		if(player.get(ExperienceHolderData.class).isPresent()){
			ExperienceHolderData data = player.get(ExperienceHolderData.class).get();
			data.level().set(0);
		}
		
		if(player.get(AchievementData.class).isPresent()){
			AchievementData data = player.get(AchievementData.class).get();
			data.getValues().clear();
		}
		
		if(player.get(TradeOfferData.class).isPresent()){
			TradeOfferData data = player.get(TradeOfferData.class).get();
			data.tradeOffers().set(new ArrayList<TradeOffer>());
		}

	}
	
	private static void blockRollback(Player player){
		
	}
	
	private static void deleteMoney(Player player){
		if(Main.hasEconomy()){
			if(new ConfigManager().getConfig().getNode("Zero-Balance").getBoolean()){
				EconomyManager.setBalance(player.getUniqueId().toString(), null, 0);
			}
		}
	}

	private static void setBan(Player player){
    	ConfigManager playerConfigManager = new ConfigManager(player);
    	CommentedConfigurationNode playerConfig = playerConfigManager.getConfig();
    	CommentedConfigurationNode config = new ConfigManager().getConfig();
    	
    	if(config.getNode("Ban", "Enabled").getBoolean()){
    		playerConfig.getNode("Banned").setValue(true);
    		playerConfigManager.save();
    		
    		if(!config.getNode("Ban", "Time").getString().equalsIgnoreCase("0")){
    			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			Date date = new Date();
    			String strDate = dateFormat.format(date).toString();
	
    			playerConfig.getNode("Time").setValue(strDate);
    			playerConfigManager.save();
    		}

    		String remaining = RetroManager.getBanTimeRemaining(player);
    		if(remaining != null){
    			player.kick(Texts.of("Game Over!\nTemporarily Banned\n", remaining));
    		}else{
    			//Main.getGame().getRegistry().createBuilder(BanBuilder.class).reason(Texts.of("Game Over\nPermanently Banned!")).type(BanType.USER_BAN).user(player).build();
    			player.getBanData().bans().add((User)player);
            	player.kick(Texts.of("Game Over\nPermanently Banned!"));
            }
    	}
	}
}