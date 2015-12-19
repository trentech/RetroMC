package com.gmail.trentech.RetroMC.Managers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.world.World;

import com.erigitic.service.TEService;
import com.gmail.trentech.RetroMC.Main;

import me.Flibio.EconomyLite.API.EconomyLiteAPI;

public class EconomyManager {

	private static TEService totalEconomy;
	private static EconomyLiteAPI economyLite;

	public static boolean initialize(){
		Optional<PluginContainer> plugin = Main.getGame().getPluginManager().getPlugin("EconomyLite");
		if(plugin.isPresent()) {
			economyLite = Main.getGame().getServiceManager().provide(EconomyLiteAPI.class).get();
			Main.getLog().info("Hooked to EconomyLite!");
			return true;
		}
		plugin = Main.getGame().getPluginManager().getPlugin("TotalEconomy");
		if(plugin.isPresent()) {
			totalEconomy = Main.getGame().getServiceManager().provide(TEService.class).get();
			Main.getLog().info("Hooked to TotalEconomy!");
			return true;
		}
		return false;
	}

	public static void deposit(String uuid, World world, double amount) {
		if(economyLite != null) {
			economyLite.getPlayerAPI().addCurrency(uuid, (int) amount);
		}else if(totalEconomy != null) {
			totalEconomy.addToBalance(UUID.fromString(uuid), new BigDecimal(amount), false);
		}
	}

	public static void withdraw(String uuid, World world, double amount) {
		if(economyLite != null) {
			economyLite.getPlayerAPI().removeCurrency(uuid, (int) amount);
		}else if(totalEconomy != null) {
			totalEconomy.removeFromBalance(UUID.fromString(uuid), new BigDecimal(amount));
		}
	}
	
	public static double getBalance(String uuid, World world) {
		if(economyLite != null) {
			return economyLite.getPlayerAPI().getBalance(uuid);
		} else if(totalEconomy != null) {
			return totalEconomy.getBalance(UUID.fromString(uuid)).doubleValue();
		}
		return 0;
	}

	public static void setBalance(String uuid, World world, double amount) {
		if(economyLite != null) {
			economyLite.getPlayerAPI().setBalance(uuid, (int) amount);
		}else if(totalEconomy != null) {
			totalEconomy.setBalance(UUID.fromString(uuid), new BigDecimal(amount));
		}
	}
}
