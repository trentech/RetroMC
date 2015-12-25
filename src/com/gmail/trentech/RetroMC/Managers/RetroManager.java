package com.gmail.trentech.RetroMC.Managers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.manipulator.mutable.entity.AchievementData;
import org.spongepowered.api.data.manipulator.mutable.entity.ExperienceHolderData;
import org.spongepowered.api.data.manipulator.mutable.entity.TradeOfferData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.merchant.TradeOffer;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.Ban.Builder;
import org.spongepowered.api.util.ban.BanTypes;

import com.gmail.trentech.RetroMC.Main;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class RetroManager {

	public static void resetPlayer(Player player){
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
    	CommentedConfigurationNode config = new ConfigManager().getConfig();
    	
    	if(config.getNode("Ban", "Enabled").getBoolean()){
    		Text reason = Texts.of("Game Over!\nPermanently Banned\n");
    		
			UserStorageService userStorage = Main.getGame().getServiceManager().provide(UserStorageService.class).get();
			BanService banService = Main.getGame().getServiceManager().provide(BanService.class).get();
			
			User user = userStorage.get(player.getUniqueId()).get();

			Builder builder = Ban.builder();
			builder.type(BanTypes.PROFILE).reason(reason).profile(user.getProfile());
			
			if(!config.getNode("Ban", "Time").getString().equalsIgnoreCase("0")){
				builder.expirationDate(Instant.now().plusMillis(getTimeInMilliSeconds(config.getNode("Ban", "Time").getString())));
				reason = Texts.of("Game Over!\nTemporarily Banned\n");
			}

			banService.addBan(builder.build());

			player.kick(reason);
    	}
	}
	
	private static long getTimeInMilliSeconds(String time) {
		String[] times = time.split(" ");
		long milliSeconds = 0;
		for(String t : times) {
			if(t.matches("(\\d+)[s]$")) {
				milliSeconds = TimeUnit.SECONDS.toMillis(Integer.parseInt(t.replace("s", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[m]$")) {
				milliSeconds = TimeUnit.MINUTES.toMillis(Integer.parseInt(t.replace("m", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[h]$")) {
				milliSeconds = TimeUnit.HOURS.toMillis(Integer.parseInt(t.replace("h", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[d]$")) {
				milliSeconds = TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("d", ""))) + milliSeconds;
			}else if(t.matches("(\\d+)[w]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("d", ""))) * 7) + milliSeconds;
			}else if(t.matches("(\\d+)[mo]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("mo", ""))) * 30) + milliSeconds;
			}else if(t.matches("(\\d+)[y]$")) {
				milliSeconds = (TimeUnit.DAYS.toMillis(Integer.parseInt(t.replace("y", ""))) * 365) + milliSeconds;
			}
		}
		return milliSeconds;
	}
}